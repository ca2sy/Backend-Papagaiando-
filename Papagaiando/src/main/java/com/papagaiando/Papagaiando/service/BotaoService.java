package com.papagaiando.Papagaiando.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.papagaiando.Papagaiando.model.BotaoModel;
import com.papagaiando.Papagaiando.repository.BotaoModelRepository;

@Service
public class BotaoService {

    @Autowired
    private BotaoModelRepository botaoRepository;

    public BotaoModel criarBotao(String nome, String urlImagem, String urlAudio) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do botão é obrigatório");
        }
        BotaoModel botao = new BotaoModel(nome, urlImagem, urlAudio);
        return botaoRepository.save(botao);
    }

    public List<BotaoModel> listarBotoes() {
        return botaoRepository.findAll();
    }

    public Optional<BotaoModel> buscarPorId(UUID id) {
        return botaoRepository.findById(id);
    }

    public List<BotaoModel> buscarPorNome(String nome) {
        return botaoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public void deletarBotao(UUID id) {
        botaoRepository.deleteById(id);
    }

    public BotaoModel atualizarBotao(UUID id, String nome, String urlImagem, String urlAudio) {
        BotaoModel botao = buscarPorId(id).orElseThrow(() -> 
            new RuntimeException("Botão não encontrado"));
        
        if (nome != null) botao.setNome(nome);
        if (urlImagem != null) botao.setUrlImagem(urlImagem);
        if (urlAudio != null) botao.setUrlAudio(urlAudio);
        
        return botaoRepository.save(botao);
    }
}