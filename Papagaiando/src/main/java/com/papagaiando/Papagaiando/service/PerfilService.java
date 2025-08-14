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

@Service
public class PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Criar perfil
   public PerfilModel criarPerfilPorId(String nome, String urlFoto, UUID usuarioId) {
        UsuarioModel usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        PerfilModel perfil = new PerfilModel(nome, urlFoto, usuario);
        return perfilRepository.save(perfil);
    }

    public PerfilModel atualizarPerfil(UUID id, String nome, String urlFoto) {
        PerfilModel perfil = perfilRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));
        
        if (nome != null) perfil.setNome(nome);
        if (urlFoto != null) perfil.seturlFoto(urlFoto);
        
        return perfilRepository.save(perfil);
    }

    // Buscar por ID
    public Optional<PerfilModel> buscarPorId(UUID id) {
        return perfilRepository.findById(id);
    }

    // Listar
    public List<PerfilModel> listarPerfis() {
        return perfilRepository.findAll();
    }

    // Deletar perfil
    public void deletarPerfil(UUID id) {
        perfilRepository.deleteById(id);
    }
}
