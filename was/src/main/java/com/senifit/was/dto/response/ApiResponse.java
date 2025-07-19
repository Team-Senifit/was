package com.senifit.was.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.senifit.was.exception.api.ApiException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.Map;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {
    private final int status;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String errorCode;

    public static ApiResponse<Void> success() {
        return new ApiResponse<>(HttpStatus.OK.value(), "요청이 성공적으로 처리되었습니다.", null, null);
    }
    public static ApiResponse<Void> success(String message) {
       return new ApiResponse<>(HttpStatus.OK.value(), message, null, null);
    }
    public static <DataType> ApiResponse<DataType> success(DataType data) {
        return new ApiResponse<>(HttpStatus.OK.value(), "요청이 성공적으로 처리되었습니다.", data, null);
    }

    public static ApiResponse<Map<String, Object>> success(Long id) {
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "요청이 성공적으로 처리되었습니다.",
                Collections.singletonMap("id", id),
                null
        );
    }


    public static ApiResponse<Void> failure(ApiException e) {
        return new ApiResponse<>(e.getHttpStatusCode(), e.getErrorMessage(), null, e.getErrorCode());
    }
}
