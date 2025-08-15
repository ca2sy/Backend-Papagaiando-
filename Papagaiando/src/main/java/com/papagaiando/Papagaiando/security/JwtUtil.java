package com.papagaiando.Papagaiando.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private final String SECRET_KEY_STRING = "G5rT9vX2kL8mQ4pZ7hS1jN6wU3yC0bAq";
    private final Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes(StandardCharsets.UTF_8));

    public String generateToken(String email, String userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        return createToken(claims, email);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        long expirationTime = 1000 * 60 * 60 * 10; // 10 horas
        return Jwts.builder()
                   .setClaims(claims)
                   .setSubject(subject)
                   .setIssuedAt(new Date(System.currentTimeMillis()))
                   .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                   .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                   .compact();
    }

    public Boolean validateToken(String token, String email) {
    try {
        final String username = extractUsername(token);
        boolean valid = username.equals(email) && !isTokenExpired(token);
        
        System.out.println("Validando token:");
        System.out.println(" - Email esperado: " + email);
        System.out.println(" - Email no token: " + username);
        System.out.println(" - Token expirado? " + isTokenExpired(token));
        System.out.println(" - Token válido? " + valid);
        
        return valid;
    } catch (Exception e) {
        System.out.println("Erro na validação: " + e.getMessage());
        return false;
    }
}

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractUserId(String token) {
        final Claims claims = extractAllClaims(token);
        return claims.get("userId", String.class);
    }

    public UUID extractUserIdAsUUID(String token) {
    String userId = extractUserId(token);
    try {
        return UUID.fromString(userId);
    } catch (IllegalArgumentException e) {
        throw new JwtException("ID de usuário inválido no token");
    }
}
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(SECRET_KEY)
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
