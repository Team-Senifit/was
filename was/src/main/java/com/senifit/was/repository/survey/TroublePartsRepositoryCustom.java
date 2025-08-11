package com.senifit.was.repository.survey;

import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TroublePartsRepositoryCustom {
    void deleteBySurveyIds(Collection<Long> surveyIds);
}
