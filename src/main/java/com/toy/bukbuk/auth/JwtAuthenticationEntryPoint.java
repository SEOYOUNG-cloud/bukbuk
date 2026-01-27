package com.toy.bukbuk.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toy.bukbuk.auth.exception.ErrorCode;
import com.toy.bukbuk.auth.exception.ErrorResponseDto;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;
    private final View error;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorCode errorCode = ErrorCode.LOGIN_FAILED;

        Object exception = request.getAttribute("JWT_EXCEPTION");
        if(exception instanceof ExpiredJwtException){
            errorCode = ErrorCode.ACCESS_TOKEN_EXPIRED;
        } else if(exception instanceof JwtException){
            errorCode = ErrorCode.INVALID_TOKEN;
        }

        response.setStatus(errorCode.getStatus().value());
        response.setContentType("application/json;charset=UTF-8");

        response.getWriter().write(
                objectMapper.writeValueAsString( // json
                    ErrorResponseDto.from(errorCode)
                )
        );
    }
}
