package com.fredodev.riee.auth.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fredodev.riee.shared.api.ApiResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public JwtAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {
        String errorCode = "TOKEN_MISSING";
        String message = "Token no proporcionado o autenticacion requerida";

        if (authException instanceof JwtAuthenticationException jwtException) {
            errorCode = jwtException.getErrorCode();
            message = jwtException.getMessage();
        }

        ApiResponse<Void> apiResponse = ApiResponse.error(
                HttpStatus.UNAUTHORIZED.value(),
                message,
                List.of(errorCode)
        );

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), apiResponse);
    }
}
