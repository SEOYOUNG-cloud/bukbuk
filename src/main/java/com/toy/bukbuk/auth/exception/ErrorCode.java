package com.toy.bukbuk.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    EMAIL_DUPLICATED(
            HttpStatus.CONFLICT,
            "EMAIL_DUPLICATED",
            "이미 존재하는 이메일입니다."
    ),

    USER_NOT_FOUND(
      HttpStatus.NOT_FOUND,
      "USER_NOT_FOUND",
      "존재하지 않는 유저입니다."
    );

    private final HttpStatus status;
    private final String code;
    private final String message;
}
