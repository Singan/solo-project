package com.my.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        setResponse(response, "token 인증 오류");
    }

    private void setResponse(HttpServletResponse response, String message) throws IOException {

        log.error("[exceptionHandle] AuthenticationEntryPoint = {}", message);

        HttpStatusCode statusCode = HttpStatusCode.valueOf(403);
        ObjectMapper objectMapper = new ObjectMapper();

        response.setStatus(statusCode.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");

        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", "Unauthorized");
        errorDetails.put("message", message);

        String responseMsg = objectMapper.writeValueAsString(errorDetails);
        response.getWriter().write(responseMsg);
    }
}