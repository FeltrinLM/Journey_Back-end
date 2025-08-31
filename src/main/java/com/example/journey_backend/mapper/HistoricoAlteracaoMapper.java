package com.example.journey_backend.mapper;

import com.example.journey_backend.dto.HistoricoAlteracaoDTO;
import com.example.journey_backend.model.HistoricoAlteracao;
import com.example.journey_backend.model.Usuario;

public class HistoricoAlteracaoMapper {

    // Converte Model → DTO
    public static HistoricoAlteracaoDTO toDTO(HistoricoAlteracao model) {
        HistoricoAlteracaoDTO dto = new HistoricoAlteracaoDTO();
        dto.setId(model.getId());
        dto.setEntidade(model.getEntidade());
        dto.setEntidadeId(model.getEntidadeId());
        dto.setCampoAlterado(model.getCampoAlterado());
        dto.setValorAntigo(model.getValorAntigo());
        dto.setValorNovo(model.getValorNovo());
        dto.setDataHora(model.getDataHora());
        dto.setUsuarioId(model.getUsuario().getUsuarioId());
        return dto;
    }

    // Converte DTO → Model
    public static HistoricoAlteracao toModel(HistoricoAlteracaoDTO dto, Usuario usuario) {
        HistoricoAlteracao model = new HistoricoAlteracao();

        // Evita forçar ID=0 no model em operações de CREATE (deixe o JPA gerar)
        if (dto.getId() > 0) {
            model.setId(dto.getId());
        }

        model.setEntidade(dto.getEntidade());
        model.setEntidadeId(dto.getEntidadeId());
        model.setCampoAlterado(dto.getCampoAlterado());
        model.setValorAntigo(dto.getValorAntigo());
        model.setValorNovo(dto.getValorNovo());
        model.setDataHora(dto.getDataHora()); // se vier nulo, o service garante now()
        model.setUsuario(usuario);
        return model;
    }
}
