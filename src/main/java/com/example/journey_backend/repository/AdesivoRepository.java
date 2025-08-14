package com.example.journey_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.journey_backend.model.Adesivo;

@Repository
public interface AdesivoRepository extends JpaRepository<Adesivo, Integer> {
    // Nenhum método adicional necessário no momento
}
