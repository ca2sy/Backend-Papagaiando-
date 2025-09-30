package com.papagaiando.Papagaiando.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_botoes")
public class BotaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String nome; 

    @Column(nullable = false)
    private String urlImagem; 

    @Column(nullable = false)
    private String urlAudio;

    @Column(nullable = false)
    private boolean padrao = true;

    // Todos os botões pertencem a uma única categoria
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    @JsonBackReference
    private CategoriaModel categoria;

    // Construtores
    public BotaoModel() {}

    // Construtor para botões padrão
    public BotaoModel(String nome, String urlImagem, String urlAudio, CategoriaModel categoria) {
        this.nome = nome;
        this.urlImagem = urlImagem;
        this.urlAudio = urlAudio;
        this.categoria = categoria;
        this.padrao = true;
    }

    // Construtor para botões personalizados
    public BotaoModel(String nome, String urlImagem, String urlAudio, CategoriaModel categoria, boolean personalizado) {
        this.nome = nome;
        this.urlImagem = urlImagem;
        this.urlAudio = urlAudio;
        this.categoria = categoria;
        this.padrao = !personalizado; // Se é personalizado, então não é padrão
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

    public boolean isPadrao() {
        return padrao;
    }

    public void setPadrao(boolean padrao) {
        this.padrao = padrao;
    }

    public CategoriaModel getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaModel categoria) {
        this.categoria = categoria;
        if (categoria != null && !categoria.getBotoes().contains(this)) {
            categoria.getBotoes().add(this);
        }
    }
}