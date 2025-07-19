package com.senifit.was.exception.api.handler;

import com.senifit.was.dto.response.ApiResponse;
import com.senifit.was.exception.api.common.BadRequestApiException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class BadRequestApiExceptionHandler {
    private final HandlerUtil handlerUtil;

    // 클라이언트가 요청을 잘못 날려서 생기는 오류들을 한번에 묶어
    // 400 Bad Request로 처리하는 부분입니다.
    // TODO: Exception들 목록 정확한지 다시 확인
    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            HttpMediaTypeException.class,
            MethodArgumentNotValidException.class,
            MissingServletRequestParameterException.class,
            MethodArgumentTypeMismatchException.class,
            MissingPathVariableException.class,
            BindException.class,
    })
    public ResponseEntity<ApiResponse<Void>> handleClientBadRequest(Exception ex, HttpServletRequest request) {
        handlerUtil.log("Bad request", ex.toString(), request);
        return handlerUtil.buildResponseEntityWithApiException(new BadRequestApiException());

    }

}
