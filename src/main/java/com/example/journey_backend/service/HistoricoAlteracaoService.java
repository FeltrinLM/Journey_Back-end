package com.example.journey_backend.service;

import com.example.journey_backend.dto.HistoricoAlteracaoDTO;
import com.example.journey_backend.mapper.HistoricoAlteracaoMapper;
import com.example.journey_backend.model.HistoricoAlteracao;
import com.example.journey_backend.model.Usuario;
import com.example.journey_backend.repository.HistoricoAlteracaoRepository;
import com.example.journey_backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // transações

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HistoricoAlteracaoService {

    @Autowired
    private HistoricoAlteracaoRepository historicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Retorna a lista de todas as alterações feitas no sistema
     */
    public List<HistoricoAlteracaoDTO> listarAlteracoes() {
        return historicoRepository.findAllByOrderByDataHoraDesc().stream()
                .map(HistoricoAlteracaoMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retorna uma alteração específica pelo ID
     */
    public HistoricoAlteracaoDTO buscarPorId(Long id) {
        Optional<HistoricoAlteracao> historico = historicoRepository.findById(id);
        return historico.map(HistoricoAlteracaoMapper::toDTO).orElse(null);
    }

    /**
     * Registra uma nova alteração feita no sistema.
     * Deve ser chamada internamente ao alterar um item.
     */
    @Transactional
    public void registrarAlteracao(HistoricoAlteracaoDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado para registrar histórico."));

        HistoricoAlteracao model = HistoricoAlteracaoMapper.toModel(dto, usuario);

        // Garante data/hora mesmo que o mapper não tenha preenchido
        if (model.getDataHora() == null) {
            model.setDataHora(LocalDateTime.now());
        }

        historicoRepository.save(model);
    }
}
