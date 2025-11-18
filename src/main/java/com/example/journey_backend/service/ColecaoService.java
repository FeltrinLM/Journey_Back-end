package com.example.journey_backend.service;

import com.example.journey_backend.dto.ColecaoDTO;
import com.example.journey_backend.dto.HistoricoAlteracaoDTO; // Importado
import com.example.journey_backend.mapper.ColecaoMapper;
import com.example.journey_backend.model.Colecao;
import com.example.journey_backend.model.Usuario; // Importado
import com.example.journey_backend.repository.ColecaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate; // Importado
import java.util.List;
import java.util.Objects; // Importado
import java.util.stream.Collectors;

@Service
public class ColecaoService {

    @Autowired
    private ColecaoRepository colecaoRepository;

    @Autowired
    private HistoricoAlteracaoService historicoService; // Injetado

    // Listar todas as coleções
    public List<ColecaoDTO> listarColecoes() {
        return colecaoRepository.findAll().stream()
                .map(ColecaoMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar coleção por ID
    public ColecaoDTO buscarPorId(int id) {
        Colecao colecao = colecaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Coleção não encontrada com ID: " + id));
        return ColecaoMapper.toDTO(colecao);
    }

    // Criar nova coleção
    public ColecaoDTO criarColecao(ColecaoDTO dto, Usuario autor) {
        Colecao novaColecao = ColecaoMapper.toModel(dto);
        Colecao salva = colecaoRepository.save(novaColecao);

        // --- Log de Criação ---
        registrarLog(autor.getUsuarioId(), "Colecao", salva.getColecaoId(), "nome", null, salva.getNome());
        registrarLog(autor.getUsuarioId(), "Colecao", salva.getColecaoId(), "dataInicio", null, String.valueOf(salva.getDataInicio()));
        registrarLog(autor.getUsuarioId(), "Colecao", salva.getColecaoId(), "dataFim", null, String.valueOf(salva.getDataFim()));
        // --- Fim do Log ---

        return ColecaoMapper.toDTO(salva);
    }

    // Editar coleção existente
    public ColecaoDTO editarColecao(int id, ColecaoDTO dto, Usuario autor) {
        Colecao existente = colecaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Coleção não encontrada com ID: " + id));

        // Guarda valores antigos
        String nomeAntigo = existente.getNome();
        LocalDate dataInicioAntiga = existente.getDataInicio();
        LocalDate dataFimAntiga = existente.getDataFim();

        // Atualiza
        existente.setNome(dto.getNome());
        existente.setDataInicio(dto.getDataInicio());
        existente.setDataFim(dto.getDataFim());
        Colecao atualizada = colecaoRepository.save(existente);

        // --- Log de Edição ---
        registrarLog(autor.getUsuarioId(), "Colecao", id, "nome", nomeAntigo, atualizada.getNome());
        registrarLog(autor.getUsuarioId(), "Colecao", id, "dataInicio", String.valueOf(dataInicioAntiga), String.valueOf(atualizada.getDataInicio()));
        registrarLog(autor.getUsuarioId(), "Colecao", id, "dataFim", String.valueOf(dataFimAntiga), String.valueOf(atualizada.getDataFim()));
        // --- Fim do Log ---

        return ColecaoMapper.toDTO(atualizada);
    }

    // Deletar coleção por ID
    public void deletarColecao(int id, Usuario autor) {
        Colecao colecao = colecaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Coleção não encontrada com ID: " + id));

        // --- Log de Deleção ---
        registrarLog(autor.getUsuarioId(), "Colecao", id, "nome", colecao.getNome(), null);
        registrarLog(autor.getUsuarioId(), "Colecao", id, "dataInicio", String.valueOf(colecao.getDataInicio()), null);
        registrarLog(autor.getUsuarioId(), "Colecao", id, "dataFim", String.valueOf(colecao.getDataFim()), null);
        // --- Fim do Log ---

        colecaoRepository.deleteById(id);
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