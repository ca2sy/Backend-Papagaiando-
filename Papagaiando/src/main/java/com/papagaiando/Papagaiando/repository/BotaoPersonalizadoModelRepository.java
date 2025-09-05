package com.papagaiando.Papagaiando.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.papagaiando.Papagaiando.model.BotaoPersonalizadoModel;
import com.papagaiando.Papagaiando.model.CategoriaModel;

@Repository
public interface BotaoPersonalizadoModelRepository extends JpaRepository<BotaoPersonalizadoModel, UUID> {
   
    List<BotaoPersonalizadoModel> findByCategoria(CategoriaModel categoria);

    List<BotaoPersonalizadoModel> findByCategoriaId(UUID categoriaId);
 
    List<BotaoPersonalizadoModel> findByCategoriaAndNomeContainingIgnoreCase(CategoriaModel categoria, String nome);
}