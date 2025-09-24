package com.papagaiando.Papagaiando.security;

import com.papagaiando.Papagaiando.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AuthUtil {
    
    @Autowired
    private JwtUtil jwtUtil;
    
    public UUID extractUserIdFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token não fornecido");
        }
        
        String token = authHeader.substring(7);
        return jwtUtil.extractUserIdAsUUID(token);
    }
    
    public String extractEmailFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token não fornecido");
        }
        
        String token = authHeader.substring(7);
        return jwtUtil.extractUsername(token);
    }
}

