package com.senifit.was.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // User
    USER_NOT_FOUND(404, "USR001", "존재하지 않는 사용자입니다.");

    private final int status;
    private final String code;
    private final String message;
}
