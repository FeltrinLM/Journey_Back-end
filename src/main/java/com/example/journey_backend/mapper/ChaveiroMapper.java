package com.example.journey_backend.mapper;

import com.example.journey_backend.dto.ChaveiroDTO;
import com.example.journey_backend.model.Chaveiro;
import com.example.journey_backend.model.Colecao;

public final class ChaveiroMapper {

    /**
     * Construtor privado para impedir a instanciação desta classe utilitária.
     */
    private ChaveiroMapper() {
        // Construtor intencionalmente vazio e privado
    }

    // Model → DTO
    public static ChaveiroDTO toDTO(Chaveiro chaveiro) {
        if (chaveiro == null) {
            return null;
        }

        ChaveiroDTO dto = new ChaveiroDTO();
        dto.setChaveiroId(chaveiro.getChaveiroId());
        dto.setChaveiroModelo(chaveiro.getChaveiroModelo());
        dto.setColecaoId(
                chaveiro.getColecao() != null ? chaveiro.getColecao().getColecaoId() : 0
        );

        return dto;
    }

    // DTO → Model (precisa da instância de Colecao!)
    public static Chaveiro toModel(ChaveiroDTO dto, Colecao colecao) {
        if (dto == null){
            return null;
        }

        Chaveiro chaveiro = new Chaveiro();
        chaveiro.setChaveiroId(dto.getChaveiroId());
        chaveiro.setChaveiroModelo(dto.getChaveiroModelo());
        chaveiro.setColecao(colecao); // injetamos a Colecao já buscada no service

        return chaveiro;
    }
}
