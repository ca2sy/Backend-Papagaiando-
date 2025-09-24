package com.papagaiando.Papagaiando.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.papagaiando.Papagaiando.model.BotaoPersonalizadoModel;
import com.papagaiando.Papagaiando.model.CategoriaModel;
import com.papagaiando.Papagaiando.repository.BotaoPersonalizadoModelRepository;
import com.papagaiando.Papagaiando.repository.CategoriaRepository;
import com.papagaiando.Papagaiando.exception.ResourceNotFoundException;

@Service
public class BotaoPersonalizadoService {

    @Autowired
    private BotaoPersonalizadoModelRepository botaoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Autowired
    private AuthorizationService authorizationService;

    public BotaoPersonalizadoModel criarBotaoPersonalizado(
            String nome, String urlImagem, String urlAudio, UUID categoriaId, UUID usuarioLogado) {
        
        // Valida que a categoria pertence ao usuário logado
        authorizationService.validarCriacaoBotaoPersonalizado(categoriaId, usuarioLogado);
        
        CategoriaModel categoria = categoriaRepository.findById(categoriaId)
            .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
        
        return botaoRepository.save(new BotaoPersonalizadoModel(nome, urlImagem, urlAudio, categoria));
    }

    public List<BotaoPersonalizadoModel> listarPorCategoria(UUID categoriaId, UUID usuarioLogado) {
        authorizationService.validarPropriedadeCategoria(categoriaId, usuarioLogado);
        return botaoRepository.findByCategoriaId(categoriaId);
    }

    public List<BotaoPersonalizadoModel> buscarPorNomeCategoria(
            UUID categoriaId, String nome, UUID usuarioLogado) {
        
        authorizationService.validarPropriedadeCategoria(categoriaId, usuarioLogado);
        
        CategoriaModel categoria = categoriaRepository.findById(categoriaId)
            .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
        
        return botaoRepository.findByCategoriaAndNomeContainingIgnoreCase(categoria, nome);
    }

    public BotaoPersonalizadoModel buscarPorId(UUID botaoId, UUID usuarioLogado) {
        authorizationService.validarPropriedadeBotaoPersonalizado(botaoId, usuarioLogado);
        
        return botaoRepository.findById(botaoId)
            .orElseThrow(() -> new ResourceNotFoundException("Botão personalizado não encontrado"));
    }

    public BotaoPersonalizadoModel atualizarBotaoPersonalizado(
            UUID botaoId, String nome, String urlImagem, String urlAudio, UUID usuarioLogado) {
        
        authorizationService.validarPropriedadeBotaoPersonalizado(botaoId, usuarioLogado);
        
        BotaoPersonalizadoModel botao = botaoRepository.findById(botaoId)
            .orElseThrow(() -> new ResourceNotFoundException("Botão personalizado não encontrado"));
        
        if (nome != null && !nome.isBlank()) botao.setNome(nome);
        if (urlImagem != null && !urlImagem.isBlank()) botao.setUrlImagem(urlImagem);
        if (urlAudio != null && !urlAudio.isBlank()) botao.setUrlAudio(urlAudio);
        
        return botaoRepository.save(botao);
    }

    public void deletarBotao(UUID botaoId, UUID usuarioLogado) {
        authorizationService.validarPropriedadeBotaoPersonalizado(botaoId, usuarioLogado);
        botaoRepository.deleteById(botaoId);
    }
}