package com.example.journey_backend.security.jwt;

import io.jsonwebtoken.ExpiredJwtException; // Importe
import io.jsonwebtoken.JwtException; // Importe genérico
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);

            // --- CORREÇÃO AQUI ---
            // Colocamos a extração do username dentro de um try...catch
            // Se o token estiver expirado ou malformado, ele apenas será ignorado
            try {
                username = tokenProvider.getUsernameFromToken(token);
            } catch (ExpiredJwtException e) {
                logger.warn("Token JWT expirado: " + e.getMessage());
            } catch (JwtException e) {
                logger.warn("Erro ao parsear JWT: " + e.getMessage());
            }
            // --- FIM DA CORREÇÃO ---
        }

        // 2. Se tiver username e o contexto não estiver autenticado, continue
        // (Esta lógica agora só roda se o 'username' foi extraído com sucesso)
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // A validação aqui é uma segunda garantia, mas a extração do username já é uma boa validação
            if (tokenProvider.validateToken(token)) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // 3. Continua a requisição
        filterChain.doFilter(request, response);
    }
}