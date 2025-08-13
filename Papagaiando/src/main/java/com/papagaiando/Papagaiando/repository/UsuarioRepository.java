package com.papagaiando.Papagaiando.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.papagaiando.Papagaiando.model.UsuarioModel;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, UUID> {

   
    Optional<UsuarioModel> findByEmail(String email);


    Optional<UsuarioModel> findByTokenRecuperacao(String token);

    
}
