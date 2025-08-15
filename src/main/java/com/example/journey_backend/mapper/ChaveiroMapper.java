package com.example.journey_backend.mapper;

import com.example.journey_backend.dto.ChaveiroDTO;
import com.example.journey_backend.model.Chaveiro;
import com.example.journey_backend.model.Colecao;

public class ChaveiroMapper {

    // Model → DTO
    public static ChaveiroDTO toDTO(Chaveiro chaveiro) {
        if (chaveiro == null) return null;

        ChaveiroDTO dto = new ChaveiroDTO();
        dto.setChaveiro_id(chaveiro.getChaveiro_id());
        dto.setChaveiro_modelo(chaveiro.getChaveiro_modelo());
        dto.setColecao_id(
                chaveiro.getColecao() != null ? chaveiro.getColecao().getColecao_id() : 0
        );

        return dto;
    }

    // DTO → Model (precisa da instância de Colecao!)
    public static Chaveiro toModel(ChaveiroDTO dto, Colecao colecao) {
        if (dto == null) return null;

        Chaveiro chaveiro = new Chaveiro();
        chaveiro.setChaveiro_id(dto.getChaveiro_id());
        chaveiro.setChaveiro_modelo(dto.getChaveiro_modelo());
        chaveiro.setColecao(colecao); // injetamos a Colecao já buscada no service

        return chaveiro;
    }
}
