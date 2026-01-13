package com.toy.bukbuk.auth.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<ErrorResponseDto> handleBusinessException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                errorCode.getCode(),
                errorCode.getMessage()
        );

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(errorResponseDto);
    }
}
