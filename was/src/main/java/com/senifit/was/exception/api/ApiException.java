package com.senifit.was.exception.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiException extends RuntimeException {
    private final String errorCode;
    private final int httpStatusCode;
    private final String errorMessage;

    public ApiException(ApiExceptionDetails details) {
        this.errorCode = details.getErrorCode();
        this.httpStatusCode = details.getHttpStatusCode();
        this.errorMessage = details.getMessage();
    }
}
