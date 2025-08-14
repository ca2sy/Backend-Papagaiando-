package com.papagaiando.Papagaiando.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.papagaiando.Papagaiando.model.BotaoPersonalizadoModel;
import com.papagaiando.Papagaiando.model.PerfilModel;
import com.papagaiando.Papagaiando.service.BotaoPersonalizadoService;

@RestController
@RequestMapping("/botoes/personalizados")
public class BotaoPersonalizadoController {

    @Autowired
    private BotaoPersonalizadoService botaoService;

    // Criar botão personalizado
    @PostMapping("/criar")
    public ResponseEntity<BotaoPersonalizadoModel> criarBotao(
            @RequestParam String nome,
            @RequestParam String urlImagem,
            @RequestParam String urlAudio,
            @RequestBody PerfilModel perfil) {
        BotaoPersonalizadoModel criado = botaoService.criarBotao(nome, urlImagem, urlAudio, perfil);
        return ResponseEntity.ok(criado);
    }

    // Listar todos os botões de um perfil
    @GetMapping("/perfil/{perfilId}")
    public ResponseEntity<List<BotaoPersonalizadoModel>> listarPorPerfil(@PathVariable UUID perfilId,
                                                                          @RequestBody PerfilModel perfil) {
        return ResponseEntity.ok(botaoService.listarPorPerfil(perfil));
    }

    // Buscar botão por ID
    @GetMapping("/{id}")
    public ResponseEntity<BotaoPersonalizadoModel> buscarPorId(@PathVariable UUID id) {
        Optional<BotaoPersonalizadoModel> botao = botaoService.buscarPorId(id);
        return botao.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    // Buscar botões de um perfil por nome
    @GetMapping("/perfil/{perfilId}/buscar")
    public ResponseEntity<List<BotaoPersonalizadoModel>> buscarPorNome(
            @PathVariable UUID perfilId,
            @RequestParam String nome,
            @RequestBody PerfilModel perfil) {
        return ResponseEntity.ok(botaoService.buscarPorNome(perfil, nome));
    }

    // Deletar botão
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarBotao(@PathVariable UUID id) {
        botaoService.deletarBotao(id);
        return ResponseEntity.noContent().build();
    }
}
