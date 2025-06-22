package com.senifit.was.exception.custom;

import com.senifit.was.exception.CustomException;
import com.senifit.was.exception.ErrorCode;

public class UserNotFoundException extends CustomException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}