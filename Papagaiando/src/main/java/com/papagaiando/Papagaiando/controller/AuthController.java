package com.papagaiando.Papagaiando.controller;

import com.papagaiando.Papagaiando.dto.AuthLoginDTO;
import com.papagaiando.Papagaiando.model.UsuarioModel;
import com.papagaiando.Papagaiando.security.JwtUtil;
import com.papagaiando.Papagaiando.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;



import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthLoginDTO loginDTO) {
        try {
            UsuarioModel usuario = usuarioService.autenticarUsuario(
                loginDTO.getEmail(), 
                loginDTO.getSenha()
            );
            
            String token = jwtUtil.generateToken(
                usuario.getEmail(), 
                usuario.getId().toString()
            );
            
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("email", usuario.getEmail());
            response.put("userId", usuario.getId());
            response.put("nome", usuario.getNome());
            
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Credenciais inválidas");
            errorResponse.put("message", e.getMessage());
            
            return ResponseEntity.status(401).body(errorResponse);
        }
    }

    
@PostMapping("/verificar-senha")
public ResponseEntity<?> verificarSenha(@RequestBody Map<String, String> body) {
    String senhaFornecida = body.get("senha");

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String emailUsuario = authentication.getName(); // 
    UsuarioModel usuario = usuarioService.buscarPorEmail(emailUsuario);

    boolean senhaCorreta = false;
    if (usuario != null) {
        senhaCorreta = passwordEncoder.matches(senhaFornecida, usuario.getSenha());
    }

    Map<String, Object> response = new HashMap<>();
    response.put("valido", senhaCorreta);

    if (senhaCorreta) {
        return ResponseEntity.ok(response);
    } else {
        response.put("message", "Senha incorreta");
        return ResponseEntity.status(401).body(response);
    }
}

}

