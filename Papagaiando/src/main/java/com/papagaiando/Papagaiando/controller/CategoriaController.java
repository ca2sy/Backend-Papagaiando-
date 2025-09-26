package com.papagaiando.Papagaiando.controller;

import com.papagaiando.Papagaiando.dto.CategoriaCreateDTO;
import com.papagaiando.Papagaiando.dto.CategoriaUpdateDTO;
import com.papagaiando.Papagaiando.model.CategoriaModel;
import com.papagaiando.Papagaiando.service.CategoriaService;
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
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;
    
    @Autowired
    private AuthUtil authUtil;

    @PostMapping
    public ResponseEntity<CategoriaModel> criarCategoria(
            @Valid @RequestBody CategoriaCreateDTO dto,
            HttpServletRequest request) {
        
        UUID usuarioLogado = authUtil.extractUserIdFromRequest(request);
        
        CategoriaModel categoriaCriada = categoriaService.criarCategoria(
            dto.getNome(),
            dto.getUrlImagem(),
            dto.getPerfilId(),
            usuarioLogado
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaCriada);
    }


    @GetMapping("/padrao")
public ResponseEntity<List<CategoriaModel>> listarCategoriasPadrao() {
    List<CategoriaModel> padrao = categoriaService.listarCategoriasPadrao();
    return ResponseEntity.ok(padrao);
}


@GetMapping("/perfil/{perfilId}")
public ResponseEntity<List<CategoriaModel>> listarCategoriasPerfil(@PathVariable UUID perfilId,
                                                                  HttpServletRequest request) {
    UUID usuarioLogado = authUtil.extractUserIdFromRequest(request);

    List<CategoriaModel> categorias = categoriaService.listarCategoriasPerfilComPadrao(perfilId, usuarioLogado);
    return ResponseEntity.ok(categorias);
}

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaModel> buscarPorId(
            @PathVariable UUID id,
            HttpServletRequest request) {
        
        UUID usuarioLogado = authUtil.extractUserIdFromRequest(request);
        
        CategoriaModel categoria = categoriaService.buscarPorId(id, usuarioLogado);
        return ResponseEntity.ok(categoria);
    }

    @GetMapping("/perfil/{perfilId}/buscar")
    public ResponseEntity<List<CategoriaModel>> buscarPorNomePerfil(
            @PathVariable UUID perfilId,
            @RequestParam String nome,
            HttpServletRequest request) {
        
        if (nome == null || nome.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        UUID usuarioLogado = authUtil.extractUserIdFromRequest(request);
        
        List<CategoriaModel> categorias = categoriaService.buscarPorNomePerfil(perfilId, nome, usuarioLogado);
        return ResponseEntity.ok(categorias);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaModel> atualizarCategoria(
            @PathVariable UUID id,
            @Valid @RequestBody CategoriaUpdateDTO dto,
            HttpServletRequest request) {
        
        UUID usuarioLogado = authUtil.extractUserIdFromRequest(request);
        
        CategoriaModel atualizada = categoriaService.atualizarCategoria(
            id,
            dto.getNome(),
            dto.getUrlImagem(),
            usuarioLogado
        );
        
        return ResponseEntity.ok(atualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCategoria(
            @PathVariable UUID id,
            HttpServletRequest request) {
        
        UUID usuarioLogado = authUtil.extractUserIdFromRequest(request);
        
        categoriaService.deletarCategoria(id, usuarioLogado);
        return ResponseEntity.noContent().build();
    }
}