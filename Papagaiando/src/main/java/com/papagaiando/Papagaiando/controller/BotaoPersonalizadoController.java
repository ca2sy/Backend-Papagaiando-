package com.papagaiando.Papagaiando.controller;

import com.papagaiando.Papagaiando.dto.BotaoPersonalizadoCreateDTO;
import com.papagaiando.Papagaiando.dto.BotaoPersonalizadoUpdateDTO;
import com.papagaiando.Papagaiando.model.BotaoPersonalizadoModel;
import com.papagaiando.Papagaiando.service.BotaoPersonalizadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/botoes/personalizados")
public class BotaoPersonalizadoController {

    @Autowired
    private BotaoPersonalizadoService botaoService;

    @PostMapping
    public ResponseEntity<BotaoPersonalizadoModel> criarBotao(
            @Valid @RequestBody BotaoPersonalizadoCreateDTO dto) {
        
        BotaoPersonalizadoModel criado = botaoService.criarBotaoPorCategoriaId(
            dto.getNome(),
            dto.getUrlImagem(),
            dto.getUrlAudio(),
            dto.getCategoriaId() // Mudado de perfilId para categoriaId
        );
        
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<BotaoPersonalizadoModel>> listarPorCategoria(
            @PathVariable UUID categoriaId) {
        
        List<BotaoPersonalizadoModel> botoes = botaoService.listarPorCategoriaId(categoriaId);
        return ResponseEntity.ok(botoes);
    }

    @GetMapping("/categoria/{categoriaId}/buscar")
    public ResponseEntity<List<BotaoPersonalizadoModel>> buscarPorNome(
            @PathVariable UUID categoriaId,
            @RequestParam String nome) {
        
        if (nome == null || nome.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        List<BotaoPersonalizadoModel> botoes = botaoService.buscarPorNomeCategoriaId(categoriaId, nome);
        return ResponseEntity.ok(botoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BotaoPersonalizadoModel> buscarPorId(
            @PathVariable UUID id) {
        
        return botaoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarBotao(
            @PathVariable UUID id) {
        
        botaoService.deletarBotao(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BotaoPersonalizadoModel> atualizarBotaoPersonalizado(
        @PathVariable UUID id,
        @Valid @RequestBody BotaoPersonalizadoUpdateDTO dto
    ) {
        BotaoPersonalizadoModel botaoAtualizado = botaoService.atualizarBotaoPersonalizado(
            id,
            dto.getNome(),
            dto.getUrlImagem(),
            dto.getUrlAudio()
        );
        return ResponseEntity.ok(botaoAtualizado);
    }
}