package com.papagaiando.Papagaiando.controller;

import com.papagaiando.Papagaiando.dto.PerfilCreateDTO;
import com.papagaiando.Papagaiando.dto.PerfilUpdateDTO;
import com.papagaiando.Papagaiando.model.PerfilModel;
import com.papagaiando.Papagaiando.service.PerfilService;
import com.papagaiando.Papagaiando.security.AuthUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/perfis")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;
    
    @Autowired
    private AuthUtil authUtil;

    @PostMapping
    public ResponseEntity<PerfilModel> criarPerfil(
            @Valid @RequestBody PerfilCreateDTO dto,
            HttpServletRequest request) {
        
        UUID usuarioLogado = authUtil.extractUserIdFromRequest(request);
        
        // Verifica se está tentando criar perfil para si mesmo
        if (!dto.getUsuarioId().equals(usuarioLogado)) {
            throw new RuntimeException("Você só pode criar perfis para sua própria conta");
        }
        
        PerfilModel perfilCriado = perfilService.criarPerfil(
            dto.getNome(),
            dto.getUrlFoto(),
            usuarioLogado // Usa o ID do token, não do DTO
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(perfilCriado);
    }

    @GetMapping("/me")
    public ResponseEntity<List<PerfilModel>> listarMeusPerfis(HttpServletRequest request) {
        UUID usuarioLogado = authUtil.extractUserIdFromRequest(request);
        List<PerfilModel> perfis = perfilService.listarPerfisPorUsuario(usuarioLogado);
        return ResponseEntity.ok(perfis);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerfilModel> buscarPorId(
            @PathVariable UUID id,
            HttpServletRequest request) {
        
        UUID usuarioLogado = authUtil.extractUserIdFromRequest(request);
        PerfilModel perfil = perfilService.buscarPorId(id, usuarioLogado);
        return ResponseEntity.ok(perfil);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PerfilModel> atualizarPerfil(
            @PathVariable UUID id,
            @Valid @RequestBody PerfilUpdateDTO dto,
            HttpServletRequest request) {
        
        UUID usuarioLogado = authUtil.extractUserIdFromRequest(request);
        
        PerfilModel atualizado = perfilService.atualizarPerfil(
            id, 
            dto.getNome(), 
            dto.getUrlFoto(),
            usuarioLogado
        );
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPerfil(
            @PathVariable UUID id,
            HttpServletRequest request) {
        
        UUID usuarioLogado = authUtil.extractUserIdFromRequest(request);
        perfilService.deletarPerfil(id, usuarioLogado);
        return ResponseEntity.noContent().build();
    }
}