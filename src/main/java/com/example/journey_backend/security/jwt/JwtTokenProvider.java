package com.example.journey_backend.security.jwt;

// 1. Imports da Biblioteca JJWT
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

// 2. Imports do Spring Framework
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

// 3. Imports do Java Padrão
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationMs;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    // --- AQUI ESTÁ A MUDANÇA ---
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();

        // 1. Extraímos as permissões (Roles) do usuário autenticado
        // O Spring Security guarda isso como uma lista de GrantedAuthority
        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst() // Pegamos a primeira role (ex: "Administrador")
                .orElse("Funcionario"); // Fallback caso não tenha nenhuma

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        // 2. Adicionamos .claim("role", role) no builder
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role) // <--- O PULO DO GATO: Salvamos o tipo DENTRO do token
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;

        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Token inválido: " + e.getMessage());
        }
        return false;
    }
}