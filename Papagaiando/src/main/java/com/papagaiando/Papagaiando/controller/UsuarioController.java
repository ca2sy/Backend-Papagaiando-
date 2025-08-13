package com.papagaiando.Papagaiando.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.papagaiando.Papagaiando.model.UsuarioModel;
import com.papagaiando.Papagaiando.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Criar usuário
    @PostMapping("/criar")
    public ResponseEntity<UsuarioModel> criarUsuario(
            @RequestParam String nome,
            @RequestParam String email,
            @RequestParam String senha) {
        UsuarioModel usuarioCriado = usuarioService.registrarUsuario(email, nome, senha);
        return ResponseEntity.ok(usuarioCriado);
    }

    // Listar todos os usuários
    @GetMapping
    public ResponseEntity<List<UsuarioModel>> listarUsuarios() {
        List<UsuarioModel> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    // Buscar usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioModel> buscarPorId(@PathVariable UUID id) {
        Optional<UsuarioModel> usuario = usuarioService.buscarPorId(id);
        return usuario.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    // Atualizar usuário
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioModel> atualizarUsuario(
            @PathVariable UUID id,
            @RequestParam String nome,
            @RequestParam String email,
            @RequestParam String senha) {
        UsuarioModel atualizado = usuarioService.atualizarUsuario(id, nome, email, senha);
        if (atualizado != null) {
            return ResponseEntity.ok(atualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Deletar usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable UUID id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    // Esqueci minha senha
    @PostMapping("/esqueci-senha")
    public ResponseEntity<String> esqueciSenha(@RequestParam String email) {
        String token = usuarioService.gerarTokenRecuperacao(email);
        if (token != null) {
            // Aqui você pode enviar o token por e-mail
            // emailService.enviarToken(email, token);
            return ResponseEntity.ok("E-mail de recuperação enviado!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
    }

    // Redefinir senha
    @PostMapping("/redefinir-senha")
    public ResponseEntity<String> redefinirSenha(
            @RequestParam String token,
            @RequestParam String novaSenha) {
        boolean sucesso = usuarioService.redefinirSenha(token, novaSenha);
        if (sucesso) {
            return ResponseEntity.ok("Senha redefinida com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token inválido ou expirado");
        }
    }
}