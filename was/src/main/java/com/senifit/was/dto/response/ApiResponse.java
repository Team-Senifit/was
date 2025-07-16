package com.senifit.was.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.senifit.was.exception.api.ApiException;
import com.senifit.was.util.NullToEmptyObjectSerializer;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse<T> {
    private final int status;
    private final String message;

    @JsonSerialize(using = NullToEmptyObjectSerializer.class)
    private final T data;

    public static ApiResponse<Void> success() {
        return new ApiResponse<Void>(HttpStatus.OK.value(), "요청이 성공적으로 처리되었습니다.", null);
    }
    public static ApiResponse<Void> success(String message) {
       return new ApiResponse<Void>(HttpStatus.OK.value(), message, null);
    }
    public static <DataType> ApiResponse<DataType> success(DataType data) {
        return new ApiResponse<DataType>(HttpStatus.OK.value(), "요청이 성공적으로 처리되었습니다.", data);
    }

    public static ApiResponse<Void> failure(int statusCode, String message) {
        return new ApiResponse<Void>(statusCode, message, null);
    }
    public static ApiResponse<Void> failure(ApiException e) {
        return new ApiResponse<Void>(e.getHttpStatusCode(), e.getErrorMessage(), null);
    }

    public ApiResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
