package com.papagaiando.Papagaiando.service;

import com.papagaiando.Papagaiando.model.UsuarioModel;
import com.papagaiando.Papagaiando.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Registrar usuário
      public UsuarioModel registrarUsuario(String email, String nome, String senha) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new RuntimeException("Email já cadastrado");
        }
        
        String senhaCriptografada = passwordEncoder.encode(senha);
        UsuarioModel usuario = new UsuarioModel(email, senhaCriptografada, nome);
        return usuarioRepository.save(usuario);
    }

public UsuarioModel atualizarUsuario(UUID id, String nome, String email, String senha) {
    UsuarioModel usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

    // Atualiza email se fornecido e diferente
    if (email != null && !email.equals(usuario.getEmail())) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new RuntimeException("Email já está em uso");
        }
        usuario.setEmail(email);
    }

    // Atualiza nome se fornecido
    if (nome != null) {
        if (nome.isBlank()) throw new IllegalArgumentException("Nome inválido");
        usuario.setNome(nome);
    }

    // Atualiza senha se fornecida
    if (senha != null) {
        if (senha.length() < 8) throw new IllegalArgumentException("Senha muito curta");
        usuario.setSenha(passwordEncoder.encode(senha));
    }

    return usuarioRepository.save(usuario);
}

    // Listar todos os usuários
    public List<UsuarioModel> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // Buscar por ID
    public Optional<UsuarioModel> buscarPorId(UUID id) {
        return usuarioRepository.findById(id);
    }

    public UsuarioModel buscarPorEmail(String email) {
    return usuarioRepository.findByEmail(email);
}


    // Deletar usuário
    public void deletarUsuario(UUID id) {
        usuarioRepository.deleteById(id);
    }

   // Gerar token de recuperação
public String gerarTokenRecuperacao(String email) {
    UsuarioModel usuario = usuarioRepository.findByEmail(email); // seu método atual
    if (usuario != null) {
        String token = UUID.randomUUID().toString();
        usuario.setTokenRecuperacao(token);
        usuario.setExpiracaoToken(LocalDateTime.now().plusHours(1)); // token válido por 1 hora
        usuarioRepository.save(usuario);
        return token;
    }
    return null; // ou lançar exceção se não encontrar usuário
}

// Validar token de recuperação
public boolean validarTokenRecuperacao(String token) {
    Optional<UsuarioModel> optionalUsuario = usuarioRepository.findByTokenRecuperacao(token);
    if (optionalUsuario.isPresent()) {
        UsuarioModel usuario = optionalUsuario.get();
        return usuario.getExpiracaoToken() != null && usuario.getExpiracaoToken().isAfter(LocalDateTime.now());
    }
    return false;
}

// Redefinir senha usando token
public boolean redefinirSenha(String token, String novaSenha) {
    Optional<UsuarioModel> optionalUsuario = usuarioRepository.findByTokenRecuperacao(token);
    if (optionalUsuario.isPresent()) {
        UsuarioModel usuario = optionalUsuario.get();
        if (validarTokenRecuperacao(token)) {
            usuario.setSenha(passwordEncoder.encode(novaSenha));
            usuario.setTokenRecuperacao(null); // limpa token após uso
            usuario.setExpiracaoToken(null);
            usuarioRepository.save(usuario);
            return true;
        }
    }
    return false;
}

public UsuarioModel autenticarUsuario(String email, String senha) {
    UsuarioModel usuario = usuarioRepository.findByEmail(email);
    
    if (usuario == null || !passwordEncoder.matches(senha, usuario.getSenha())) {
        throw new RuntimeException("Email ou senha inválidos");
    }
    
    return usuario;
}
}
