package com.papagaiando.Papagaiando.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.papagaiando.Papagaiando.model.BotaoPersonalizadoModel;
import com.papagaiando.Papagaiando.model.CategoriaModel;
import com.papagaiando.Papagaiando.repository.BotaoPersonalizadoModelRepository;
import com.papagaiando.Papagaiando.repository.CategoriaRepository;

@Service
public class BotaoPersonalizadoService {

    @Autowired
    private BotaoPersonalizadoModelRepository botaoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public BotaoPersonalizadoModel criarBotaoPorCategoriaId(String nome, String urlImagem, String urlAudio, UUID categoriaId) {
        if (!categoriaRepository.existsById(categoriaId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria com ID " + categoriaId + " não encontrada");
        }
        
        CategoriaModel categoria = categoriaRepository.getReferenceById(categoriaId);
        return botaoRepository.save(new BotaoPersonalizadoModel(nome, urlImagem, urlAudio, categoria));
    }

    public List<BotaoPersonalizadoModel> listarPorCategoriaId(UUID categoriaId) {
        if (!categoriaRepository.existsById(categoriaId)) {
            throw new RuntimeException("Categoria não encontrada");
        }
        return botaoRepository.findByCategoriaId(categoriaId);
    }

    public List<BotaoPersonalizadoModel> buscarPorNomeCategoriaId(UUID categoriaId, String nome) {
        Optional<CategoriaModel> categoriaOpt = categoriaRepository.findById(categoriaId);
        return categoriaOpt.map(c -> botaoRepository.findByCategoriaAndNomeContainingIgnoreCase(c, nome))
                          .orElse(List.of());
    }

    public Optional<BotaoPersonalizadoModel> buscarPorId(UUID id) {
        return botaoRepository.findById(id);
    }

    public void deletarBotao(UUID id) {
        botaoRepository.deleteById(id);
    }

    public BotaoPersonalizadoModel atualizarBotaoPersonalizado(
        UUID id, String nome, String urlImagem, String urlAudio
    ) {
        BotaoPersonalizadoModel botao = buscarPorId(id).orElseThrow(() -> 
            new RuntimeException("Botão personalizado não encontrado"));
        
        if (nome != null) botao.setNome(nome);
        if (urlImagem != null) botao.setUrlImagem(urlImagem);
        if (urlAudio != null) botao.setUrlAudio(urlAudio);
        
        return botaoRepository.save(botao);
    }
}