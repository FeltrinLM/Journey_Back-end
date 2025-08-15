package com.example.journey_backend.mapper;

import com.example.journey_backend.dto.PecaDTO;
import com.example.journey_backend.model.Peca;

public class PecaMapper {

    // Model → DTO
    public static PecaDTO toDTO(Peca peca) {
        if (peca == null) return null;

        PecaDTO dto = new PecaDTO();
        dto.setPeca_id(peca.getPeca_id());
        dto.setTipo(peca.getTipo());
        dto.setTamanho(peca.getTamanho());
        dto.setCor(peca.getCor());
        dto.setQuantidade(peca.getQuantidade());

        return dto;
    }

    // DTO → Model
    public static Peca toModel(PecaDTO dto) {
        if (dto == null) return null;

        Peca peca = new Peca();
        peca.setPeca_id(dto.getPeca_id());
        peca.setTipo(dto.getTipo());
        peca.setTamanho(dto.getTamanho());
        peca.setCor(dto.getCor());
        peca.setQuantidade(dto.getQuantidade());

        return peca;
    }
}
