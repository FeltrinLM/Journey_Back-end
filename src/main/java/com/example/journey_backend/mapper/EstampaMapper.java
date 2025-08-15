package com.example.journey_backend.mapper;

import com.example.journey_backend.dto.EstampaDTO;
import com.example.journey_backend.model.Colecao;
import com.example.journey_backend.model.Estampa;

public class EstampaMapper {

    // Model → DTO
    public static EstampaDTO toDTO(Estampa estampa) {
        if (estampa == null) return null;

        EstampaDTO dto = new EstampaDTO();
        dto.setEstampa_id(estampa.getEstampa_id());
        dto.setNome(estampa.getNome());
        dto.setQuantidade(estampa.getQuantidade());
        dto.setColecao_id(estampa.getColecao() != null ? estampa.getColecao().getColecao_id() : 0);
        return dto;
    }

    // DTO → Model (assume que o objeto Colecao já foi buscado e será passado)
    public static Estampa toModel(EstampaDTO dto, Colecao colecao) {
        if (dto == null) return null;

        Estampa estampa = new Estampa();
        estampa.setEstampa_id(dto.getEstampa_id());
        estampa.setNome(dto.getNome());
        estampa.setQuantidade(dto.getQuantidade());
        estampa.setColecao(colecao); // precisa ser injetado (via serviço)
        return estampa;
    }
}
