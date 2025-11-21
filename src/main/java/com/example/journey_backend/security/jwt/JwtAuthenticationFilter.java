package com.example.journey_backend.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
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

        try {
            String header = request.getHeader("Authorization");

            // LOG 1: Ver se o cabeçalho chegou
            if (header != null) {
                System.out.println("--- JWT FILTER ---");
                System.out.println("Header Authorization recebido: " + header);
            }

            String token = null;
            String username = null;

            if (header != null && header.startsWith("Bearer ")) {
                token = header.substring(7).trim(); // .trim() remove espaços extras/quebras de linha

                // LOG 2: Ver o token limpo
                System.out.println("Token extraído: " + token);

                try {
                    username = tokenProvider.getUsernameFromToken(token);
                    System.out.println("Username extraído do token: " + username);
                } catch (ExpiredJwtException e) {
                    System.out.println("ERRO: Token expirado.");
                } catch (Exception e) {
                    System.out.println("ERRO: Falha ao ler username do token. Causa: " + e.getMessage());
                    e.printStackTrace(); // Isso vai mostrar o erro exato no console
                }
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Tenta carregar do banco
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                System.out.println("Usuário encontrado no banco de dados: " + userDetails.getUsername());

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
                    System.out.println("Autenticação definida no Contexto com sucesso!");
                } else {
                    System.out.println("ERRO: Token não passou na validação (validateToken retornou false).");
                }
            }
        } catch (Exception e) {
            System.out.println("ERRO CRÍTICO NO FILTRO:");
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }
}