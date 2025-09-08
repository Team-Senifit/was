package com.senifit.was.service.workoutData;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.senifit.was.dto.request.program.recommendation.ByPersonalRequest;
import com.senifit.was.dto.request.program.recommendation.ByTargetRequest;
import com.senifit.was.dto.response.program.ProgramInfoResponse;
import com.senifit.was.dto.response.program.ProgramResponse;
import com.senifit.was.entity.*;
import com.senifit.was.entity.base.BaseGlobalEnumSelection;
import com.senifit.was.entity.selections.CognitiveWorkoutKind;
import com.senifit.was.entity.selections.IncludesSingingWorkout;
import com.senifit.was.entity.selections.SpecializedWorkoutKind;
import com.senifit.was.entity.selections.TargetKind;
import com.senifit.was.exception.api.common.BadRequestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final JPAQueryFactory queryFactory;
    private final WorkoutDataDtoService workoutDataDtoService;

    public ProgramResponse getProgram(Long programId) {
        QProgram p = QProgram.program;
        QProgramBundle pb = QProgramBundle.programBundle;
        QBundleVideo bv = QBundleVideo.bundleVideo;
        QVideo v = QVideo.video;

        List<Video> videos = queryFactory
            .selectFrom(v)
            .join(bv).on(bv.video.eq(v))
            .join(pb).on(pb.bundle.eq(bv.bundle))
            .join(p).on(pb.program.eq(p))
            .where(p.id.eq(programId))
            .orderBy(pb.sequence.asc())
            .orderBy(bv.sequence.asc())
            .fetch();

        Program program = queryFactory
                .selectFrom(p)
                .where(p.id.eq(programId))
                .fetchOne();

        return workoutDataDtoService.buildProgramDtoWithVideos(program, videos);
    }

    public List<ProgramInfoResponse> byPersonal(ByPersonalRequest request){
        QProgram p = QProgram.program;

        int duration = switch (request.getDuration()) {
            case workout_duration_30minutes -> 30;
            case workout_duration_60minutes -> 60;
        };

        List<Program> queryResult = queryFactory
                .selectFrom(p)
                .where(
                        durationEquals(p, duration),
                        cognitiveWorkoutCodeEquals(p, request.getCognitive_workout_code().getId()),
                        targetCodeEquals(p, request.getPrimary_target_code().getId()),
                        singingWorkoutCodeEquals(p, request.getSinging_workout_code().getId())
                )
                .orderBy(p.createdAt.desc())
                .fetch();

        List<ProgramInfoResponse> result = new ArrayList<>(queryResult.size());
        for (Program program : queryResult) {
            result.add(workoutDataDtoService.buildProgramInfoDto(program));
        }
        return result;
    }

    public List<ProgramInfoResponse> byTarget(ByTargetRequest request){
        if (request.getWorkout_kind().equals(SpecializedWorkoutKind.workout_notSelected))
            throw new BadRequestApiException();

        QProgram p = QProgram.program;
        List<Program> queryResult = queryFactory
                .selectFrom(p)
                .where(specializedWorkoutCodeEquals(p, request.getWorkout_kind().getId()))
                .orderBy(p.createdAt.desc())
                .fetch();

        List<ProgramInfoResponse> result = new ArrayList<>(queryResult.size());
        for (Program program : queryResult) {
            result.add(workoutDataDtoService.buildProgramInfoDto(program));
        }
        return result;
    }

    /* TODO: programCount는 이제 의미 없음. 하지만 호환성 유지를 위해 유지중
       MVP 종료 후 프로그램 개선하며 API도 같이 개선 필요 */
    public List<ProgramInfoResponse> byPopular(Integer programCount){
        if (!(1 <= programCount && programCount <= 20))
            throw new BadRequestApiException();

        QProgram p = QProgram.program;

        List<Program> queryResult = queryFactory
            .selectFrom(p)
            .where(
                specializedWorkoutCodeEquals(p, SpecializedWorkoutKind.workout_programs_selections_byPopular.getId()))
            .orderBy(p.createdAt.desc())
            .limit(programCount)
            .fetch();

        List<ProgramInfoResponse> result = new ArrayList<>(queryResult.size());
        for (Program program : queryResult) {
            result.add(workoutDataDtoService.buildProgramInfoDto(program));
        }
        return result;
    }

    private BooleanExpression durationEquals(QProgram program, Integer duration) {
        return program.duration.eq(duration);
    }
    private BooleanExpression cognitiveWorkoutCodeEquals(QProgram program, Long code) {
        return program.cognitiveWorkoutKind.id.eq(code);
    }
    private BooleanExpression targetCodeEquals(QProgram program, Long code) {
        return program.primaryTarget.id.eq(code);
    }
    private BooleanExpression singingWorkoutCodeEquals(QProgram program, Long code) {
        return program.singingWorkoutKind.id.eq(code);
    }
    private BooleanExpression specializedWorkoutCodeEquals(QProgram program, Long code) {
        return program.specializedWorkoutKind.id.eq(code);
    }

}
