package com.example.journey_backend.mapper;

import com.example.journey_backend.dto.UsuarioDTO;
import com.example.journey_backend.model.Usuario;
import com.example.journey_backend.model.Usuario.TipoUsuario;

/**
 * Conversões entre Model <-> DTO.
 *
 * Observações:
 * - toDTO NÃO inclui senha (senha é WRITE_ONLY no DTO).
 * - toModel apenas seta senha quando dto.getSenha() for não nula e não vazia,
 *   evitando sobrescrever a senha existente com null ao editar.
 */
public final class UsuarioMapper {

    /**
     * Construtor privado para impedir a instanciação desta classe utilitária.
     */
    private UsuarioMapper() {
        // Construtor intencionalmente vazio e privado
    }

    // Model → DTO
    public static UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null){
            return null;
        }

        UsuarioDTO dto = new UsuarioDTO();
        dto.setUsuarioId(usuario.getUsuarioId());
        dto.setNome(usuario.getNome());
        dto.setTipo(usuario.getTipo() == null ? null : usuario.getTipo().name());
        // intentionally do NOT expose senha
        return dto;
    }

    // DTO → Model
    public static Usuario toModel(UsuarioDTO dto) {
        if (dto == null){
            return null;
        }

        Usuario usuario = new Usuario();
        usuario.setUsuarioId(dto.getUsuarioId());
        usuario.setNome(dto.getNome());

        if (dto.getTipo() != null) {
            try {
                usuario.setTipo(TipoUsuario.valueOf(dto.getTipo().toUpperCase()));
            } catch (IllegalArgumentException ex) {
                // deixar a exceção subir ou tratar conforme sua política — por enquanto lança Runtime
                throw new IllegalArgumentException("Tipo de usuário inválido: " + dto.getTipo(), ex);
            }
        }

        // Somente setar senha se for informada (evita sobrescrever com null em updates)
        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            usuario.setSenha(dto.getSenha());
        }

        return usuario;
    }

    /**
     * Útil quando você quer atualizar um Usuario existente a partir de um DTO (ex: em editarUsuario).
     * Copia apenas campos permitidos: nome, tipo e (opcionalmente) senha.
     */
    public static void updateModelFromDTO(UsuarioDTO dto, Usuario usuarioExistente) {
        if (dto == null || usuarioExistente == null){
            return;
        }

        if (dto.getNome() != null && !dto.getNome().isBlank()) {
            usuarioExistente.setNome(dto.getNome());
        }

        if (dto.getTipo() != null) {
            usuarioExistente.setTipo(TipoUsuario.valueOf(dto.getTipo().toUpperCase()));
        }

        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            usuarioExistente.setSenha(dto.getSenha());
        }
    }
}
