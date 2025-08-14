package com.example.journey_backend.repository;

import com.example.journey_backend.model.Colecao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColecaoRepository extends JpaRepository<Colecao, Integer> {

    // Busca uma coleção pelo nome
    Optional<Colecao> findByNome(String nome);
}
