package com.senifit.was.exception.api.common;

import com.senifit.was.exception.api.ApiExceptionDetails;
import com.senifit.was.exception.api.ApiException;

public class NoAccessApiException extends ApiException {
    public NoAccessApiException() {
        super(ApiExceptionDetails.ACCESS_DENIED);
    }
}
