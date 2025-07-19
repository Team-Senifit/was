package com.senifit.was.exception.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiExceptionDetails {

    UNHANDLED(500, "INTERNAL000", "내부 오류가 발생했습니다. 잠시 후 다시 시도해 주세요."),
    BAD_REQUEST(400, "BADREQUEST000", "잘못된 요청입니다."),
    ACCESS_DENIED(403, "ACCESS000", "권한이 부족합니다."),
    NOT_FOUND(404, "NOTFOUND000", "잘못된 경로입니다."),

    // Sign Up
    SIGNUP_VALIDATION_ID_EXISTS(409, "SIGNUP_VAL:001", "이미 존재하는 센터 ID 입니다."),

    // Sign In
    SIGNIN_ELSE(403, "SIGNIN:000", "인증 과정 중 오류가 발생했습니다. 잠시후 다시 시도해 주세요."),
    SIGNIN_AUTHENTICATION_FAIL(401, "SIGNIN:001", "아이디 / 비밀번호를 확인하세요."),
    SIGNIN_MAXIMUM_SESSION_COUNT(403, "SIGNIN:002", "동시에 접속할 수 있는 최대 한도를 초과했습니다."),

    // Records

    // Centers
    CENTER_NOT_FOUND(404, "CEN001", "센터가 존재하지 않습니다."),

    // Programs
    PROGRAM_NOT_FOUND(404, "PRO001", "프로그램이 존재하지 않습니다."),

    // Members
    MEMBER_NOT_FOUND(404, "MEM001", "사용자가 존재하지 않습니다.");

    private final int httpStatusCode;
    private final String errorCode;
    private final String message;
}
