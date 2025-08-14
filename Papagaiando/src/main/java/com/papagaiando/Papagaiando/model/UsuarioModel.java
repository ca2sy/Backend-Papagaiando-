package com.papagaiando.Papagaiando.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_usuarios")
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String nome;

    @OneToMany(mappedBy = "usuario")
    @JsonManagedReference
    private Set<PerfilModel> perfis = new HashSet<>();

    // Token de recuperação
    private String tokenRecuperacao;

    // Expiração do token
    private LocalDateTime expiracaoToken;

    // Construtor padrão (necessário para JPA)
    public UsuarioModel() {}

    public UsuarioModel(String email, String senha, String nome) {
        this.email = email;
        this.senha = senha;
        this.nome = nome;
    }

    // Getters e Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTokenRecuperacao() { return tokenRecuperacao; }
    public void setTokenRecuperacao(String tokenRecuperacao) { this.tokenRecuperacao = tokenRecuperacao; }

    public LocalDateTime getExpiracaoToken() { return expiracaoToken; }
    public void setExpiracaoToken(LocalDateTime expiracaoToken) { this.expiracaoToken = expiracaoToken; }

    public Set<PerfilModel> getPerfis() {
        return perfis;
    }

    public void setPerfis(Set<PerfilModel> perfis) {
        this.perfis = perfis;
    }

     public void adicionarPerfil(PerfilModel perfil) {
        this.perfis.add(perfil);
        if (perfil.getUsuario() != this) {
            perfil.setUsuario(this);
        }
    }
}
