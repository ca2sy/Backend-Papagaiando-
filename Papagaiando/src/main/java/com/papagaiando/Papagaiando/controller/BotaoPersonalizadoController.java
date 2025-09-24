package com.papagaiando.Papagaiando.controller;

import com.papagaiando.Papagaiando.dto.BotaoPersonalizadoCreateDTO;
import com.papagaiando.Papagaiando.dto.BotaoPersonalizadoUpdateDTO;
import com.papagaiando.Papagaiando.model.BotaoPersonalizadoModel;
import com.papagaiando.Papagaiando.service.BotaoPersonalizadoService;
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
@RequestMapping("/botoes/personalizados")
public class BotaoPersonalizadoController {

    @Autowired
    private BotaoPersonalizadoService botaoService;
    
    @Autowired
    private AuthUtil authUtil;

    @PostMapping
    public ResponseEntity<BotaoPersonalizadoModel> criarBotao(
            @Valid @RequestBody BotaoPersonalizadoCreateDTO dto,
            HttpServletRequest request) {
        
        UUID usuarioLogado = authUtil.extractUserIdFromRequest(request);
        
        BotaoPersonalizadoModel criado = botaoService.criarBotaoPersonalizado(
            dto.getNome(),
            dto.getUrlImagem(),
            dto.getUrlAudio(),
            dto.getCategoriaId(),
            usuarioLogado
        );
        
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<BotaoPersonalizadoModel>> listarPorCategoria(
            @PathVariable UUID categoriaId,
            HttpServletRequest request) {
        
        UUID usuarioLogado = authUtil.extractUserIdFromRequest(request);
        
        List<BotaoPersonalizadoModel> botoes = botaoService.listarPorCategoria(categoriaId, usuarioLogado);
        return ResponseEntity.ok(botoes);
    }

    @GetMapping("/categoria/{categoriaId}/buscar")
    public ResponseEntity<List<BotaoPersonalizadoModel>> buscarPorNome(
            @PathVariable UUID categoriaId,
            @RequestParam String nome,
            HttpServletRequest request) {
        
        if (nome == null || nome.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        UUID usuarioLogado = authUtil.extractUserIdFromRequest(request);
        
        List<BotaoPersonalizadoModel> botoes = botaoService.buscarPorNomeCategoria(categoriaId, nome, usuarioLogado);
        return ResponseEntity.ok(botoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BotaoPersonalizadoModel> buscarPorId(
            @PathVariable UUID id,
            HttpServletRequest request) {
        
        UUID usuarioLogado = authUtil.extractUserIdFromRequest(request);
        
        BotaoPersonalizadoModel botao = botaoService.buscarPorId(id, usuarioLogado);
        return ResponseEntity.ok(botao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BotaoPersonalizadoModel> atualizarBotaoPersonalizado(
        @PathVariable UUID id,
        @Valid @RequestBody BotaoPersonalizadoUpdateDTO dto,
        HttpServletRequest request
    ) {
        UUID usuarioLogado = authUtil.extractUserIdFromRequest(request);
        
        BotaoPersonalizadoModel botaoAtualizado = botaoService.atualizarBotaoPersonalizado(
            id,
            dto.getNome(),
            dto.getUrlImagem(),
            dto.getUrlAudio(),
            usuarioLogado
        );
        return ResponseEntity.ok(botaoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarBotao(
            @PathVariable UUID id,
            HttpServletRequest request) {
        
        UUID usuarioLogado = authUtil.extractUserIdFromRequest(request);
        
        botaoService.deletarBotao(id, usuarioLogado);
        return ResponseEntity.noContent().build();
    }
}