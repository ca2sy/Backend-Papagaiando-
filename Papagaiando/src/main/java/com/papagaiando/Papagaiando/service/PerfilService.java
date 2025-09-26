package com.papagaiando.Papagaiando.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.papagaiando.Papagaiando.model.PerfilModel;
import com.papagaiando.Papagaiando.model.UsuarioModel;
import com.papagaiando.Papagaiando.repository.PerfilRepository;
import com.papagaiando.Papagaiando.repository.UsuarioRepository;
import com.papagaiando.Papagaiando.exception.ResourceNotFoundException;

@Service
public class PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private AuthorizationService authorizationService;

    public PerfilModel criarPerfil(String nome, String urlFoto, UUID usuarioId) {
        UsuarioModel usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        
        PerfilModel perfil = new PerfilModel(nome, urlFoto, usuario);
        return perfilRepository.save(perfil);
    }

    public PerfilModel atualizarPerfil(UUID perfilId, String nome, String urlFoto, UUID usuarioLogado) {
        // Valida que o perfil pertence ao usuário logado
        authorizationService.validarPropriedadePerfil(perfilId, usuarioLogado);
        
        PerfilModel perfil = perfilRepository.findById(perfilId)
            .orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado"));
        
        if (nome != null && !nome.isBlank()) perfil.setNome(nome);
        if (urlFoto != null && !urlFoto.isBlank()) perfil.setUrlFoto(urlFoto);
        
        return perfilRepository.save(perfil);
    }

    public PerfilModel buscarPorId(UUID perfilId, UUID usuarioLogado) {
        return authorizationService.buscarPerfilComAutorizacao(perfilId, usuarioLogado);
    }

    public List<PerfilModel> listarPerfisPorUsuario(UUID usuarioId) {
        return perfilRepository.findByUsuarioId(usuarioId);
    }

    public void deletarPerfil(UUID perfilId, UUID usuarioLogado) {
        authorizationService.validarPropriedadePerfil(perfilId, usuarioLogado);
        perfilRepository.deleteById(perfilId);
    }


    
}