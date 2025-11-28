package com.example.journey_backend.mapper;

import com.example.journey_backend.dto.AdesivoDTO;
import com.example.journey_backend.model.Adesivo;

// ADICIONADO "final" AQUI V
public final class AdesivoMapper {

    /**
     * Construtor privado para impedir a instanciação desta classe utilitária.
     */
    private AdesivoMapper() {
        // Construtor intencionalmente vazio e privado
    }

    // Model → DTO
    public static AdesivoDTO toDTO(Adesivo adesivo) {
        if (adesivo == null){
            return null;
        }

        AdesivoDTO dto = new AdesivoDTO();
        dto.setAdesivoId(adesivo.getAdesivoId());
        dto.setAdesivoModelo(adesivo.getAdesivoModelo());
        dto.setCromatico(adesivo.isCromatico());
        return dto;
    }

    // DTO → Model
    public static Adesivo toModel(AdesivoDTO dto) {
        if (dto == null) {
            return null;
        }

        Adesivo adesivo = new Adesivo();
        adesivo.setAdesivoId(dto.getAdesivoId());
        adesivo.setAdesivoModelo(dto.getAdesivoModelo());
        adesivo.setCromatico(dto.isCromatico());
        return adesivo;
    }
}