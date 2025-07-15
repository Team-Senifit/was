package com.senifit.was.exception.custom;

import com.senifit.was.exception.CustomException;
import com.senifit.was.exception.ErrorCode;

public class RecordNotFoundException extends CustomException {
    public RecordNotFoundException() {
        super(ErrorCode.RECORD_NOT_FOUND);
    }
}
