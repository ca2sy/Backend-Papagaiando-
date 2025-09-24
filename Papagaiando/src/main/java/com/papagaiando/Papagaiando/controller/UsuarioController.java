package com.papagaiando.Papagaiando.controller;

import com.papagaiando.Papagaiando.dto.*;
import com.papagaiando.Papagaiando.model.UsuarioModel;
import com.papagaiando.Papagaiando.security.JwtUtil;
import com.papagaiando.Papagaiando.service.UsuarioService;
import com.papagaiando.Papagaiando.security.AuthUtil;
import jakarta.servlet.http.HttpServletRequest;
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
    
    @Autowired
    private AuthUtil authUtil;

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

    // Endpoint para buscar dados do próprio usuário logado
    @GetMapping("/me")
    public ResponseEntity<UsuarioModel> buscarMeuPerfil(HttpServletRequest request) {
        UUID usuarioLogado = authUtil.extractUserIdFromRequest(request);
        return usuarioService.buscarPorId(usuarioLogado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Apenas admin pode listar todos os usuários (se necessário)
    @GetMapping
    public ResponseEntity<List<UsuarioModel>> listarUsuarios() {
        List<UsuarioModel> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    // Usuário só pode atualizar seus próprios dados
    @PutMapping("/me")
    public ResponseEntity<UsuarioModel> atualizarMeuPerfil(
            @Valid @RequestBody UsuarioUpdateDTO usuarioDTO,
            HttpServletRequest request) {
        
        UUID usuarioLogado = authUtil.extractUserIdFromRequest(request);
        
        UsuarioModel atualizado = usuarioService.atualizarUsuario(
            usuarioLogado,
            usuarioDTO.getNome(),
            usuarioDTO.getEmail(),
            usuarioDTO.getSenha()
        );
        
        return ResponseEntity.ok(atualizado);
    }

    // Usuário só pode deletar sua própria conta
    @DeleteMapping("/me")
    public ResponseEntity<Void> deletarMinhaConta(HttpServletRequest request) {
        UUID usuarioLogado = authUtil.extractUserIdFromRequest(request);
        usuarioService.deletarUsuario(usuarioLogado);
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
