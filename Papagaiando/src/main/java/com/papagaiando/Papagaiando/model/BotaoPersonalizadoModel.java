package com.papagaiando.Papagaiando.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_botoesPersonalizados")
public class BotaoPersonalizadoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String nome;     

    @Column(nullable = false)
    private String urlImagem; 

    @Column(nullable = false)
    private String urlAudio;  

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    @JsonBackReference
    private CategoriaModel categoria;

    // Construtores
    public BotaoPersonalizadoModel() {}

    public BotaoPersonalizadoModel(String nome, String urlImagem, String urlAudio, CategoriaModel categoria) {
        this.nome = nome;
        this.urlImagem = urlImagem;
        this.urlAudio = urlAudio;
        this.categoria = categoria;
    }

    // Getters e Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public CategoriaModel getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaModel categoria) {
        this.categoria = categoria;
        if (categoria != null && !categoria.getBotoesPersonalizados().contains(this)) {
            categoria.getBotoesPersonalizados().add(this);
        }
    }
}