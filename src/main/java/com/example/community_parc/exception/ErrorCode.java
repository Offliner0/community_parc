package com.example.community_parc.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    INVALID_INPUT_PARAMETER(HttpStatus.BAD_REQUEST, "잘못된 입력값입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "시스템에 문제가 발생했습니다. 관리자에게 문의해주십시오"),
    MAIL_SENDING_FAILED(HttpStatus.INTERNAL_SERVER_ERROR,"이메일 전송 실패");

    private final HttpStatus httpStatus;
    private final String message;

}
