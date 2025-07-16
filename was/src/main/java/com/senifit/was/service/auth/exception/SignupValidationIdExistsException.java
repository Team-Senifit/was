package com.senifit.was.service.auth.exception;

import com.senifit.was.exception.api.ApiExceptionDetails;
import com.senifit.was.exception.api.ApiException;

public class SignupValidationIdExistsException extends ApiException {
    public SignupValidationIdExistsException() {
        super(ApiExceptionDetails.SIGNUP_VALIDATION_ID_EXISTS);
    }
}
