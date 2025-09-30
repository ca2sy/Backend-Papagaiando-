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

    // AGORA SÓ TEM UMA LISTA DE BOTÕES!
    @OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<BotaoModel> botoes = new HashSet<>();

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

    public Set<BotaoModel> getBotoes() {
        return botoes;
    }

    public void setBotoes(Set<BotaoModel> botoes) {
        this.botoes = botoes;
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

    public boolean isPadrao() {
        return padrao;
    }

    public void setPadrao(boolean padrao) {
        this.padrao = padrao;
    }

    // Métodos auxiliares
    public void adicionarBotao(BotaoModel botao) {
        this.botoes.add(botao);
        if (botao.getCategoria() != this) {
            botao.setCategoria(this);
        }
    }

    // Método para obter apenas botões padrão
    public Set<BotaoModel> getBotoesPadrao() {
        Set<BotaoModel> botoesPadrao = new HashSet<>();
        for (BotaoModel botao : this.botoes) {
            if (botao.isPadrao()) {
                botoesPadrao.add(botao);
            }
        }
        return botoesPadrao;
    }

    // Método para obter apenas botões personalizados
    public Set<BotaoModel> getBotoesPersonalizados() {
        Set<BotaoModel> botoesPersonalizados = new HashSet<>();
        for (BotaoModel botao : this.botoes) {
            if (!botao.isPadrao()) {
                botoesPersonalizados.add(botao);
            }
        }
        return botoesPersonalizados;
    }
}