package com.senifit.was.repository.survey;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.senifit.was.dto.response.survey.SurveyResponse;
import com.senifit.was.dto.response.survey.TroublePartsResponse;
import com.senifit.was.entity.*;
import com.senifit.was.entity.selections.BaseSelectionEnum;
import com.senifit.was.entity.selections.TargetKind;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
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
        QMember member = QMember.member;
        QSurveyTroublePart troublePart = QSurveyTroublePart.surveyTroublePart;

        List<Survey> surveys = queryFactory
                .selectFrom(survey)
                .join(survey.memberRecord, memberRecord).fetchJoin()
                .join(memberRecord.member, member).fetchJoin()
                .join(memberRecord.record, record)
                .leftJoin(survey.surveyTroubleParts, troublePart).fetchJoin()
                .where(record.recordId.eq(recordId),
                        survey.centerId.eq(centerId))
                .fetch();

        return surveys.stream()
                .map(s -> {
                    var m = s.getMemberRecord().getMember();
                    return SurveyResponse.builder()
                            .surveyId(s.getSurveyId())
                            .name(m.getName())
                            .birthDate(m.getBirthDate())
                            .gender(m.getGender().getId())
                            .memberRank(m.getRank().getId())
                            .troubleParts(
                                    s.getSurveyTroubleParts().stream()
                                            .map(tp -> TroublePartsResponse.builder()
                                                    .target(
                                                            BaseSelectionEnum.fromId(
                                                                    TargetKind.class,
                                                                    tp.getTarget().getId()
                                                            ))
                                                    .build())
                                            .collect(Collectors.toList())
                            )
                            .attitudeScore(s.getAttitudeScore())
                            .abilityScore(s.getAbilityScore())
                            .hadTrouble(Boolean.TRUE.equals(s.getHadTrouble()))
                            .updatedAt(s.getUpdatedAt())
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Survey> findAllByRecordId(Long recordId) {
        QSurvey s = QSurvey.survey;
        QMemberRecord mr = QMemberRecord.memberRecord;
        QRecord r = QRecord.record;

        // Survey 엔티티만 필요하므로 fetch join은 생략 (N+1 없음)
        return queryFactory
                .selectFrom(s)
                .join(s.memberRecord, mr)
                .join(mr.record, r)
                .where(r.recordId.eq(recordId))
                .fetch();
    }
}
