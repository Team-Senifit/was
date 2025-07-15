package com.senifit.was.exception.custom;

import com.senifit.was.exception.CustomException;
import com.senifit.was.exception.ErrorCode;

public class CenterExistsException extends CustomException {
    public CenterExistsException() {
        super(ErrorCode.CENTER_EXISTS);
    }
}