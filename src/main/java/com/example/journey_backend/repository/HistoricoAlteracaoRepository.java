package com.example.journey_backend.repository;

import com.example.journey_backend.model.HistoricoAlteracao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricoAlteracaoRepository extends JpaRepository<HistoricoAlteracao, Long> {

    // Buscar por entidade (ex: "Peca", "Estampa")
    List<HistoricoAlteracao> findByEntidade(String entidade);

    // Buscar por entidade + ID da entidade alterada
    List<HistoricoAlteracao> findByEntidadeAndEntidadeId(String entidade, int entidadeId);

    // Buscar por usuário
    List<HistoricoAlteracao> findByUsuarioUsuarioId(int usuarioId);

    // Buscar todos os históricos ordenados por data/hora decrescente
    List<HistoricoAlteracao> findAllByOrderByDataHoraDesc();
}
