package com.papagaiando.Papagaiando.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.papagaiando.Papagaiando.model.PerfilModel;

public interface PerfilRepository extends JpaRepository<PerfilModel, UUID> {
    
    // Método existente
    boolean existsByUsuarioIdAndId(UUID usuarioId, UUID perfilId);
    
    // Adicione estes métodos explicitamente
    @Override
    Optional<PerfilModel> findById(UUID id);
    
    @Query("SELECT p FROM PerfilModel p WHERE p.id = :id")
    Optional<PerfilModel> buscarPorId(@Param("id") UUID id);
    
    // Método para debug
    @Query("SELECT p.id FROM PerfilModel p")
    List<UUID> listarTodosIds();
}