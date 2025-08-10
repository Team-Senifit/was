package com.senifit.was.exception.custom;

import com.senifit.was.exception.api.ApiException;
import com.senifit.was.exception.api.ApiExceptionDetails;

public class SurveySizeMismatchException extends ApiException {
    public SurveySizeMismatchException() {
      super(ApiExceptionDetails.SURVEY_SIZE_MISMATCH);
    }
}
