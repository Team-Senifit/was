package com.senifit.was.service.center.exception;

import com.senifit.was.exception.api.ApiException;
import com.senifit.was.exception.api.ApiExceptionDetails;

public class DuplicateCenterCodeExcpetion extends ApiException {
    public DuplicateCenterCodeExcpetion() {
        super(ApiExceptionDetails.CENTER_DUPLICATE_CODE);
    }
}
