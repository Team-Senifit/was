package com.senifit.was.exception.custom;

import com.senifit.was.exception.api.ApiException;
import com.senifit.was.exception.api.ApiExceptionDetails;

public class CenterNotFoundException extends ApiException {
    public CenterNotFoundException() {
      super(ApiExceptionDetails.CENTER_NOT_FOUND);
    }
}
