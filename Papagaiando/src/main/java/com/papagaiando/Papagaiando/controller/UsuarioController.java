package com.papagaiando.Papagaiando.controller;

import com.papagaiando.Papagaiando.dto.*;
import com.papagaiando.Papagaiando.model.UsuarioModel;
import com.papagaiando.Papagaiando.security.JwtUtil;
import com.papagaiando.Papagaiando.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.HashMap;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<?> criarUsuario(@Valid @RequestBody UsuarioCreateDTO usuarioDTO) {
        UsuarioModel usuarioCriado = usuarioService.registrarUsuario(
            usuarioDTO.getEmail(),
            usuarioDTO.getNome(),
            usuarioDTO.getSenha()
        );

        String token = jwtUtil.generateToken(
            usuarioCriado.getEmail(),
            usuarioCriado.getId().toString()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("usuario", usuarioCriado);
        response.put("token", token);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioModel>> listarUsuarios() {
        List<UsuarioModel> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioModel> buscarPorId(@PathVariable UUID id) {
        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioModel> atualizarUsuario(
            @PathVariable UUID id,
            @Valid @RequestBody UsuarioUpdateDTO usuarioDTO) {
        
        UsuarioModel atualizado = usuarioService.atualizarUsuario(
            id,
            usuarioDTO.getNome(),
            usuarioDTO.getEmail(),
            usuarioDTO.getSenha()
        );
        
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable UUID id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/esqueci-senha")
    public ResponseEntity<String> esqueciSenha(@RequestParam String email) {
        String token = usuarioService.gerarTokenRecuperacao(email);
        if (token != null) {
            return ResponseEntity.ok("E-mail de recuperação enviado!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
    }

    @PostMapping("/redefinir-senha")
    public ResponseEntity<String> redefinirSenha(
            @Valid @RequestBody PasswordResetDTO resetDTO) {
        
        boolean sucesso = usuarioService.redefinirSenha(
            resetDTO.getToken(),
            resetDTO.getNovaSenha()
        );
        
        if (sucesso) {
            return ResponseEntity.ok("Senha redefinida com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token inválido ou expirado");
    }
}