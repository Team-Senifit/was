package com.senifit.was.repository.survey;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.senifit.was.dto.response.survey.SurveyResponse;
import com.senifit.was.dto.response.survey.TroublePartsResponse;
import com.senifit.was.entity.QSurvey;
import com.senifit.was.entity.QRecord;
import com.senifit.was.entity.QMemberRecord;
import com.senifit.was.entity.QSurveyTroublePart;
import com.senifit.was.entity.Survey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class SurveysRepositoryImpl implements SurveysRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<SurveyResponse> findAllSurveyByRecordIdAndCenterId(Long recordId, Long centerId) {
        QSurvey survey = QSurvey.survey;
        QMemberRecord memberRecord = QMemberRecord.memberRecord;
        QRecord record = QRecord.record;
        QSurveyTroublePart troublePart = QSurveyTroublePart.surveyTroublePart;

        List<Survey> surveys = queryFactory
                .selectFrom(survey)
                .join(survey.memberRecord, memberRecord)
                .join(memberRecord.record, record)
                .leftJoin(survey.surveyTroubleParts, troublePart).fetchJoin()
                .where(record.id.eq(recordId),
                        survey.centerId.eq(centerId))
                .fetch();

        return surveys.stream()
                .map(s -> SurveyResponse.builder()
                        .surveyId(s.getId())
                        .troubleParts(
                                s.getSurveyTroubleParts().stream()
                                        .map(tp -> TroublePartsResponse.builder()
                                                .target(tp.getTarget().getId())
                                                .build())
                                        .collect(Collectors.toList())
                        )
                        .attitude(s.getAttitudeScore())
                        .ability(s.getAbilityScore())
                        .trouble(Boolean.TRUE.equals(s.getHadTrouble()))
                        .centerId(s.getCenterId())
                        .updatedAt(s.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
