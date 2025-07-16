package com.senifit.was.exception.api.common;

import com.senifit.was.exception.api.ApiExceptionDetails;
import com.senifit.was.exception.api.ApiException;

public class BadRequestApiException extends ApiException {
    public BadRequestApiException() {
        super(ApiExceptionDetails.BAD_REQUEST);
    }
}
