package com.senifit.was.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Unhandled
    UNHANDLED(500, "INTERNAL001", "서버 내부 오류입니다."),

    // User
    USER_NOT_FOUND(404, "USR001", "존재하지 않는 사용자입니다."),

    // Sign Up Validation
    //  회원가입은 관리자가 처리하는 만큼 이중 validation은 불필요할걸로 판단하여, id만 중복처리하도록 구현했습니다.
    SIGNUP_VALIDATION_ID_EXISTS(409, "SIGNUP_VAL:001", "이미 존재하는 센터 ID 입니다.");

    private final int status;
    private final String code;
    private final String message;
}
