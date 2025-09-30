package com.papagaiando.Papagaiando.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class BotaoCreateDTO {
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    
    @NotBlank(message = "URL da imagem é obrigatória")
    private String urlImagem;
    
    @NotBlank(message = "URL do áudio é obrigatória")
    private String urlAudio;  
    
    @NotNull(message = "ID da categoria é obrigatório")
    private UUID categoriaId;

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

    public UUID getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(UUID categoriaId) {
        this.categoriaId = categoriaId;
    }
}