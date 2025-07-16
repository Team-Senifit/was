package com.senifit.was.exception.api.common;

import com.senifit.was.exception.api.ApiException;
import com.senifit.was.exception.api.ApiExceptionDetails;

public class UnhandledApiException extends ApiException {
    public UnhandledApiException() {
       super(ApiExceptionDetails.UNHANDLED);
    }
}
