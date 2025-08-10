package com.senifit.was.exception.custom;

import com.senifit.was.exception.api.ApiException;
import com.senifit.was.exception.api.ApiExceptionDetails;

public class RecordNotFoundException extends ApiException {
    public RecordNotFoundException() {
      super(ApiExceptionDetails.RECORD_NOT_FOUND);
    }
}
