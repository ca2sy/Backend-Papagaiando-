package com.papagaiando.Papagaiando.controller;

import com.papagaiando.Papagaiando.dto.BotaoCreateDTO;
import com.papagaiando.Papagaiando.dto.BotaoUpdateDTO;
import com.papagaiando.Papagaiando.model.BotaoModel;
import com.papagaiando.Papagaiando.service.BotaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/botoes")
public class BotaoController {

    @Autowired
    private BotaoService botaoService;

    @PostMapping
    public ResponseEntity<BotaoModel> criarBotao(@Valid @RequestBody BotaoCreateDTO botaoDTO) {
        BotaoModel botaoCriado = botaoService.criarBotao(
            botaoDTO.getNome(),
            botaoDTO.getUrlImagem(),
            botaoDTO.getUrlAudio()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(botaoCriado);
    }

    @GetMapping
    public ResponseEntity<List<BotaoModel>> listarBotoes() {
        List<BotaoModel> botoes = botaoService.listarBotoes();
        return ResponseEntity.ok(botoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BotaoModel> buscarPorId(@PathVariable UUID id) {
        Optional<BotaoModel> botao = botaoService.buscarPorId(id);
        return botao.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<BotaoModel>> buscarPorNome(@RequestParam String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(botaoService.buscarPorNome(nome));
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarBotao(@PathVariable UUID id) {
        botaoService.deletarBotao(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
public ResponseEntity<BotaoModel> atualizarBotao(
    @PathVariable UUID id,
    @Valid @RequestBody BotaoUpdateDTO dto
) {
    BotaoModel botaoAtualizado = botaoService.atualizarBotao(
        id,
        dto.getNome(),
        dto.getUrlImagem(),
        dto.getUrlAudio()
    );
    return ResponseEntity.ok(botaoAtualizado);
}
}