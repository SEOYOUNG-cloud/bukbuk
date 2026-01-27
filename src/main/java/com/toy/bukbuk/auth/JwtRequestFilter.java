package com.toy.bukbuk.auth;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private static final String JWT_EXCEPTION_ATTRIBUTE = "JWT_EXCEPTION";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try{
            // 토큰 유효성 검사
            authenticateIfPossible(request);
        } catch (UsernameNotFoundException e) {
            log.warn("User not found: {}", e.getMessage());
            request.setAttribute(JWT_EXCEPTION_ATTRIBUTE, e);
        } catch (ExpiredJwtException e) {
            log.warn("JWT token expired: {}", e.getMessage());
            request.setAttribute(JWT_EXCEPTION_ATTRIBUTE, e);
        } catch (JwtException e) {
            log.warn("Invalid JWT token: {}", e.getMessage());
            request.setAttribute(JWT_EXCEPTION_ATTRIBUTE, e);
        } catch (Exception e) {
            log.error("Authentication error: {}", e.getMessage());
            request.setAttribute(JWT_EXCEPTION_ATTRIBUTE, e);
        }

        filterChain.doFilter(request, response);
    }

    private void authenticateIfPossible(HttpServletRequest request){
        String token = extractToken(request);

        if(token == null || !jwtUtil.isTokenValid(token)) {
            return;
        }

        String username = jwtUtil.extractUsername(token);
        if(username == null || isAlreadyAuthenticated()){
            return;
        }

        // 사용자 정보 로드
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        // 토큰 정보와 일치하는지 확인
        if(!jwtUtil.validateToken(token, userDetails)) {
            return;
        }

        // Setting Information in SecurityContext
        setAuthentication(request, userDetails);
    }

    // 헤더에서 토큰 추출
    private String extractToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");

        if(token != null && token.startsWith("Bearer ")) {
            return null;
        }
        return token;
    }

    private boolean isAlreadyAuthenticated(){
        // SecurityContext에 인증 정보가 있는지 확인
        return SecurityContextHolder.getContext().getAuthentication() != null;
    }

    private void setAuthentication(HttpServletRequest request, UserDetails userDetails) {
        // Make authentication object in Spring Security
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        // 요청정보(IP, session ..) 세팅 - client IP 검사 시 사용
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // Security Context에 인증정보 저장
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
