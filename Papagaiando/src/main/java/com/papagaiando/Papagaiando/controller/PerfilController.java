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

    
    @PostMapping("/criar")
    public ResponseEntity<PerfilModel> criarPerfil(
            @RequestParam String nome,
            @RequestParam int idade,
            @RequestBody UsuarioModel usuario) { // Recebe o usuário como JSON
        PerfilModel perfilCriado = perfilService.criarPerfil(nome, idade, usuario);
        return ResponseEntity.ok(perfilCriado);
    }

  
    @GetMapping
    public ResponseEntity<List<PerfilModel>> listarPerfis() {
        List<PerfilModel> perfis = perfilService.listarPerfis();
        return ResponseEntity.ok(perfis);
    }

 
    @GetMapping("/{id}")
    public ResponseEntity<PerfilModel> buscarPorId(@PathVariable UUID id) {
        Optional<PerfilModel> perfil = perfilService.buscarPorId(id);
        return perfil.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

  
    @PutMapping("/{id}")
    public ResponseEntity<PerfilModel> atualizarPerfil(
            @PathVariable UUID id,
            @RequestParam String nome,
            @RequestParam int idade) {
        PerfilModel atualizado = perfilService.atualizarPerfil(id, nome, idade);
        if (atualizado != null) {
            return ResponseEntity.ok(atualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

  
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPerfil(@PathVariable UUID id) {
        perfilService.deletarPerfil(id);
        return ResponseEntity.noContent().build();
    }
}
