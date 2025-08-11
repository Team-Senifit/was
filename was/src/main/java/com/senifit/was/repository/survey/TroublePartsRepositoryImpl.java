package com.senifit.was.repository.survey;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.senifit.was.entity.QSurveyTroublePart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
@RequiredArgsConstructor
public class TroublePartsRepositoryImpl implements TroublePartsRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public void deleteBySurveyIds(Collection<Long> surveyIds) {
        if (surveyIds == null || surveyIds.isEmpty()) return;
        QSurveyTroublePart tp = QSurveyTroublePart.surveyTroublePart;

        // JPQL bulk delete와 동일: 영속성 컨텍스트를 우회하여 DB에 직접 DELETE
        queryFactory
                .delete(tp)
                .where(tp.survey.surveyId.in(surveyIds))
                .execute();
    }
}
