package com.example.journey_backend.mapper;

import com.example.journey_backend.dto.AdesivoDTO;
import com.example.journey_backend.model.Adesivo;

public class AdesivoMapper {

    // Model → DTO
    public static AdesivoDTO toDTO(Adesivo adesivo) {
        if (adesivo == null) return null;

        AdesivoDTO dto = new AdesivoDTO();
        dto.setAdesivo_id(adesivo.getAdesivo_id());
        dto.setAdesivo_modelo(adesivo.getAdesivo_modelo());
        dto.setCromatico(adesivo.isCromatico());
        return dto;
    }

    // DTO → Model
    public static Adesivo toModel(AdesivoDTO dto) {
        if (dto == null) return null;

        Adesivo adesivo = new Adesivo();
        adesivo.setAdesivo_id(dto.getAdesivo_id());
        adesivo.setAdesivo_modelo(dto.getAdesivo_modelo());
        adesivo.setCromatico(dto.isCromatico());
        return adesivo;
    }
}
