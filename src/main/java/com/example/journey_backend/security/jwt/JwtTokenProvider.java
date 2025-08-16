package com.example.journey_backend.security.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationMs;

    // Gera o token baseado na autenticação
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    // Pega o nome do usuário (username) de dentro do token
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Valida o token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            System.out.println("Assinatura inválida do JWT: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Token JWT malformado: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("Token JWT expirado: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("Token JWT não suportado: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Token JWT vazio: " + e.getMessage());
        }

        return false;
    }
}
