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

    // Criar botão padrão
    public BotaoModel criarBotao(String nome, String urlImagem, String urlAudio) {
        BotaoModel botao = new BotaoModel(nome, urlImagem, urlAudio);
        return botaoRepository.save(botao);
    }

    // Listar todos os botões padrão
    public List<BotaoModel> listarBotoes() {
        return botaoRepository.findAll();
    }

    // Buscar botão padrão por ID
    public Optional<BotaoModel> buscarPorId(UUID id) {
        return botaoRepository.findById(id);
    }

    // Buscar botões padrão por nome
    public List<BotaoModel> buscarPorNome(String nome) {
        return botaoRepository.findByNomeContainingIgnoreCase(nome);
    }

    // Deletar botão padrão
    public void deletarBotao(UUID id) {
        botaoRepository.deleteById(id);
    }
}
