package com.example.journey_backend.mapper;

import com.example.journey_backend.dto.ColecaoDTO;
import com.example.journey_backend.model.Colecao;

public class ColecaoMapper {

    // Model → DTO
    public static ColecaoDTO toDTO(Colecao colecao) {
        if (colecao == null) return null;

        ColecaoDTO dto = new ColecaoDTO();
        dto.setColecaoId(colecao.getColecaoId());
        dto.setNome(colecao.getNome());
        dto.setDataInicio(colecao.getDataInicio());
        dto.setDataFim(colecao.getDataFim());

        return dto;
    }

    // DTO → Model
    public static Colecao toModel(ColecaoDTO dto) {
        if (dto == null) return null;

        Colecao colecao = new Colecao();
        colecao.setColecaoId(dto.getColecaoId());
        colecao.setNome(dto.getNome());
        colecao.setDataInicio(dto.getDataInicio());
        colecao.setDataFim(dto.getDataFim());

        return colecao;
    }
}
