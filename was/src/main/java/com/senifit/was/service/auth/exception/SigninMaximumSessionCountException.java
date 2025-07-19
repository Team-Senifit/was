package com.senifit.was.service.auth.exception;

import com.senifit.was.exception.api.ApiExceptionDetails;
import com.senifit.was.exception.api.ApiException;

public class SigninMaximumSessionCountException extends ApiException {
    public SigninMaximumSessionCountException() {
        super(ApiExceptionDetails.SIGNIN_MAXIMUM_SESSION_COUNT);
    }
}