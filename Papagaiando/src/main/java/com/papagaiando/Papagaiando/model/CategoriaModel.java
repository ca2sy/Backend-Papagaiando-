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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_categorias")
public class CategoriaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String urlImagem;

        @Column(nullable = false)
        private boolean padrao = true;

    // Categoria pode ter botões padrão
    @ManyToMany
    @JoinTable(
        name = "categoria_botao_padrao",
        joinColumns = @JoinColumn(name = "categoria_id"),
        inverseJoinColumns = @JoinColumn(name = "botao_id")
    )
    private Set<BotaoModel> botoesPadrao = new HashSet<>();

    // Categoria pode ter botões personalizados
    @OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<BotaoPersonalizadoModel> botoesPersonalizados = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "perfil_id")
    @JsonBackReference
    private PerfilModel perfil;

    // Construtores
    public CategoriaModel() {}

    public CategoriaModel(String nome, String urlImagem, PerfilModel perfil) {
        this.nome = nome;
        this.urlImagem = urlImagem;
        this.perfil = perfil;
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

    public Set<BotaoModel> getBotoesPadrao() {
        return botoesPadrao;
    }

    public void setBotoesPadrao(Set<BotaoModel> botoesPadrao) {
        this.botoesPadrao = botoesPadrao;
    }

    public Set<BotaoPersonalizadoModel> getBotoesPersonalizados() {
        return botoesPersonalizados;
    }

    public void setBotoesPersonalizados(Set<BotaoPersonalizadoModel> botoesPersonalizados) {
        this.botoesPersonalizados = botoesPersonalizados;
    }

    public PerfilModel getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilModel perfil) {
        this.perfil = perfil;
        if (perfil != null && !perfil.getCategorias().contains(this)) {
            perfil.getCategorias().add(this);
        }
    }

    // Métodos auxiliares
    public void adicionarBotaoPadrao(BotaoModel botao) {
        this.botoesPadrao.add(botao);
        if (!botao.getCategorias().contains(this)) {
            botao.getCategorias().add(this);
        }
    }

    public void adicionarBotaoPersonalizado(BotaoPersonalizadoModel botao) {
        this.botoesPersonalizados.add(botao);
        if (botao.getCategoria() != this) {
            botao.setCategoria(this);
        }
    }

    public boolean isPadrao() {
        return padrao;
    }

    public void setPadrao(boolean padrao) {
        this.padrao = padrao;
    }
}