package com.example.community_parc.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MailSendingException extends RuntimeException {
    private final ErrorCode errorCode;

    public static class MailSendingFailedException extends MailSendingException {
        public MailSendingFailedException() {
            super(ErrorCode.MAIL_SENDING_FAILED);
        }
    }

}
