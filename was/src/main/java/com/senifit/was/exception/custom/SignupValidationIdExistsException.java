package com.senifit.was.exception.custom;

import com.senifit.was.exception.CustomException;
import com.senifit.was.exception.ErrorCode;

public class SignupValidationIdExistsException extends CustomException {
    public SignupValidationIdExistsException() {
        super(ErrorCode.SIGNUP_VALIDATION_ID_EXISTS);
    }
}