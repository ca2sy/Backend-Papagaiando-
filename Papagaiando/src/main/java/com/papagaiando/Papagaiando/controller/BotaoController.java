package com.papagaiando.Papagaiando.controller;

import com.papagaiando.Papagaiando.dto.BotaoCreateDTO;
import com.papagaiando.Papagaiando.dto.BotaoUpdateDTO;
import com.papagaiando.Papagaiando.model.BotaoModel;
import com.papagaiando.Papagaiando.service.BotaoService;
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
@RequestMapping("/botoes")
public class BotaoController {

    @Autowired
    private BotaoService botaoService;
    
    @Autowired
    private AuthUtil authUtil;

    // Criar botão personalizado
    @PostMapping("/personalizados")
    public ResponseEntity<BotaoModel> criarBotaoPersonalizado(
            @Valid @RequestBody BotaoCreateDTO dto,
            HttpServletRequest request) {
        
        UUID usuarioLogado = authUtil.extractUserIdFromRequest(request);
        
        BotaoModel criado = botaoService.criarBotaoPersonalizado(
            dto.getNome(),
            dto.getUrlImagem(),
            dto.getUrlAudio(),
            dto.getCategoriaId(),
            usuarioLogado
        );
        
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    // Criar botão padrão (apenas admin)
    @PostMapping("/padrao")
    public ResponseEntity<BotaoModel> criarBotaoPadrao(@Valid @RequestBody BotaoCreateDTO dto) {
        BotaoModel criado = botaoService.criarBotaoPadrao(
            dto.getNome(),
            dto.getUrlImagem(),
            dto.getUrlAudio(),
            dto.getCategoriaId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<BotaoModel>> listarPorCategoria(
            @PathVariable UUID categoriaId,
            HttpServletRequest request) {
        
        UUID usuarioLogado = authUtil.extractUserIdFromRequest(request);
        
        List<BotaoModel> botoes = botaoService.listarPorCategoria(categoriaId, usuarioLogado);
        return ResponseEntity.ok(botoes);
    }

    @GetMapping("/categoria/{categoriaId}/padrao")
    public ResponseEntity<List<BotaoModel>> listarBotoesPadrao(@PathVariable UUID categoriaId) {
        List<BotaoModel> botoes = botaoService.listarBotoesPadraoPorCategoria(categoriaId);
        return ResponseEntity.ok(botoes);
    }

    @GetMapping("/categoria/{categoriaId}/personalizados")
    public ResponseEntity<List<BotaoModel>> listarBotoesPersonalizados(
            @PathVariable UUID categoriaId,
            HttpServletRequest request) {
        
        UUID usuarioLogado = authUtil.extractUserIdFromRequest(request);
        
        List<BotaoModel> botoes = botaoService.listarBotoesPersonalizadosPorCategoria(categoriaId, usuarioLogado);
        return ResponseEntity.ok(botoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BotaoModel> buscarPorId(
            @PathVariable UUID id,
            HttpServletRequest request) {
        
        UUID usuarioLogado = authUtil.extractUserIdFromRequest(request);
        
        BotaoModel botao = botaoService.buscarPorId(id, usuarioLogado);
        return ResponseEntity.ok(botao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BotaoModel> atualizarBotao(
            @PathVariable UUID id,
            @Valid @RequestBody BotaoUpdateDTO dto,
            HttpServletRequest request) {
        
        UUID usuarioLogado = authUtil.extractUserIdFromRequest(request);
        
        BotaoModel botaoAtualizado = botaoService.atualizarBotao(
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