package com.papagaiando.Papagaiando.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.papagaiando.Papagaiando.model.PerfilModel;

public interface PerfilRepository extends JpaRepository<PerfilModel, UUID>{
    
}
