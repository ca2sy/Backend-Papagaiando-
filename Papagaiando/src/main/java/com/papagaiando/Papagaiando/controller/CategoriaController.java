package com.papagaiando.Papagaiando.controller;

import com.papagaiando.Papagaiando.dto.CategoriaCreateDTO;
import com.papagaiando.Papagaiando.dto.CategoriaUpdateDTO;
import com.papagaiando.Papagaiando.model.CategoriaModel;
import com.papagaiando.Papagaiando.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<CategoriaModel> criarCategoria(@Valid @RequestBody CategoriaCreateDTO dto) {
        CategoriaModel categoriaCriada = categoriaService.criarCategoria(
            dto.getNome(),
            dto.getUrlImagem(),
            dto.getPerfilId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaCriada);
    }

    @GetMapping("/perfil/{perfilId}")
    public ResponseEntity<List<CategoriaModel>> listarPorPerfil(@PathVariable UUID perfilId) {
        List<CategoriaModel> categorias = categoriaService.listarPorPerfilId(perfilId);
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaModel> buscarPorId(@PathVariable UUID id) {
        return categoriaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<CategoriaModel>> buscarPorNome(@RequestParam String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(categoriaService.buscarPorNome(nome));
    }

    @GetMapping("/perfil/{perfilId}/buscar")
    public ResponseEntity<List<CategoriaModel>> buscarPorNomePerfil(
            @PathVariable UUID perfilId,
            @RequestParam String nome) {
        
        if (nome == null || nome.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        List<CategoriaModel> categorias = categoriaService.buscarPorNomePerfilId(perfilId, nome);
        return ResponseEntity.ok(categorias);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaModel> atualizarCategoria(
            @PathVariable UUID id,
            @Valid @RequestBody CategoriaUpdateDTO dto) {
        
        CategoriaModel atualizada = categoriaService.atualizarCategoria(
            id,
            dto.getNome(),
            dto.getUrlImagem()
        );
        
        return ResponseEntity.ok(atualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable UUID id) {
        categoriaService.deletarCategoria(id);
        return ResponseEntity.noContent().build();
    }


}