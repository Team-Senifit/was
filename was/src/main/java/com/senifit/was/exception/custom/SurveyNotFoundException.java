package com.senifit.was.exception.custom;

import com.senifit.was.exception.api.ApiException;
import com.senifit.was.exception.api.ApiExceptionDetails;

public class SurveyNotFoundException extends ApiException {
    public SurveyNotFoundException() {
      super(ApiExceptionDetails.SURVEY_NOT_FOUND);
    }
}
