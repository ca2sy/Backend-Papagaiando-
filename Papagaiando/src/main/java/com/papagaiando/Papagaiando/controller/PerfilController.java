package com.papagaiando.Papagaiando.controller;

import com.papagaiando.Papagaiando.dto.PerfilCreateDTO;
import com.papagaiando.Papagaiando.dto.PerfilUpdateDTO;
import com.papagaiando.Papagaiando.model.PerfilModel;
import com.papagaiando.Papagaiando.service.PerfilService;
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

    @PostMapping
    public ResponseEntity<PerfilModel> criarPerfil(
            @Valid @RequestBody PerfilCreateDTO dto) {
        PerfilModel perfilCriado = perfilService.criarPerfilPorId(
            dto.getNome(),
            dto.getUrlFoto(),
            dto.getUsuarioId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(perfilCriado);
    }

    @GetMapping
    public ResponseEntity<List<PerfilModel>> listarPerfis() {
        List<PerfilModel> perfis = perfilService.listarPerfis();
        return ResponseEntity.ok(perfis);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PerfilModel>> listarPerfisPorUsuario(@PathVariable UUID usuarioId) {
        List<PerfilModel> perfis = perfilService.listarPerfisPorUsuarioId(usuarioId);
        return ResponseEntity.ok(perfis);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerfilModel> buscarPorId(@PathVariable UUID id) {
        return perfilService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PerfilModel> atualizarPerfil(
            @PathVariable UUID id,
            @Valid @RequestBody PerfilUpdateDTO dto) {
        
        PerfilModel atualizado = perfilService.atualizarPerfil(
            id, 
            dto.getNome(), 
            dto.getUrlFoto()
        );
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPerfil(@PathVariable UUID id) {
        perfilService.deletarPerfil(id);
        return ResponseEntity.noContent().build();
    }
}