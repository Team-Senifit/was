package com.senifit.was.service.recommendation;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.senifit.was.dto.request.program.recommendation.ByPersonalRequest;
import com.senifit.was.dto.request.program.recommendation.ByTargetRequest;
import com.senifit.was.dto.response.program.ProgramInfoResponse;
import com.senifit.was.entity.*;
import com.senifit.was.entity.selections.SpecializedWorkoutKind;
import com.senifit.was.exception.api.common.BadRequestApiException;
import com.senifit.was.service.ProgramService;
import com.senifit.was.service.recommendation.by_personal_engines.v1.BasicRecommendationEngineV1;
import com.senifit.was.vo.ProgramData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final JPAQueryFactory queryFactory;
    private final ProgramService programService;
    private final BasicRecommendationEngineV1 recommendationEngine;
    private final ByPersonalRecommendationHelper byPersonalRequestHelper;

    @Transactional
    public List<ProgramInfoResponse> byPersonal(ByPersonalRequest request){
        ProgramData programData = recommendationEngine.generate(request);
        String programHash = byPersonalRequestHelper.generateProgramHash(programData.videos());
        Program program = programService.getPersonalByHash(programHash);

        if (program == null) {
            program = byPersonalRequestHelper.buildProgram(programData, programHash);
            programService.save(program);

            List<ProgramVideo> programDataList =
                    byPersonalRequestHelper.buildProgramVideos(
                            program.getId(), programData.videos());
            programService.saveProgramVideos(programDataList);
        }

        ArrayList<ProgramInfoResponse> result = new ArrayList<>();
        result.add(programService.buildProgramInfoDto(program));
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
            result.add(programService.buildProgramInfoDto(program));
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
            result.add(programService.buildProgramInfoDto(program));
        }
        return result;
    }

    private BooleanExpression specializedWorkoutCodeEquals(QProgram program, Long code) {
        return program.specializedWorkoutKind.id.eq(code);
    }
}