package com.example.journey_backend.repository;

import com.example.journey_backend.model.Peca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PecaRepository extends JpaRepository<Peca, Integer> {

    Optional<Peca> findById(int id);

    // Pode ser útil para evitar duplicidades e buscas por filtros
    List<Peca> findByTipo(String tipo);
    List<Peca> findByTamanho(String tamanho);
    List<Peca> findByCor(String cor);

    // Também pode ser útil para múltiplos critérios, por exemplo em filtros de lista
    List<Peca> findByTipoAndTamanhoAndCor(String tipo, String tamanho, String cor);
}
