package com.example.journey_backend.repository;

import com.example.journey_backend.model.Chaveiro;
import com.example.journey_backend.model.Colecao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChaveiroRepository extends JpaRepository<Chaveiro, Integer> {

    Optional<Chaveiro> findById(int id);

    // Busca por modelo (evita duplicidade e permite filtro)
    Optional<Chaveiro> findByChaveiroModelo(String chaveiroModelo);

    // Busca por colecao associada
    List<Chaveiro> findByColecao(Colecao colecao);

    // Caso precise evitar chaveiros repetidos com o mesmo modelo e coleção
    Optional<Chaveiro> findByChaveiroModeloAndColecao(String chaveiroModelo, Colecao colecao);
}
