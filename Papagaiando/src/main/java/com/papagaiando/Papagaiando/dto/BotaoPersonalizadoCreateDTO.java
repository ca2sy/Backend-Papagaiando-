package com.papagaiando.Papagaiando.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class BotaoPersonalizadoCreateDTO {
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    
    @NotBlank(message = "URL da imagem é obrigatória")
    private String urlImagem;
    
    @NotBlank(message = "URL do áudio é obrigatória")
    private String urlAudio;  
    
    @NotNull(message = "ID do perfil é obrigatório")
    private UUID perfilId;

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public String getUrlAudio() {
        return urlAudio;
    }

    public void setUrlAudio(String urlAudio) {
        this.urlAudio = urlAudio;
    }

    public UUID getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(UUID perfilId) {
        this.perfilId = perfilId;
    }
}