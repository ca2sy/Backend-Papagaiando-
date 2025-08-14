package com.papagaiando.Papagaiando.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.papagaiando.Papagaiando.model.BotaoModel;

@Repository
public interface BotaoModelRepository extends JpaRepository<BotaoModel, UUID> {
    
    List<BotaoModel> findByNomeContainingIgnoreCase(String nome);
}
