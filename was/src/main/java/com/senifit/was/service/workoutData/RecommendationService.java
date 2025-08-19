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
import com.senifit.was.entity.selections.ProgramRecommendationPerTargetKind;
import com.senifit.was.entity.selections.TargetKind;
import com.senifit.was.exception.api.common.BadRequestApiException;
import com.senifit.was.repository.bundle.BundleVideoRepository;
import com.senifit.was.repository.bundle.ProgramBundleRepository;
import com.senifit.was.repository.video.VideoRepository;
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
        QProgram p = QProgram.program;
        QProgramBundle pb = QProgramBundle.programBundle;
        QBundleVideo bv = QBundleVideo.bundleVideo;
        QVideo v = QVideo.video;

        ProgramRecommendationPerTargetKind rawKind = request.getWorkout_kind();
        BaseGlobalEnumSelection kind = CognitiveWorkoutKind.fromId(rawKind.getId());

        if (kind == null)
            kind = TargetKind.fromId(rawKind.getId());
        if (kind == null)
            kind = IncludesSingingWorkout.fromId(rawKind.getId());
        if (kind == null)
            throw new BadRequestApiException();

        List<Program> queryResult = queryFactory
                .selectFrom(p)
                .where(
                    kind instanceof CognitiveWorkoutKind ? p.cognitiveWorkoutKind.id.eq(kind.getId()) :
                    kind instanceof TargetKind ? p.primaryTarget.id.eq(kind.getId()) :
                    p.singingWorkoutKind.id.eq(kind.getId())
                )
                .orderBy(p.createdAt.desc())
                .fetch();

        List<ProgramInfoResponse> result = new ArrayList<>(queryResult.size());
        for (Program program : queryResult) {
            result.add(workoutDataDtoService.buildProgramInfoDto(program));
        }
        return result;
    }

    public List<ProgramInfoResponse> byPopular(Integer programCount){
        if (!(1 <= programCount && programCount <= 20))
            throw new BadRequestApiException();

        QProgram p = QProgram.program;
        QProgramStat ps = QProgramStat.programStat;

        List<Program> queryResult = queryFactory
            .selectFrom(p)
            .join(ps).on(p.eq(ps.program))
            .orderBy(ps.usedCount.desc())
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

}
