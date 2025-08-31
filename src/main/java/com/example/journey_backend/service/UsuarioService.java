package com.example.journey_backend.service;

import com.example.journey_backend.dto.UsuarioDTO;
import com.example.journey_backend.mapper.UsuarioMapper;
import com.example.journey_backend.model.Usuario;
import com.example.journey_backend.model.Usuario.TipoUsuario;
import com.example.journey_backend.model.HistoricoAlteracao;
import com.example.journey_backend.repository.UsuarioRepository;
import com.example.journey_backend.repository.HistoricoAlteracaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // transações

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
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.map(UsuarioMapper::toDTO).orElse(null);
    }

    // Criar novo usuário
    @Transactional
    public UsuarioDTO criarUsuario(UsuarioDTO dto) {
        if (usuarioRepository.existsByNome(dto.getNome())) {
            throw new RuntimeException("Usuário com esse nome já existe.");
        }
        Usuario novoUsuario = UsuarioMapper.toModel(dto);
        Usuario salvo = usuarioRepository.save(novoUsuario);
        return UsuarioMapper.toDTO(salvo);
    }

    // Criar novo usuário (com autor para registrar histórico)
    @Transactional
    public UsuarioDTO criarUsuario(UsuarioDTO dto, Usuario autor) {
        if (usuarioRepository.existsByNome(dto.getNome())) {
            throw new RuntimeException("Usuário com esse nome já existe.");
        }
        Usuario novoUsuario = UsuarioMapper.toModel(dto);
        Usuario salvo = usuarioRepository.save(novoUsuario);

        // Registrar criação (se autor informado)
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
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuarioExistente.setNome(dto.getNome());
        usuarioExistente.setTipo(TipoUsuario.valueOf(dto.getTipo().toUpperCase()));

        Usuario atualizado = usuarioRepository.save(usuarioExistente);
        return UsuarioMapper.toDTO(atualizado);
    }

    // Editar usuário existente (com autor para registrar histórico)
    @Transactional
    public UsuarioDTO editarUsuario(int id, UsuarioDTO dto, Usuario autor) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Captura valores antigos para comparação
        String nomeAntigo = usuarioExistente.getNome();
        TipoUsuario tipoAntigo = usuarioExistente.getTipo();

        // Aplica alterações
        usuarioExistente.setNome(dto.getNome());
        usuarioExistente.setTipo(TipoUsuario.valueOf(dto.getTipo().toUpperCase()));

        Usuario atualizado = usuarioRepository.save(usuarioExistente);

        // Registrar alterações campo a campo (se autor informado)
        if (autor != null) {
            if (nomeAntigo != null ? !nomeAntigo.equals(atualizado.getNome()) : atualizado.getNome() != null) {
                logAlteracao(autor, "Usuario", atualizado.getUsuarioId(),
                        "nome", nomeAntigo, atualizado.getNome());
            }
            if (tipoAntigo != null ? !tipoAntigo.equals(atualizado.getTipo()) : atualizado.getTipo() != null) {
                logAlteracao(autor, "Usuario", atualizado.getUsuarioId(),
                        "tipo",
                        tipoAntigo == null ? null : tipoAntigo.name(),
                        atualizado.getTipo() == null ? null : atualizado.getTipo().name());
            }
            // ⚠️ intencionalmente NÃO logamos senha aqui.
        }

        return UsuarioMapper.toDTO(atualizado);
    }

    // Deletar usuário por ID
    @Transactional
    public void deletarUsuario(int id) {
        // Evita violar FK de histórico (usuarioId não pode ser nulo)
        if (historicoRepository.existsByUsuarioUsuarioId(id)) {
            throw new IllegalStateException("Não é possível deletar um usuário que possui histórico de alterações.");
        }
        usuarioRepository.deleteById(id);
    }

    // Deletar usuário por ID (com autor para registrar histórico)
    @Transactional
    public void deletarUsuario(int id, Usuario autor) {
        Usuario alvo = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (historicoRepository.existsByUsuarioUsuarioId(id)) {
            throw new IllegalStateException("Não é possível deletar um usuário que possui histórico de alterações.");
        }

        // Registrar intenção de deleção (se autor informado)
        logAlteracao(autor,
                "Usuario", alvo.getUsuarioId(),
                "__DELETE__", "nome=" + alvo.getNome() + "; tipo=" + alvo.getTipo(), null);

        usuarioRepository.deleteById(id);
    }

    // Validação de login
    public boolean validarLogin(String nome, String senha) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByNome(nome);
        return usuarioOpt.map(usuario -> usuario.getSenha().equals(senha)).orElse(false);
    }

    // Verificar se não há nenhum usuário no sistema
    public boolean sistemaSemUsuarios() {
        return usuarioRepository.count() == 0; // mais eficiente que findAll().isEmpty()
    }

    // Criar usuário administrador padrão
    @Transactional
    public UsuarioDTO criarAdminPadrao() {
        if (!sistemaSemUsuarios()) {
            throw new IllegalStateException("Já existem usuários no sistema.");
        }

        Usuario admin = new Usuario();
        admin.setNome("admin");
        admin.setSenha("admin"); // ⚠️ Em produção, aplicar hash seguro
        admin.setTipo(TipoUsuario.ADMINISTRADOR);

        Usuario salvo = usuarioRepository.save(admin);

        // Registrar criação do admin padrão usando ele mesmo como autor
        logAlteracao(salvo,
                "Usuario", salvo.getUsuarioId(),
                "__CREATE_ADMIN__", null,
                "nome=" + salvo.getNome() + "; tipo=" + salvo.getTipo());

        return UsuarioMapper.toDTO(salvo);
    }

    // ================== Helpers de histórico ==================

    // Cria e persiste um registro de histórico (somente se autor != null)
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
        h.setUsuario(autor); // usuário que realizou a ação

        historicoRepository.save(h);
    }
}
