package com.papagaiando.Papagaiando.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.papagaiando.Papagaiando.model.PerfilModel;
import com.papagaiando.Papagaiando.model.UsuarioModel;
import com.papagaiando.Papagaiando.service.PerfilService;

@RestController
@RequestMapping("/perfis")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    // Criar perfil
   @PostMapping("/criar")
public ResponseEntity<PerfilModel> criarPerfil(
        @RequestParam String nome,
        @RequestParam String urlFoto,
        @RequestParam UUID usuarioId) {  // 
    PerfilModel perfilCriado = perfilService.criarPerfilPorId(nome, urlFoto, usuarioId);
    return ResponseEntity.ok(perfilCriado);
}

    // Listar perfis
    @GetMapping
    public ResponseEntity<List<PerfilModel>> listarPerfis() {
        List<PerfilModel> perfis = perfilService.listarPerfis();
        return ResponseEntity.ok(perfis);
    }

    // Buscar perfil por ID
    @GetMapping("/{id}")
    public ResponseEntity<PerfilModel> buscarPorId(@PathVariable UUID id) {
        Optional<PerfilModel> perfil = perfilService.buscarPorId(id);
        return perfil.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    // Atualizar perfil (nome e foto opcionais)
    @PutMapping("/{id}")
    public ResponseEntity<PerfilModel> atualizarPerfil(
            @PathVariable UUID id,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String urlFoto) {

        PerfilModel atualizado = perfilService.atualizarPerfil(id, nome, urlFoto);
        if (atualizado != null) {
            return ResponseEntity.ok(atualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Deletar perfil
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPerfil(@PathVariable UUID id) {
        perfilService.deletarPerfil(id);
        return ResponseEntity.noContent().build();
    }
}
