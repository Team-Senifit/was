package com.senifit.was.exception.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiException extends RuntimeException {
    private final String errorCode;
    private final int httpStatusCode;
    private final String errorMessage;
    private final boolean loggingEnabled;

    public ApiException(ApiExceptionDetails details) {
        this.errorCode = details.getErrorCode();
        this.httpStatusCode = details.getHttpStatusCode();
        this.errorMessage = details.getMessage();
        this.loggingEnabled = details.isEnableLogging();
    }
}
