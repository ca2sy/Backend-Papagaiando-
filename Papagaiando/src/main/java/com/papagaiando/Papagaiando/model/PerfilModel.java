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
@Table (name = "tb_perfis")
public class PerfilModel {
    //nome, foto, idade, id, botao normal e botao personalizado (botoes: botaoN e botaoP estendem de botoes)
   

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String urlFoto;

    @ManyToMany
    @JoinTable(
    name = "perfil_botao_padrao",
    joinColumns = @JoinColumn(name = "perfil_id"),
    inverseJoinColumns = @JoinColumn(name = "botao_id")
)
private Set<BotaoModel> botoesPadrao = new HashSet<>();

    @OneToMany(mappedBy = "perfil", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<BotaoPersonalizadoModel> botoesPersonalizados = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonBackReference
    private UsuarioModel usuario;

//construtor

    

    public PerfilModel(String nome, String urlFoto, UsuarioModel usuario) {
        this.nome = nome;
        this.urlFoto = urlFoto;
        this.usuario = usuario;
        }


    public PerfilModel() {
    }


    //getters e setters
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

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public String geturlFoto() {
        return urlFoto;
    }


    public void seturlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

public void adicionarBotaoPadrao(BotaoModel botao) {
        this.botoesPadrao.add(botao);
        if (!botao.getPerfis().contains(this)) {
            botao.getPerfis().add(this);
        }
    }

    public void adicionarBotaoPersonalizado(BotaoPersonalizadoModel botao) {
        this.botoesPersonalizados.add(botao);
        if (botao.getPerfil() != this) {
            botao.setPerfil(this);
        }
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
        if (usuario != null && !usuario.getPerfis().contains(this)) {
            usuario.getPerfis().add(this);
        }
    }

    
}
