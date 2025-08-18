package com.senifit.was.service.workoutData.exception;

import com.senifit.was.exception.api.ApiException;
import com.senifit.was.exception.api.ApiExceptionDetails;

public class InvalidXlsxTemplateApiException extends ApiException {
    public InvalidXlsxTemplateApiException() {
        super(ApiExceptionDetails.PROGRAM_TEMPLATE_PARSE_FAIL);
    }
}
