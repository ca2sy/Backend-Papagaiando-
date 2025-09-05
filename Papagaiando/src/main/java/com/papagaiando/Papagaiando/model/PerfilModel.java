package com.papagaiando.Papagaiando.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_perfis")
public class PerfilModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String urlFoto;

    @OneToMany(mappedBy = "perfil", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<CategoriaModel> categorias = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonBackReference
    private UsuarioModel usuario;

    // Construtor
    public PerfilModel(String nome, String urlFoto, UsuarioModel usuario) {
        this.nome = nome;
        this.urlFoto = urlFoto;
        this.usuario = usuario;
    }

    public PerfilModel() {
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Set<CategoriaModel> getCategorias() {
        return categorias;
    }

    public void setCategorias(Set<CategoriaModel> categorias) {
        this.categorias = categorias;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public void adicionarCategoria(CategoriaModel categoria) {
        this.categorias.add(categoria);
        if (categoria.getPerfil() != this) {
            categoria.setPerfil(this);
        }
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
        if (usuario != null && !usuario.getPerfis().contains(this)) {
            usuario.getPerfis().add(this);
        }
    }
}