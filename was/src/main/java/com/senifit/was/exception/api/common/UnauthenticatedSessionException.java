package com.senifit.was.exception.api.common;

import com.senifit.was.exception.api.ApiException;
import com.senifit.was.exception.api.ApiExceptionDetails;

public class UnauthenticatedSessionException extends ApiException {
    public UnauthenticatedSessionException() {
        super(ApiExceptionDetails.UN_AUTH_SESSION);
    }
}
