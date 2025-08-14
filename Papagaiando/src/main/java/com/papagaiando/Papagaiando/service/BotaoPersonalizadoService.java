package com.papagaiando.Papagaiando.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.papagaiando.Papagaiando.model.BotaoPersonalizadoModel;
import com.papagaiando.Papagaiando.model.PerfilModel;
import com.papagaiando.Papagaiando.repository.BotaoPersonalizadoModelRepository;

@Service
public class BotaoPersonalizadoService {

    @Autowired
    private BotaoPersonalizadoModelRepository botaoPersonalizadoRepository;

    // Criar botão personalizado
    public BotaoPersonalizadoModel criarBotao(String nome, String urlImagem, String urlAudio, PerfilModel perfil) {
        BotaoPersonalizadoModel botao = new BotaoPersonalizadoModel(nome, urlImagem, urlAudio, perfil);
        return botaoPersonalizadoRepository.save(botao);
    }

    // Listar todos os botões de um perfil
    public List<BotaoPersonalizadoModel> listarPorPerfil(PerfilModel perfil) {
        return botaoPersonalizadoRepository.findByPerfil(perfil);
    }

    // Buscar botão personalizado por ID
    public Optional<BotaoPersonalizadoModel> buscarPorId(UUID id) {
        return botaoPersonalizadoRepository.findById(id);
    }

    // Buscar botões de um perfil por nome
    public List<BotaoPersonalizadoModel> buscarPorNome(PerfilModel perfil, String nome) {
        return botaoPersonalizadoRepository.findByPerfilAndNomeContainingIgnoreCase(perfil, nome);
    }

    // Deletar botão personalizado
    public void deletarBotao(UUID id) {
        botaoPersonalizadoRepository.deleteById(id);
    }
}
