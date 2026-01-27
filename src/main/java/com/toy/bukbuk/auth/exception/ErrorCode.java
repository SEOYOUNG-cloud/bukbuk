package com.toy.bukbuk.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    LOGIN_FAILED(
            HttpStatus.UNAUTHORIZED,
            "LOGIN_FAILED",
            "이메일 또는 비밀번호가 올바르지 않습니다."
    ),

    INVALID_TOKEN(
            HttpStatus.UNAUTHORIZED,
            "INVALID_TOKEN",
            "유효하지 않은 토큰입니다."
    ),

    EMAIL_DUPLICATED(
            HttpStatus.CONFLICT,
            "EMAIL_DUPLICATED",
            "이미 존재하는 이메일입니다."
    ),

    USER_NOT_FOUND(
      HttpStatus.UNAUTHORIZED,
      "USER_NOT_FOUND",
      "존재하지 않는 유저입니다."
    ),

    ACCESS_TOKEN_EXPIRED(
        HttpStatus.UNAUTHORIZED,
        "ACCESS_TOKEN_EXPIRED",
        "Access Token이 만료되었습니다."
    ),

    REFRESH_TOKEN_EXPIRED(
            HttpStatus.UNAUTHORIZED,
            "REFRESH_TOKEN_EXPIRED",
            "Refresh Token이 만료되었습니다."
    );

    private final HttpStatus status;
    private final String code;
    private final String message;
}
