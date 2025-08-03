package com.example.community_parc.exception;

import jakarta.mail.MessagingException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommonException extends RuntimeException {
    private final ErrorCode errorCode;

    // 400
    public static class InvalidInputException extends CommonException {
        public InvalidInputException() {
            super(ErrorCode.INVALID_INPUT_PARAMETER);
        }
    }

    // 500
    public static class InternalServerException extends CommonException {
        public InternalServerException() {
            super(ErrorCode.SERVER_ERROR);
        }
    }


}
