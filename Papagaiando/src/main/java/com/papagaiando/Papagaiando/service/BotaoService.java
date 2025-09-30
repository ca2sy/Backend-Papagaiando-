package com.papagaiando.Papagaiando.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.papagaiando.Papagaiando.model.BotaoModel;
import com.papagaiando.Papagaiando.model.CategoriaModel;
import com.papagaiando.Papagaiando.repository.BotaoRepository;
import com.papagaiando.Papagaiando.repository.CategoriaRepository;
import com.papagaiando.Papagaiando.exception.ResourceNotFoundException;

@Service
public class BotaoService {

    @Autowired
    private BotaoRepository botaoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Autowired
    private AuthorizationService authorizationService;

    // Criar botão padrão (apenas admin)
    public BotaoModel criarBotaoPadrao(String nome, String urlImagem, String urlAudio, UUID categoriaId) {
        CategoriaModel categoria = categoriaRepository.findById(categoriaId)
            .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
        
        BotaoModel botao = new BotaoModel(nome, urlImagem, urlAudio, categoria);
        return botaoRepository.save(botao);
    }

    // Criar botão personalizado
    public BotaoModel criarBotaoPersonalizado(String nome, String urlImagem, String urlAudio, UUID categoriaId, UUID usuarioLogado) {
        CategoriaModel categoria = categoriaRepository.findById(categoriaId)
            .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
        
        // Valida se o usuário pode criar botão nesta categoria
        if (!categoria.isPadrao()) {
            authorizationService.validarPropriedadeCategoria(categoriaId, usuarioLogado);
        }
        
        BotaoModel botao = new BotaoModel(nome, urlImagem, urlAudio, categoria, true);
        return botaoRepository.save(botao);
    }

    public List<BotaoModel> listarPorCategoria(UUID categoriaId, UUID usuarioLogado) {
        CategoriaModel categoria = categoriaRepository.findById(categoriaId)
            .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
        
        if (!categoria.isPadrao()) {
            authorizationService.validarPropriedadeCategoria(categoriaId, usuarioLogado);
        }
        
        return botaoRepository.findByCategoriaId(categoriaId);
    }

    public List<BotaoModel> listarBotoesPadraoPorCategoria(UUID categoriaId) {
        return botaoRepository.findByCategoriaIdAndPadrao(categoriaId, true);
    }

    public List<BotaoModel> listarBotoesPersonalizadosPorCategoria(UUID categoriaId, UUID usuarioLogado) {
        authorizationService.validarPropriedadeCategoria(categoriaId, usuarioLogado);
        return botaoRepository.findByCategoriaIdAndPadrao(categoriaId, false);
    }

    public BotaoModel buscarPorId(UUID botaoId, UUID usuarioLogado) {
        BotaoModel botao = botaoRepository.findById(botaoId)
            .orElseThrow(() -> new ResourceNotFoundException("Botão não encontrado"));
        
        // Se for botão personalizado, valida propriedade
        if (!botao.isPadrao()) {
            authorizationService.validarPropriedadeBotao(botaoId, usuarioLogado);
        }
        
        return botao;
    }

    public BotaoModel atualizarBotao(UUID botaoId, String nome, String urlImagem, String urlAudio, UUID usuarioLogado) {
        BotaoModel botao = botaoRepository.findById(botaoId)
            .orElseThrow(() -> new ResourceNotFoundException("Botão não encontrado"));
        
        // Se for botão personalizado, valida propriedade
        if (!botao.isPadrao()) {
            authorizationService.validarPropriedadeBotao(botaoId, usuarioLogado);
        }
        
        if (nome != null && !nome.isBlank()) botao.setNome(nome);
        if (urlImagem != null && !urlImagem.isBlank()) botao.setUrlImagem(urlImagem);
        if (urlAudio != null && !urlAudio.isBlank()) botao.setUrlAudio(urlAudio);
        
        return botaoRepository.save(botao);
    }

    public void deletarBotao(UUID botaoId, UUID usuarioLogado) {
        BotaoModel botao = botaoRepository.findById(botaoId)
            .orElseThrow(() -> new ResourceNotFoundException("Botão não encontrado"));
        
        // Se for botão personalizado, valida propriedade
        if (!botao.isPadrao()) {
            authorizationService.validarPropriedadeBotao(botaoId, usuarioLogado);
        }
        
        botaoRepository.deleteById(botaoId);
    }
}