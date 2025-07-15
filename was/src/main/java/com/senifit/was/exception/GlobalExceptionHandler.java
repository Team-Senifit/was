package com.senifit.was.exception;

import com.senifit.was.common.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 모든 커스텀 예외 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        log.error("Handled CustomException throw: {}", e.getErrorCode().getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                e.getErrorCode().getStatus(),
                e.getErrorCode().getCode(),
                e.getErrorCode().getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }

    // throw로 처리하지 못한 나머지 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Unhandled exception: " + e);
        ErrorCode unhandled = ErrorCode.UNHANDLED;
        ErrorResponse errorResponse = new ErrorResponse(
                unhandled.getStatus(),
                unhandled.getCode(),
                unhandled.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(unhandled.getStatus()));
    }
}
