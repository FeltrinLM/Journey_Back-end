package com.example.journey_backend.mapper;

import com.example.journey_backend.model.Usuario;
import com.example.journey_backend.model.Usuario.TipoUsuario;
import com.example.journey_backend.dto.UsuarioDTO;

public class UsuarioMapper {

    // Model → DTO
    public static UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) return null;

        UsuarioDTO dto = new UsuarioDTO();
        dto.setUsuarioId(usuario.getUsuarioId());
        dto.setNome(usuario.getNome());
        dto.setTipo(usuario.getTipo().name()); // converte enum para string
        return dto;
    }

    // DTO → Model
    public static Usuario toModel(UsuarioDTO dto) {
        if (dto == null) return null;

        Usuario usuario = new Usuario();
        usuario.setUsuarioId(dto.getUsuarioId());
        usuario.setNome(dto.getNome());
        // converte string para enum (normaliza para maiúsculas para evitar IllegalArgumentException)
        if (dto.getTipo() != null) {
            usuario.setTipo(TipoUsuario.valueOf(dto.getTipo().toUpperCase()));
        }
        return usuario;
    }
}
