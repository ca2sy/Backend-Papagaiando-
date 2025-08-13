package com.papagaiando.Papagaiando.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.papagaiando.Papagaiando.model.PerfilModel;
import com.papagaiando.Papagaiando.model.UsuarioModel;
import com.papagaiando.Papagaiando.repository.PerfilRepository;

@Service
public class PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    // Criar perfil 
    public PerfilModel criarPerfil(String nome, int idade, UsuarioModel usuario) {
        PerfilModel perfil = new PerfilModel(idade, nome, usuario);

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

    // Atualizar perfil
    public PerfilModel atualizarPerfil(UUID id, String nome, int idade) {
        Optional<PerfilModel> optionalPerfil = perfilRepository.findById(id);
        if (optionalPerfil.isPresent()) {
            PerfilModel perfil = optionalPerfil.get();
            perfil.setNome(nome);
            perfil.setIdade(idade);
            return perfilRepository.save(perfil);
        }
        return null; 
    }


    public void deletarPerfil(UUID id) {
        perfilRepository.deleteById(id);
    }
}
