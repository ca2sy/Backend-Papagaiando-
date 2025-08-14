package com.papagaiando.Papagaiando.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.papagaiando.Papagaiando.model.BotaoPersonalizadoModel;
import com.papagaiando.Papagaiando.model.PerfilModel;
import com.papagaiando.Papagaiando.repository.BotaoPersonalizadoModelRepository;
import com.papagaiando.Papagaiando.repository.PerfilRepository;

@Service
public class BotaoPersonalizadoService {

    @Autowired
    private BotaoPersonalizadoModelRepository botaoRepository;

    @Autowired
    private PerfilRepository perfilRepository;

   
       public BotaoPersonalizadoModel criarBotaoPorPerfilId(String nome, String urlImagem, String urlAudio, UUID perfilId) {
   
    if (!perfilRepository.existsById(perfilId)) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Perfil com ID " + perfilId + " não encontrado");
    }
    
    PerfilModel perfil = perfilRepository.getReferenceById(perfilId); 
    return botaoRepository.save(new BotaoPersonalizadoModel(nome, urlImagem, urlAudio, perfil));
}

    public List<BotaoPersonalizadoModel> listarPorPerfilId(UUID perfilId) {
        if (!perfilRepository.existsById(perfilId)) {
            throw new RuntimeException("Perfil não encontrado");
        }
        return botaoRepository.findByPerfilId(perfilId);
    }

    // Buscar botões de um perfil por nome
    public List<BotaoPersonalizadoModel> buscarPorNomeId(UUID perfilId, String nome) {
        Optional<PerfilModel> perfilOpt = perfilRepository.findById(perfilId);
        return perfilOpt.map(p -> botaoRepository.findByPerfilAndNomeContainingIgnoreCase(p, nome)).orElse(List.of());
    }

    // Buscar botão por ID
    public Optional<BotaoPersonalizadoModel> buscarPorId(UUID id) {
        return botaoRepository.findById(id);
    }

    // Deletar botão
    public void deletarBotao(UUID id) {
        botaoRepository.deleteById(id);
    }
}
