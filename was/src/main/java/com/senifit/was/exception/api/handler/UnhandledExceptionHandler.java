package com.senifit.was.exception.api.handler;

import com.senifit.was.dto.response.ApiResponse;
import com.senifit.was.exception.api.common.UnhandledApiException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class UnhandledExceptionHandler {
    private final HandlerUtil handlerUtil;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleUnfilteredException(Exception exception, HttpServletRequest request) {
        handlerUtil.log("general", exception.toString(), request);
        return handlerUtil.buildResponseEntityWithApiException(new UnhandledApiException());
    }
}
