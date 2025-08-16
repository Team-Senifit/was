package com.senifit.was.service.program;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.senifit.was.dto.request.program.recommendation.ByPersonalRequest;
import com.senifit.was.dto.response.program.ProgramResponse;
import com.senifit.was.dto.response.program.VideoResponse;
import com.senifit.was.entity.Program;
import com.senifit.was.entity.QProgram;
import com.senifit.was.repository.bundle.BundleVideoRepository;
import com.senifit.was.repository.bundle.ProgramBundleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final JPAQueryFactory queryFactory;
    private final ProgramBundleRepository programBundleRepository;
    private final BundleVideoRepository bundleVideoRepository;

    public List<ProgramResponse> byPersonal(ByPersonalRequest request){
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
                        singingWorkoutCodeEquals(p, request.getSigning_workout_code().getId())
                )
                .orderBy(p.createdAt.desc())
                .fetch();

        List<ProgramResponse> result = new ArrayList<>(queryResult.size());
        for (Program program : queryResult) {

        }
        return null;

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
