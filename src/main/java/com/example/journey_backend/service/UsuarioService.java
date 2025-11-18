package com.example.journey_backend.service;

import com.example.journey_backend.dto.HistoricoAlteracaoDTO;
import com.example.journey_backend.dto.UsuarioDTO;
import com.example.journey_backend.mapper.UsuarioMapper;
import com.example.journey_backend.model.Usuario;
import com.example.journey_backend.model.Usuario.TipoUsuario;
import com.example.journey_backend.repository.HistoricoAlteracaoRepository; // RE-IMPORTADO
import com.example.journey_backend.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private HistoricoAlteracaoService historicoService; // Injetado (Correto)

    // RE-INJETADO para a regra de deleção
    @Autowired
    private HistoricoAlteracaoRepository historicoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // --- CORREÇÃO: CORPO DO MÉTODO RESTAURADO ---
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(UsuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    // --- CORREÇÃO: CORPO DO MÉTODO RESTAURADO ---
    public UsuarioDTO buscarPorId(int id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + id));
        return UsuarioMapper.toDTO(usuario);
    }

    // --- CORREÇÃO: CORPO DO MÉTODO RESTAURADO ---
    @Transactional
    public UsuarioDTO criarUsuario(UsuarioDTO dto) {
        if (usuarioRepository.existsByNome(dto.getNome())) {
            throw new IllegalArgumentException("Usuário com esse nome já existe.");
        }
        Usuario novoUsuario = UsuarioMapper.toModel(dto);
        String senhaHash = passwordEncoder.encode(dto.getSenha());
        novoUsuario.setSenha(senhaHash);
        Usuario salvo = usuarioRepository.save(novoUsuario);
        return UsuarioMapper.toDTO(salvo);
    }

    // Criar novo usuário (com autor)
    @Transactional
    public UsuarioDTO criarUsuario(UsuarioDTO dto, Usuario autor) {
        if (usuarioRepository.existsByNome(dto.getNome())) {
            throw new IllegalArgumentException("Usuário com esse nome já existe.");
        }
        Usuario novoUsuario = UsuarioMapper.toModel(dto);
        String senhaHash = passwordEncoder.encode(dto.getSenha());
        novoUsuario.setSenha(senhaHash);
        Usuario salvo = usuarioRepository.save(novoUsuario);

        logAlteracao(autor.getUsuarioId(),
                "Usuario", salvo.getUsuarioId(),
                "__CREATE__", null,
                "nome=" + salvo.getNome() + "; tipo=" + salvo.getTipo());

        return UsuarioMapper.toDTO(salvo);
    }

    // --- CORREÇÃO: CORPO DO MÉTODO RESTAURADO ---
    @Transactional
    public UsuarioDTO editarUsuario(int id, UsuarioDTO dto) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + id));

        usuarioExistente.setNome(dto.getNome());
        usuarioExistente.setTipo(TipoUsuario.valueOf(dto.getTipo().toUpperCase()));

        Usuario atualizado = usuarioRepository.save(usuarioExistente);
        return UsuarioMapper.toDTO(atualizado);
    }

    // Editar usuário existente (com autor)
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
                logAlteracao(autor.getUsuarioId(), "Usuario", atualizado.getUsuarioId(),
                        "nome", nomeAntigo, atualizado.getNome());
            }
            if (!tipoAntigo.equals(atualizado.getTipo())) {
                logAlteracao(autor.getUsuarioId(), "Usuario", atualizado.getUsuarioId(),
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
        // --- CORREÇÃO: REGRA DE NEGÓCIO RESTAURADA ---
        if (historicoRepository.existsByUsuarioUsuarioId(id)) {
            throw new IllegalStateException("Não é possível deletar um usuário que possui histórico de alterações.");
        }
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário não encontrado com ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }


    // Deletar usuário por ID (com autor)
    @Transactional
    public void deletarUsuario(int id, Usuario autor) {
        Usuario alvo = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + id));

        // --- CORREÇÃO: REGRA DE NEGÓCIO RESTAURADA ---
        if (historicoRepository.existsByUsuarioUsuarioId(id)) {
            throw new IllegalStateException("Não é possível deletar um usuário que possui histórico de alterações.");
        }

        logAlteracao(autor.getUsuarioId(),
                "Usuario", alvo.getUsuarioId(),
                "__DELETE__", "nome=" + alvo.getNome() + "; tipo=" + alvo.getTipo(), null);

        usuarioRepository.deleteById(id);
    }

    // --- CORREÇÃO: CORPO DO MÉTODO RESTAURADO ---
    public boolean validarLogin(String nome, String senha) {
        return usuarioRepository.findByNome(nome)
                .map(usuario -> passwordEncoder.matches(senha, usuario.getSenha()))
                .orElse(false);
    }

    // --- CORREÇÃO: CORPO DO MÉTODO RESTAURADO ---
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
        admin.setNome("admin_master");
        String senhaPadrao = "admin12345";
        String senhaHash = passwordEncoder.encode(senhaPadrao);
        admin.setSenha(senhaHash);
        admin.setTipo(TipoUsuario.ADMINISTRADOR);
        Usuario salvo = usuarioRepository.save(admin);

        logAlteracao(salvo.getUsuarioId(), // Admin loga sua própria criação
                "Usuario", salvo.getUsuarioId(),
                "__CREATE_ADMIN__", null,
                "nome=" + salvo.getNome() + "; tipo=" + salvo.getTipo());

        return UsuarioMapper.toDTO(salvo);
    }

    // ================== Helpers de histórico (ATUALIZADO) ==================
    private void logAlteracao(Integer autorId,
                              String entidade,
                              int entidadeId,
                              String campoAlterado,
                              String valorAntigo,
                              String valorNovo) {
        if (autorId == null) return;

        HistoricoAlteracaoDTO dto = new HistoricoAlteracaoDTO();
        dto.setEntidade(entidade);
        dto.setEntidadeId(entidadeId);
        dto.setCampoAlterado(campoAlterado);
        dto.setValorAntigo(valorAntigo);
        dto.setValorNovo(valorNovo);
        dto.setUsuarioId(autorId);

        historicoService.registrarAlteracao(dto);
    }
}