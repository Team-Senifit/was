package com.senifit.was.exception.api.handler;

import com.senifit.was.dto.response.ApiResponse;
import com.senifit.was.exception.api.ApiException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
@Configuration
public class HandlerUtil {
    public void log(String title, String message, HttpServletRequest request) {
        log.warn("[API Exception ({})] {} | {} -> {} {}",
            title, message,
            request.getRemoteAddr(),
            request.getMethod(),
            request.getRequestURI()
        );
    }

    public ResponseEntity<ApiResponse<Void>> buildResponseEntityWithApiException(ApiException e) {
        return ResponseEntity
                .status(HttpStatus.valueOf(e.getHttpStatusCode()))
                .body(ApiResponse.failure(e));
    }
}
