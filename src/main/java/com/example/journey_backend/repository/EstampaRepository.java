package com.example.journey_backend.repository;

import com.example.journey_backend.model.Estampa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EstampaRepository extends JpaRepository<Estampa, Integer> {

    Optional<Estampa> findById(int id);

    List<Estampa> findByNome(String nome);

}
