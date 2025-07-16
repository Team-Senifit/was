package com.senifit.was.exception.api.common;

import com.senifit.was.exception.api.ApiExceptionDetails;
import com.senifit.was.exception.api.ApiException;

public class NotFoundApiException extends ApiException {
    public NotFoundApiException() {
        super(ApiExceptionDetails.NOT_FOUND);
    }
}
