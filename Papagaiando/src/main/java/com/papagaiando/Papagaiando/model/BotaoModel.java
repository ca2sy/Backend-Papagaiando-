package com.papagaiando.Papagaiando.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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

    @ManyToMany(mappedBy = "botoesPadrao")
    @JsonBackReference
    private Set<PerfilModel> perfis = new HashSet<>();
    
    // Construtores
    public BotaoModel() {}

    public BotaoModel(String nome, String urlImagem, String urlAudio) {
        this.nome = nome;
        this.urlImagem = urlImagem;
        this.urlAudio = urlAudio;
    }

    // Getters e Setters

    public Set<PerfilModel> getPerfis() {
        return perfis;
    }

    public void setPerfis(Set<PerfilModel> perfis) {
        this.perfis = perfis;
    }
    
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
}
