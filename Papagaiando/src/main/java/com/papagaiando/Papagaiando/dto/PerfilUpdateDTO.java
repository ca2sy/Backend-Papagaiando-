package com.papagaiando.Papagaiando.dto;

import jakarta.validation.constraints.NotBlank;

public class PerfilUpdateDTO {
    @NotBlank(message = "Nome não pode ser vazio")
    private String nome;
    
    @NotBlank(message = "URL da foto não pode ser vazia")
    private String urlFoto;

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
}