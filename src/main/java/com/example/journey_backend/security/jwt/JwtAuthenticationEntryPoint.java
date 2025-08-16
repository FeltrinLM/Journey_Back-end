package com.example.journey_backend.security.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serial;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Este método é chamado sempre que uma requisição não autenticada tenta acessar um recurso protegido.
     * Ele retorna um erro 401 (Unauthorized) com uma mensagem customizada.
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Acesso não autorizado. Token ausente ou inválido.");
    }
}
