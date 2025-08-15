package com.example.journey_backend.mapper;

import com.example.journey_backend.model.Usuario;
import com.example.journey_backend.model.Usuario.TipoUsuario;
import com.example.journey_backend.dto.UsuarioDTO;

public class UsuarioMapper {

    // Model → DTO
    public static UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) return null;

        UsuarioDTO dto = new UsuarioDTO();
        dto.setUsuario_id(usuario.getUsuario_id());
        dto.setNome(usuario.getNome());
        dto.setTipo(usuario.getTipo().name()); // converte enum para string
        return dto;
    }

    // DTO → Model
    public static Usuario toModel(UsuarioDTO dto) {
        if (dto == null) return null;

        Usuario usuario = new Usuario();
        usuario.setUsuario_id(dto.getUsuario_id());
        usuario.setNome(dto.getNome());
        usuario.setTipo(TipoUsuario.valueOf(dto.getTipo())); // converte string para enum
        return usuario;
    }
}
