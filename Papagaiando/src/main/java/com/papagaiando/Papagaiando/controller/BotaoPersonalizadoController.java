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
        
        BotaoPersonalizadoModel criado = botaoService.criarBotaoPorPerfilId(
            dto.getNome(),
            dto.getUrlImagem(),
            dto.getUrlAudio(),
            dto.getPerfilId()
        );
        
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping("/perfil/{perfilId}")
    public ResponseEntity<List<BotaoPersonalizadoModel>> listarPorPerfil(
            @PathVariable UUID perfilId) {
        
        List<BotaoPersonalizadoModel> botoes = botaoService.listarPorPerfilId(perfilId);
    return ResponseEntity.ok(botoes); 
    }

    @GetMapping("/perfil/{perfilId}/buscar")
    public ResponseEntity<List<BotaoPersonalizadoModel>> buscarPorNome(
            @PathVariable UUID perfilId,
            @RequestParam String nome) {
        
        if (nome == null || nome.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        List<BotaoPersonalizadoModel> botoes = botaoService.buscarPorNomeId(perfilId, nome);
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