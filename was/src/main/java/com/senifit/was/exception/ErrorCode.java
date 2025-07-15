package com.senifit.was.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // User
    USER_NOT_FOUND(404, "USR001", "존재하지 않는 사용자입니다."),
    CENTER_EXISTS(409, "AUTH001", "이미 존재하는 센터 ID 입니다.");

    private final int status;
    private final String code;
    private final String message;
}
