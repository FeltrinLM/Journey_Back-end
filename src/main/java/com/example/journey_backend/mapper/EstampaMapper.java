package com.example.journey_backend.mapper;

import com.example.journey_backend.dto.EstampaDTO;
import com.example.journey_backend.model.Colecao;
import com.example.journey_backend.model.Estampa;

public final class EstampaMapper {

    /**
     * Construtor privado para impedir a instanciação desta classe utilitária.
     */
    private EstampaMapper() {
        // Construtor intencionalmente vazio e privado
    }

    // Model → DTO
    public static EstampaDTO toDTO(Estampa estampa) {
        if (estampa == null){
            return null;
        }

        EstampaDTO dto = new EstampaDTO();
        dto.setEstampaId(estampa.getEstampaId());
        dto.setNome(estampa.getNome());
        dto.setQuantidade(estampa.getQuantidade());
        dto.setColecaoId(estampa.getColecao() != null ? estampa.getColecao().getColecaoId() : 0);
        return dto;
    }

    // DTO → Model (assume que o objeto Colecao já foi buscado e será passado)
    public static Estampa toModel(EstampaDTO dto, Colecao colecao) {
        if (dto == null) {
            return null;
        }

        Estampa estampa = new Estampa();
        estampa.setEstampaId(dto.getEstampaId());
        estampa.setNome(dto.getNome());
        estampa.setQuantidade(dto.getQuantidade());
        estampa.setColecao(colecao); // precisa ser injetado (via serviço)
        return estampa;
    }
}
