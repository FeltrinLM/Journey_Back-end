package com.example.journey_backend.repository;

import com.example.journey_backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // Busca usuário pelo nome (login)
    Optional<Usuario> findByNome(String nome);

    // Verifica se o nome de usuário já existe (evita duplicidade)
    boolean existsByNome(String nome);

}
