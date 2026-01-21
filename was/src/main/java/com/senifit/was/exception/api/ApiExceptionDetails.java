package com.senifit.was.exception.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiExceptionDetails {

    UNHANDLED(500, "INTERNAL000", "내부 오류가 발생했습니다. 잠시 후 다시 시도해 주세요."),
    BAD_REQUEST(400, "BADREQUEST000", "잘못된 요청입니다."),
    ACCESS_DENIED(401, "ACCESS000", "인증에 실패하였습니다."),
    NOT_AUTHORIZED(403, "ACCESS001", "권한이 부족합니다."),
    NOT_FOUND(404, "NOTFOUND000", "잘못된 경로입니다."),
    UN_AUTH_SESSION(401, "SESSION000", "유효하지 않은 세션 정보 입니다."),

    // Sign Up
    SIGNUP_VALIDATION_ID_EXISTS(409, "SIGNUP_VAL:001", "이미 존재하는 센터 ID 입니다."),

    // Sign In
    SIGNIN_ELSE(403, "SIGNIN:000", "인증 과정 중 오류가 발생했습니다. 잠시후 다시 시도해 주세요."),
    SIGNIN_AUTHENTICATION_FAIL(401, "SIGNIN:001", "아이디 / 비밀번호를 확인하세요."),
    SIGNIN_MAXIMUM_SESSION_COUNT(403, "SIGNIN:002", "동시에 접속할 수 있는 최대 한도를 초과했습니다."),

    // Records
    RECORD_NOT_FOUND(404, "REC001", "레코드가 존재하지 않습니다."),

    // Surveys
    SURVEY_NOT_FOUND(404, "SUR001", "설문이 존재하지 않습니다."),
    SURVEY_SIZE_MISMATCH(400, "SUR002", "요청한 설문 개수와 실제 설문 개수가 일치하지 않습니다."),

    // Centers
    CENTER_NOT_FOUND(404, "CEN001", "센터가 존재하지 않습니다."),
    CENTER_DUPLICATE_CODE(409, "CEN002", "이미 존재하는 센터 ID 입니다."),

    // Programs
    PROGRAM_NOT_FOUND(404, "PRO002", "프로그램이 존재하지 않습니다."),

    // Members
    MEMBER_NOT_FOUND(404, "MEM001", "사용자가 존재하지 않습니다.");

    private final int httpStatusCode;
    private final String errorCode;
    private final String message;
}
