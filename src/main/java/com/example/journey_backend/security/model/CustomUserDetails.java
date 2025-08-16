package com.example.journey_backend.security.model;

import com.example.journey_backend.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Classe que implementa UserDetails e representa o usuário autenticado no Spring Security.
 */
public class CustomUserDetails implements UserDetails {

    private final int usuarioId;     // ID do usuário
    private final String nome;       // Nome do usuário (usado como login)
    private final String senha;      // Senha já criptografada
    private final Collection<? extends GrantedAuthority> authorities; // Permissões (roles)

    // Construtor baseado na entidade Usuario
    public CustomUserDetails(Usuario usuario) {
        this.usuarioId = usuario.getUsuarioId();
        this.nome = usuario.getNome();
        this.senha = usuario.getSenha();

        // Define a role como "ROLE_ADMINISTRADOR" ou "ROLE_FUNCIONARIO"
        this.authorities = Collections.singleton(() -> "ROLE_" + usuario.getTipo().name());
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return nome; // Nome será usado como login
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Pode personalizar no futuro
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Pode personalizar no futuro
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Pode personalizar no futuro
    }

    @Override
    public boolean isEnabled() {
        return true; // Pode ser útil para ativar/desativar contas
    }
}
