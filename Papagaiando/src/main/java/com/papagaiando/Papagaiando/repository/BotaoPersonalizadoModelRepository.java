package com.papagaiando.Papagaiando.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.papagaiando.Papagaiando.model.BotaoPersonalizadoModel;
import com.papagaiando.Papagaiando.model.PerfilModel;

@Repository
public interface BotaoPersonalizadoModelRepository extends JpaRepository<BotaoPersonalizadoModel, UUID> {
   
    List<BotaoPersonalizadoModel> findByPerfil(PerfilModel perfil);

 
    List<BotaoPersonalizadoModel> findByPerfilAndNomeContainingIgnoreCase(PerfilModel perfil, String nome);
}
