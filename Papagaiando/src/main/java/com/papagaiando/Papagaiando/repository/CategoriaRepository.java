package com.papagaiando.Papagaiando.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.papagaiando.Papagaiando.model.CategoriaModel;
import com.papagaiando.Papagaiando.model.PerfilModel;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaModel, UUID> {
    
    List<CategoriaModel> findByPerfil(PerfilModel perfil);
    
    List<CategoriaModel> findByPerfilId(UUID perfilId);
    
    List<CategoriaModel> findByPerfilAndNomeContainingIgnoreCase(PerfilModel perfil, String nome);
    
    List<CategoriaModel> findByNomeContainingIgnoreCase(String nome);
}