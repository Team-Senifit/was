package com.senifit.was.service.auth.exception;

import com.senifit.was.exception.api.ApiExceptionDetails;
import com.senifit.was.exception.api.ApiException;

public class SigninAuthenticationFailureException extends ApiException {
    public SigninAuthenticationFailureException() {
        super(ApiExceptionDetails.SIGNIN_AUTHENTICATION_FAIL);
    }
}