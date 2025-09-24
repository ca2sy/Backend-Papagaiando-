package com.papagaiando.Papagaiando.service;

import com.papagaiando.Papagaiando.exception.AccessDeniedException;
import com.papagaiando.Papagaiando.exception.ResourceNotFoundException;
import com.papagaiando.Papagaiando.model.CategoriaModel;
import com.papagaiando.Papagaiando.model.PerfilModel;
import com.papagaiando.Papagaiando.model.BotaoPersonalizadoModel;
import com.papagaiando.Papagaiando.repository.PerfilRepository;
import com.papagaiando.Papagaiando.repository.CategoriaRepository;
import com.papagaiando.Papagaiando.repository.BotaoPersonalizadoModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthorizationService {
    
    @Autowired
    private PerfilRepository perfilRepository;
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Autowired
    private BotaoPersonalizadoModelRepository botaoPersonalizadoRepository;

    public void validarPropriedadePerfil(UUID perfilId, UUID usuarioId) {
        if (!perfilRepository.existsByUsuarioIdAndId(usuarioId, perfilId)) {
            throw new AccessDeniedException("Acesso negado: perfil não pertence ao usuário");
        }
    }
    
    public void validarPropriedadeCategoria(UUID categoriaId, UUID usuarioId) {
        CategoriaModel categoria = categoriaRepository.findById(categoriaId)
            .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
        
        if (!categoria.getPerfil().getUsuario().getId().equals(usuarioId)) {
            throw new AccessDeniedException("Acesso negado: categoria não pertence ao usuário");
        }
    }
    

    public void validarPropriedadeBotaoPersonalizado(UUID botaoId, UUID usuarioId) {
        BotaoPersonalizadoModel botao = botaoPersonalizadoRepository.findById(botaoId)
            .orElseThrow(() -> new ResourceNotFoundException("Botão personalizado não encontrado"));
        
        if (!botao.getCategoria().getPerfil().getUsuario().getId().equals(usuarioId)) {
            throw new AccessDeniedException("Acesso negado: botão não pertence ao usuário");
        }
    }
    

    public void validarCriacaoCategoria(UUID perfilId, UUID usuarioId) {
        validarPropriedadePerfil(perfilId, usuarioId);
    }
    

    public void validarCriacaoBotaoPersonalizado(UUID categoriaId, UUID usuarioId) {
        validarPropriedadeCategoria(categoriaId, usuarioId);
    }
    
 
    public PerfilModel buscarPerfilComAutorizacao(UUID perfilId, UUID usuarioId) {
        validarPropriedadePerfil(perfilId, usuarioId);
        return perfilRepository.findById(perfilId)
            .orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado"));
    }
    

    public CategoriaModel buscarCategoriaComAutorizacao(UUID categoriaId, UUID usuarioId) {
        validarPropriedadeCategoria(categoriaId, usuarioId);
        return categoriaRepository.findById(categoriaId)
            .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
    }
}
