package com.papagaiando.Papagaiando.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.papagaiando.Papagaiando.model.CategoriaModel;
import com.papagaiando.Papagaiando.model.PerfilModel;
import com.papagaiando.Papagaiando.repository.CategoriaRepository;
import com.papagaiando.Papagaiando.repository.PerfilRepository;
import com.papagaiando.Papagaiando.exception.ResourceNotFoundException;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private PerfilRepository perfilRepository;
    
    @Autowired
    private AuthorizationService authorizationService;

public CategoriaModel criarCategoria(String nome, String urlImagem, UUID perfilId, UUID usuarioLogado) {
    // Valida que o perfil pertence ao usuário logado
    authorizationService.validarCriacaoCategoria(perfilId, usuarioLogado);

    PerfilModel perfil = perfilRepository.findById(perfilId)
        .orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado"));

    CategoriaModel categoria = new CategoriaModel(nome, urlImagem, perfil);

    // Aqui define que toda categoria criada via front é personalizada
    categoria.setPadrao(false); 

    return categoriaRepository.save(categoria);
}


    public List<CategoriaModel> listarPorPerfil(UUID perfilId, UUID usuarioLogado) {
        // Valida que o perfil pertence ao usuário logado
        authorizationService.validarPropriedadePerfil(perfilId, usuarioLogado);
        
        return categoriaRepository.findByPerfilId(perfilId);
    }

    public CategoriaModel buscarPorId(UUID categoriaId, UUID usuarioLogado) {
        return authorizationService.buscarCategoriaComAutorizacao(categoriaId, usuarioLogado);
    }

    public List<CategoriaModel> buscarPorNomePerfil(UUID perfilId, String nome, UUID usuarioLogado) {
        authorizationService.validarPropriedadePerfil(perfilId, usuarioLogado);
        
        PerfilModel perfil = perfilRepository.findById(perfilId)
            .orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado"));
        
        return categoriaRepository.findByPerfilAndNomeContainingIgnoreCase(perfil, nome);
    }

    public CategoriaModel atualizarCategoria(UUID categoriaId, String nome, String urlImagem, UUID usuarioLogado) {
        authorizationService.validarPropriedadeCategoria(categoriaId, usuarioLogado);
        
        CategoriaModel categoria = categoriaRepository.findById(categoriaId)
            .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
        
        if (nome != null && !nome.isBlank()) categoria.setNome(nome);
        if (urlImagem != null && !urlImagem.isBlank()) categoria.setUrlImagem(urlImagem);
        
        return categoriaRepository.save(categoria);
    }

    public void deletarCategoria(UUID categoriaId, UUID usuarioLogado) {
        authorizationService.validarPropriedadeCategoria(categoriaId, usuarioLogado);
        categoriaRepository.deleteById(categoriaId);
    }

    public List<CategoriaModel> listarCategoriasPadrao() {
    return categoriaRepository.findAll().stream()
            .filter(CategoriaModel::isPadrao)
            .toList();
}
    public List<CategoriaModel> listarCategoriasPerfilComPadrao(UUID perfilId, UUID usuarioLogado) {
    // Valida que o perfil pertence ao usuário logado
    authorizationService.validarPropriedadePerfil(perfilId, usuarioLogado);

    // Busca categorias do perfil
    List<CategoriaModel> perfilCategorias = categoriaRepository.findByPerfilId(perfilId);

    // Busca categorias padrão
    List<CategoriaModel> padraoCategorias = categoriaRepository.findAll()
            .stream()
            .filter(CategoriaModel::isPadrao)
            .toList();

    // Junta as duas listas
    perfilCategorias.addAll(padraoCategorias);

    return perfilCategorias;
}
}