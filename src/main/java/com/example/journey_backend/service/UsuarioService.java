package com.example.journey_backend.service;

import com.example.journey_backend.dto.UsuarioDTO;
import com.example.journey_backend.mapper.UsuarioMapper;
import com.example.journey_backend.model.Usuario;
import com.example.journey_backend.model.Usuario.TipoUsuario;
import com.example.journey_backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Listar todos os usuários
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(UsuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar usuário por ID
    public UsuarioDTO buscarPorId(int id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.map(UsuarioMapper::toDTO).orElse(null);
    }

    // Criar novo usuário
    public UsuarioDTO criarUsuario(UsuarioDTO dto) {
        if (usuarioRepository.existsByNome(dto.getNome())) {
            throw new RuntimeException("Usuário com esse nome já existe.");
        }
        Usuario novoUsuario = UsuarioMapper.toModel(dto);
        Usuario salvo = usuarioRepository.save(novoUsuario);
        return UsuarioMapper.toDTO(salvo);
    }

    // Editar usuário existente
    public UsuarioDTO editarUsuario(int id, UsuarioDTO dto) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuarioExistente.setNome(dto.getNome());
        usuarioExistente.setTipo(TipoUsuario.valueOf(dto.getTipo().toUpperCase()));

        Usuario atualizado = usuarioRepository.save(usuarioExistente);
        return UsuarioMapper.toDTO(atualizado);
    }

    // Deletar usuário por ID
    public void deletarUsuario(int id) {
        usuarioRepository.deleteById(id);
    }

    // Validação de login
    public boolean validarLogin(String nome, String senha) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByNome(nome);
        return usuarioOpt.map(usuario -> usuario.getSenha().equals(senha)).orElse(false);
    }

    // Verificar se não há nenhum usuário no sistema
    public boolean sistemaSemUsuarios() {
        return usuarioRepository.findAll().isEmpty();
    }

    // Criar usuário administrador padrão
    public UsuarioDTO criarAdminPadrao() {
        if (!sistemaSemUsuarios()) {
            throw new IllegalStateException("Já existem usuários no sistema.");
        }

        Usuario admin = new Usuario();
        admin.setNome("admin");
        admin.setSenha("admin"); // ⚠️ Em produção, aplicar hash seguro
        admin.setTipo(TipoUsuario.ADMINISTRADOR);

        Usuario salvo = usuarioRepository.save(admin);
        return UsuarioMapper.toDTO(salvo);
    }
}
