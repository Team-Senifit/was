package com.senifit.was.repository.survey;

import com.senifit.was.dto.response.survey.SurveyResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveysRepositoryCustom {
    List<SurveyResponse> findAllSurveyByRecordIdAndCenterId(Long recordId, Long centerId);
}
