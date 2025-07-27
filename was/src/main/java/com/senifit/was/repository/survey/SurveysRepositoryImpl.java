package com.senifit.was.repository.survey;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.senifit.was.dto.response.survey.SurveyResponse;
import com.senifit.was.dto.response.survey.TroublePartsResponse;
import com.senifit.was.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class SurveysRepositoryImpl implements SurveysRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<SurveyResponse> findSurveyResponsesByRecordIdAndCenterId(Long recordId, Long centerId) {
        QSurveys survey = QSurveys.surveys;
        QRecordsMembers recordsMembers = QRecordsMembers.recordsMembers;
        QRecords records = QRecords.records;
        QTroubleParts troubleParts = QTroubleParts.troubleParts;

        List<Surveys> surveys = queryFactory
                .selectFrom(survey)
                .join(survey.recordsMembers, recordsMembers)
                .join(recordsMembers.records, records)
                .leftJoin(survey.troubleParts, troubleParts).fetchJoin()
                .where(records.recordId.eq(recordId),
                        survey.centerId.eq(centerId))
                .fetch();

        return surveys.stream()
                .map(s -> SurveyResponse.builder()
                        .surveyId(s.getSurveyId())
                        .troubleParts(
                                s.getTroubleParts().stream()
                                        .map(tp -> TroublePartsResponse.builder()
                                                .muscleType1(tp.getMuscleType1())
                                                .build())
                                        .collect(Collectors.toList())
                        )
                        .attitude(s.getAttitude())
                        .ability(s.getAbility())
                        .trouble(s.isTrouble())
                        .centerId(s.getCenterId())
                        .updatedAt(s.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
