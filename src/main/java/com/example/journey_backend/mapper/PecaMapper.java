package com.example.journey_backend.mapper;

import com.example.journey_backend.dto.PecaDTO;
import com.example.journey_backend.model.Peca;

public final class PecaMapper {

    /**
     * Construtor privado para impedir a instanciação desta classe utilitária.
     */
    private PecaMapper() {
        // Construtor intencionalmente vazio e privado
    }

    // Model → DTO
    public static PecaDTO toDTO(Peca peca) {
        if (peca == null){
            return null;
        }

        PecaDTO dto = new PecaDTO();
        dto.setPecaId(peca.getPecaId());
        dto.setTipo(peca.getTipo());
        dto.setTamanho(peca.getTamanho());
        dto.setCor(peca.getCor());
        dto.setQuantidade(peca.getQuantidade());

        return dto;
    }

    // DTO → Model
    public static Peca toModel(PecaDTO dto) {
        if (dto == null){
            return null;
        }

        Peca peca = new Peca();
        peca.setPecaId(dto.getPecaId());
        peca.setTipo(dto.getTipo());
        peca.setTamanho(dto.getTamanho());
        peca.setCor(dto.getCor());
        peca.setQuantidade(dto.getQuantidade());

        return peca;
    }
}
