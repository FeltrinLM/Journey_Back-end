package com.example.journey_backend.service;

import com.example.journey_backend.dto.PecaDTO;
import com.example.journey_backend.dto.HistoricoAlteracaoDTO; // Importado
import com.example.journey_backend.mapper.PecaMapper;
import com.example.journey_backend.model.Peca;
import com.example.journey_backend.model.Usuario; // Importado
import com.example.journey_backend.repository.PecaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects; // Importado
import java.util.stream.Collectors;

@Service
public class PecaService {

    @Autowired
    private PecaRepository pecaRepository;

    @Autowired
    private HistoricoAlteracaoService historicoService; // Injetado

    // Listar todas as peças
    public List<PecaDTO> listarPecas() {
        return pecaRepository.findAll().stream()
                .map(PecaMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar peça por ID
    public PecaDTO buscarPorId(int id) {
        Peca peca = pecaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Peça não encontrada com ID: " + id));
        return PecaMapper.toDTO(peca);
    }

    // Criar nova peça
    public PecaDTO criarPeca(PecaDTO dto, Usuario autor) {
        Peca novaPeca = PecaMapper.toModel(dto);
        Peca salva = pecaRepository.save(novaPeca);

        // --- Log de Criação ---
        registrarLog(autor.getUsuarioId(), "Peca", salva.getPecaId(), "tipo", null, salva.getTipo());
        registrarLog(autor.getUsuarioId(), "Peca", salva.getPecaId(), "tamanho", null, salva.getTamanho());
        registrarLog(autor.getUsuarioId(), "Peca", salva.getPecaId(), "cor", null, salva.getCor());
        registrarLog(autor.getUsuarioId(), "Peca", salva.getPecaId(), "quantidade", null, String.valueOf(salva.getQuantidade()));
        // --- Fim do Log ---

        return PecaMapper.toDTO(salva);
    }

    // Editar peça existente
    public PecaDTO editarPeca(int id, PecaDTO dto, Usuario autor) {
        Peca existente = pecaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Peça não encontrada com ID: " + id));

        // Guarda valores antigos
        String tipoAntigo = existente.getTipo();
        String tamanhoAntigo = existente.getTamanho();
        String corAntiga = existente.getCor();
        int qtdAntiga = existente.getQuantidade();

        // Atualiza
        existente.setTipo(dto.getTipo());
        existente.setTamanho(dto.getTamanho());
        existente.setCor(dto.getCor());
        existente.setQuantidade(dto.getQuantidade());
        Peca atualizada = pecaRepository.save(existente);

        // --- Log de Edição ---
        registrarLog(autor.getUsuarioId(), "Peca", id, "tipo", tipoAntigo, atualizada.getTipo());
        registrarLog(autor.getUsuarioId(), "Peca", id, "tamanho", tamanhoAntigo, atualizada.getTamanho());
        registrarLog(autor.getUsuarioId(), "Peca", id, "cor", corAntiga, atualizada.getCor());
        registrarLog(autor.getUsuarioId(), "Peca", id, "quantidade", String.valueOf(qtdAntiga), String.valueOf(atualizada.getQuantidade()));
        // --- Fim do Log ---

        return PecaMapper.toDTO(atualizada);
    }

    // Deletar peça por ID
    public void deletarPeca(int id, Usuario autor) {
        Peca peca = pecaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Peça não encontrada com ID: " + id));

        // --- Log de Deleção ---
        registrarLog(autor.getUsuarioId(), "Peca", id, "tipo", peca.getTipo(), null);
        registrarLog(autor.getUsuarioId(), "Peca", id, "tamanho", peca.getTamanho(), null);
        registrarLog(autor.getUsuarioId(), "Peca", id, "cor", peca.getCor(), null);
        registrarLog(autor.getUsuarioId(), "Peca", id, "quantidade", String.valueOf(peca.getQuantidade()), null);
        // --- Fim do Log ---

        pecaRepository.deleteById(id);
    }

    /**
     * Método helper para registrar alterações
     */
    private void registrarLog(Integer autorId, String entidade, int entidadeId, String campo, String valorAntigo, String valorNovo) {
        if (Objects.equals(valorAntigo, valorNovo)) {
            return;
        }
        HistoricoAlteracaoDTO logDTO = new HistoricoAlteracaoDTO();
        logDTO.setEntidade(entidade);
        logDTO.setEntidadeId(entidadeId);
        logDTO.setCampoAlterado(campo);
        logDTO.setValorAntigo(valorAntigo);
        logDTO.setValorNovo(valorNovo);
        logDTO.setUsuarioId(autorId);
        historicoService.registrarAlteracao(logDTO);
    }
}