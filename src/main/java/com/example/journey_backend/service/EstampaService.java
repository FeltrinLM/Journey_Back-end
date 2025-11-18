package com.example.journey_backend.service;

import com.example.journey_backend.dto.EstampaDTO;
import com.example.journey_backend.dto.HistoricoAlteracaoDTO; // Importado
import com.example.journey_backend.mapper.EstampaMapper;
import com.example.journey_backend.model.Colecao;
import com.example.journey_backend.model.Estampa;
import com.example.journey_backend.model.Usuario; // Importado
import com.example.journey_backend.repository.ColecaoRepository;
import com.example.journey_backend.repository.EstampaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects; // Importado
import java.util.stream.Collectors;

@Service
public class EstampaService {

    @Autowired
    private EstampaRepository estampaRepository;

    @Autowired
    private ColecaoRepository colecaoRepository;

    @Autowired
    private HistoricoAlteracaoService historicoService; // Injetado

    // Listar todas as estampas
    public List<EstampaDTO> listarEstampas() {
        return estampaRepository.findAll().stream()
                .map(EstampaMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar estampa por ID
    public EstampaDTO buscarPorId(int id) {
        Estampa estampa = estampaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estampa não encontrada com ID: " + id));
        return EstampaMapper.toDTO(estampa);
    }

    // Criar nova estampa
    public EstampaDTO criarEstampa(EstampaDTO dto, Usuario autor) {
        Colecao colecao = colecaoRepository.findById(dto.getColecaoId())
                .orElseThrow(() -> new EntityNotFoundException("Coleção não encontrada com ID: " + dto.getColecaoId()));

        Estampa nova = EstampaMapper.toModel(dto, colecao);
        Estampa salva = estampaRepository.save(nova);

        // --- Log de Criação ---
        registrarLog(autor.getUsuarioId(), "Estampa", salva.getEstampaId(), "nome", null, salva.getNome());
        registrarLog(autor.getUsuarioId(), "Estampa", salva.getEstampaId(), "quantidade", null, String.valueOf(salva.getQuantidade()));
        registrarLog(autor.getUsuarioId(), "Estampa", salva.getEstampaId(), "colecaoId", null, String.valueOf(salva.getColecao().getColecaoId()));
        // --- Fim do Log ---

        return EstampaMapper.toDTO(salva);
    }

    // Editar estampa existente
    public EstampaDTO editarEstampa(int id, EstampaDTO dto, Usuario autor) {
        Estampa existente = estampaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estampa não encontrada com ID: " + id));

        Colecao colecao = colecaoRepository.findById(dto.getColecaoId())
                .orElseThrow(() -> new EntityNotFoundException("Coleção não encontrada com ID: " + dto.getColecaoId()));

        // Guarda valores antigos
        String nomeAntigo = existente.getNome();
        int qtdAntiga = existente.getQuantidade();
        String colecaoAntiga = String.valueOf(existente.getColecao().getColecaoId());

        // Atualiza
        existente.setNome(dto.getNome());
        existente.setQuantidade(dto.getQuantidade());
        existente.setColecao(colecao);
        Estampa atualizada = estampaRepository.save(existente);

        // --- Log de Edição ---
        registrarLog(autor.getUsuarioId(), "Estampa", id, "nome", nomeAntigo, atualizada.getNome());
        registrarLog(autor.getUsuarioId(), "Estampa", id, "quantidade", String.valueOf(qtdAntiga), String.valueOf(atualizada.getQuantidade()));
        registrarLog(autor.getUsuarioId(), "Estampa", id, "colecaoId", colecaoAntiga, String.valueOf(atualizada.getColecao().getColecaoId()));
        // --- Fim do Log ---

        return EstampaMapper.toDTO(atualizada);
    }

    // Deletar estampa por ID
    public void deletarEstampa(int id, Usuario autor) {
        Estampa estampa = estampaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estampa não encontrada com ID: " + id));

        // --- Log de Deleção ---
        registrarLog(autor.getUsuarioId(), "Estampa", id, "nome", estampa.getNome(), null);
        registrarLog(autor.getUsuarioId(), "Estampa", id, "quantidade", String.valueOf(estampa.getQuantidade()), null);
        registrarLog(autor.getUsuarioId(), "Estampa", id, "colecaoId", String.valueOf(estampa.getColecao().getColecaoId()), null);
        // --- Fim do Log ---

        estampaRepository.deleteById(id);
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