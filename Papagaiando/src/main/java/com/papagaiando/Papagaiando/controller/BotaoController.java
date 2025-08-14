package com.papagaiando.Papagaiando.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.papagaiando.Papagaiando.model.BotaoModel;
import com.papagaiando.Papagaiando.service.BotaoService;

@RestController
@RequestMapping("/botoes")
public class BotaoController {

    @Autowired
    private BotaoService botaoService;

    @PostMapping("/criar")
    public ResponseEntity<BotaoModel> criarBotao(
            @RequestParam String nome,
            @RequestParam String urlImagem,
            @RequestParam String urlAudio) {
        BotaoModel criado = botaoService.criarBotao(nome, urlImagem, urlAudio);
        return ResponseEntity.ok(criado);
    }

    @GetMapping
    public ResponseEntity<List<BotaoModel>> listarBotoes() {
        return ResponseEntity.ok(botaoService.listarBotoes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BotaoModel> buscarPorId(@PathVariable UUID id) {
        Optional<BotaoModel> botao = botaoService.buscarPorId(id);
        return botao.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<BotaoModel>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(botaoService.buscarPorNome(nome));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarBotao(@PathVariable UUID id) {
        botaoService.deletarBotao(id);
        return ResponseEntity.noContent().build();
    }
}
