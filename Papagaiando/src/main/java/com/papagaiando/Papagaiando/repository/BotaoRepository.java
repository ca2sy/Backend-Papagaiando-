package com.papagaiando.Papagaiando.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.papagaiando.Papagaiando.model.BotaoModel;

@Repository
public interface BotaoRepository extends JpaRepository<BotaoModel, UUID> {
    
    List<BotaoModel> findByNomeContainingIgnoreCase(String nome);
    
    List<BotaoModel> findByCategoriaId(UUID categoriaId);
    
    List<BotaoModel> findByCategoriaIdAndPadrao(UUID categoriaId, boolean padrao);
    
    List<BotaoModel> findByCategoriaIdAndNomeContainingIgnoreCase(UUID categoriaId, String nome);
    
    List<BotaoModel> findByPadrao(boolean padrao);
}