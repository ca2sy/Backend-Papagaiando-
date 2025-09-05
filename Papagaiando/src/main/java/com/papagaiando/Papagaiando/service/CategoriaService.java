package com.papagaiando.Papagaiando.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.papagaiando.Papagaiando.model.CategoriaModel;
import com.papagaiando.Papagaiando.model.PerfilModel;
import com.papagaiando.Papagaiando.repository.CategoriaRepository;
import com.papagaiando.Papagaiando.repository.PerfilRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    public CategoriaModel criarCategoria(String nome, String urlImagem, UUID perfilId) {
        if (!perfilRepository.existsById(perfilId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Perfil com ID " + perfilId + " não encontrado");
        }
        
        PerfilModel perfil = perfilRepository.getReferenceById(perfilId);
        return categoriaRepository.save(new CategoriaModel(nome, urlImagem, perfil));
    }

    public List<CategoriaModel> listarPorPerfilId(UUID perfilId) {
        if (!perfilRepository.existsById(perfilId)) {
            throw new RuntimeException("Perfil não encontrado");
        }
        return categoriaRepository.findByPerfilId(perfilId);
    }

    public List<CategoriaModel> buscarPorNomePerfilId(UUID perfilId, String nome) {
        Optional<PerfilModel> perfilOpt = perfilRepository.findById(perfilId);
        return perfilOpt.map(p -> categoriaRepository.findByPerfilAndNomeContainingIgnoreCase(p, nome))
                       .orElse(List.of());
    }

    public List<CategoriaModel> buscarPorNome(String nome) {
        return categoriaRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Optional<CategoriaModel> buscarPorId(UUID id) {
        return categoriaRepository.findById(id);
    }

    public CategoriaModel atualizarCategoria(UUID id, String nome, String urlImagem) {
        CategoriaModel categoria = buscarPorId(id).orElseThrow(() -> 
            new RuntimeException("Categoria não encontrada"));
        
        if (nome != null) categoria.setNome(nome);
        if (urlImagem != null) categoria.setUrlImagem(urlImagem);
        
        return categoriaRepository.save(categoria);
    }

    public void deletarCategoria(UUID id) {
        categoriaRepository.deleteById(id);
    }

}