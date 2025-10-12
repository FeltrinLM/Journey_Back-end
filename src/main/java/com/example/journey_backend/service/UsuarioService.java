package com.example.journey_backend.service;

import com.example.journey_backend.dto.UsuarioDTO;
import com.example.journey_backend.mapper.UsuarioMapper;
import com.example.journey_backend.model.Usuario;
import com.example.journey_backend.model.Usuario.TipoUsuario;
import com.example.journey_backend.model.HistoricoAlteracao;
import com.example.journey_backend.repository.UsuarioRepository;
import com.example.journey_backend.repository.HistoricoAlteracaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private HistoricoAlteracaoRepository historicoRepository;

    // Listar todos os usuários
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(UsuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar usuário por ID
    public UsuarioDTO buscarPorId(int id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + id));
        return UsuarioMapper.toDTO(usuario);
    }

    // Criar novo usuário
    @Transactional
    public UsuarioDTO criarUsuario(UsuarioDTO dto) {
        if (usuarioRepository.existsByNome(dto.getNome())) {
            throw new IllegalArgumentException("Usuário com esse nome já existe.");
        }
        Usuario novoUsuario = UsuarioMapper.toModel(dto);
        Usuario salvo = usuarioRepository.save(novoUsuario);
        return UsuarioMapper.toDTO(salvo);
    }

    // Criar novo usuário (com autor para registrar histórico)
    @Transactional
    public UsuarioDTO criarUsuario(UsuarioDTO dto, Usuario autor) {
        if (usuarioRepository.existsByNome(dto.getNome())) {
            throw new IllegalArgumentException("Usuário com esse nome já existe.");
        }
        Usuario novoUsuario = UsuarioMapper.toModel(dto);
        Usuario salvo = usuarioRepository.save(novoUsuario);

        logAlteracao(autor,
                "Usuario", salvo.getUsuarioId(),
                "__CREATE__", null,
                "nome=" + salvo.getNome() + "; tipo=" + salvo.getTipo());

        return UsuarioMapper.toDTO(salvo);
    }

    // Editar usuário existente
    @Transactional
    public UsuarioDTO editarUsuario(int id, UsuarioDTO dto) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + id));

        usuarioExistente.setNome(dto.getNome());
        usuarioExistente.setTipo(TipoUsuario.valueOf(dto.getTipo().toUpperCase()));

        Usuario atualizado = usuarioRepository.save(usuarioExistente);
        return UsuarioMapper.toDTO(atualizado);
    }

    // Editar usuário existente (com autor para registrar histórico)
    @Transactional
    public UsuarioDTO editarUsuario(int id, UsuarioDTO dto, Usuario autor) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + id));

        String nomeAntigo = usuarioExistente.getNome();
        TipoUsuario tipoAntigo = usuarioExistente.getTipo();

        usuarioExistente.setNome(dto.getNome());
        usuarioExistente.setTipo(TipoUsuario.valueOf(dto.getTipo().toUpperCase()));

        Usuario atualizado = usuarioRepository.save(usuarioExistente);

        if (autor != null) {
            if (!nomeAntigo.equals(atualizado.getNome())) {
                logAlteracao(autor, "Usuario", atualizado.getUsuarioId(),
                        "nome", nomeAntigo, atualizado.getNome());
            }
            if (!tipoAntigo.equals(atualizado.getTipo())) {
                logAlteracao(autor, "Usuario", atualizado.getUsuarioId(),
                        "tipo",
                        tipoAntigo == null ? null : tipoAntigo.name(),
                        atualizado.getTipo() == null ? null : atualizado.getTipo().name());
            }
        }

        return UsuarioMapper.toDTO(atualizado);
    }

    // Deletar usuário por ID
    @Transactional
    public void deletarUsuario(int id) {
        if (historicoRepository.existsByUsuarioUsuarioId(id)) {
            throw new IllegalStateException("Não é possível deletar um usuário que possui histórico de alterações.");
        }
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário não encontrado com ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    // Deletar usuário por ID (com autor para registrar histórico)
    @Transactional
    public void deletarUsuario(int id, Usuario autor) {
        Usuario alvo = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + id));

        if (historicoRepository.existsByUsuarioUsuarioId(id)) {
            throw new IllegalStateException("Não é possível deletar um usuário que possui histórico de alterações.");
        }

        logAlteracao(autor,
                "Usuario", alvo.getUsuarioId(),
                "__DELETE__", "nome=" + alvo.getNome() + "; tipo=" + alvo.getTipo(), null);

        usuarioRepository.deleteById(id);
    }

    // Validação de login
    public boolean validarLogin(String nome, String senha) {
        return usuarioRepository.findByNome(nome)
                .map(usuario -> usuario.getSenha().equals(senha))
                .orElse(false);
    }

    // Verificar se não há nenhum usuário no sistema
    public boolean sistemaSemUsuarios() {
        return usuarioRepository.count() == 0;
    }

    // Criar usuário administrador padrão
    @Transactional
    public UsuarioDTO criarAdminPadrao() {
        if (!sistemaSemUsuarios()) {
            throw new IllegalStateException("Já existem usuários no sistema.");
        }

        Usuario admin = new Usuario();
        admin.setNome("admin");
        admin.setSenha("administrador"); // ⚠️ em produção, aplicar hash seguro
        admin.setTipo(TipoUsuario.ADMINISTRADOR);

        Usuario salvo = usuarioRepository.save(admin);

        logAlteracao(salvo,
                "Usuario", salvo.getUsuarioId(),
                "__CREATE_ADMIN__", null,
                "nome=" + salvo.getNome() + "; tipo=" + salvo.getTipo());

        return UsuarioMapper.toDTO(salvo);
    }

    // ================== Helpers de histórico ==================
    private void logAlteracao(Usuario autor,
                              String entidade,
                              int entidadeId,
                              String campoAlterado,
                              String valorAntigo,
                              String valorNovo) {
        if (autor == null) return;

        HistoricoAlteracao h = new HistoricoAlteracao();
        h.setEntidade(entidade);
        h.setEntidadeId(entidadeId);
        h.setCampoAlterado(campoAlterado);
        h.setValorAntigo(valorAntigo);
        h.setValorNovo(valorNovo);
        h.setDataHora(LocalDateTime.now());
        h.setUsuario(autor);

        historicoRepository.save(h);
    }
}
