package com.senifit.was.repository.survey;

import com.senifit.was.dto.response.survey.SurveyResponse;
import com.senifit.was.entity.Survey;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface SurveysRepositoryCustom {
    List<SurveyResponse> findAllSurveyByRecordIdAndCenterId(Long recordId, Long centerId);
    List<Survey> findAllByRecordId(Long recordId);
}
