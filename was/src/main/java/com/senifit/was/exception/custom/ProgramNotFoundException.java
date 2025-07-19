package com.senifit.was.exception.custom;

import com.senifit.was.exception.api.ApiException;
import com.senifit.was.exception.api.ApiExceptionDetails;

public class ProgramNotFoundException extends ApiException {
    public ProgramNotFoundException() {
      super(ApiExceptionDetails.PROGRAM_NOT_FOUND);
    }
}
