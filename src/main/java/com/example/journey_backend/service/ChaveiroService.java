package com.example.journey_backend.service;

import com.example.journey_backend.dto.ChaveiroDTO;
import com.example.journey_backend.dto.HistoricoAlteracaoDTO; // Importado
import com.example.journey_backend.mapper.ChaveiroMapper;
import com.example.journey_backend.model.Chaveiro;
import com.example.journey_backend.model.Colecao;
import com.example.journey_backend.model.Usuario; // Importado
import com.example.journey_backend.repository.ChaveiroRepository;
import com.example.journey_backend.repository.ColecaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects; // Importado
import java.util.stream.Collectors;

@Service
public class ChaveiroService {

    @Autowired
    private ChaveiroRepository chaveiroRepository;

    @Autowired
    private ColecaoRepository colecaoRepository;

    @Autowired
    private HistoricoAlteracaoService historicoService; // Injetado

    // Listar todos os chaveiros
    public List<ChaveiroDTO> listarChaveiros() {
        return chaveiroRepository.findAll().stream()
                .map(ChaveiroMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar chaveiro por ID
    public ChaveiroDTO buscarPorId(int id) {
        Chaveiro chaveiro = chaveiroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Chaveiro não encontrado com ID: " + id));
        return ChaveiroMapper.toDTO(chaveiro);
    }

    // Criar novo chaveiro
    public ChaveiroDTO criarChaveiro(ChaveiroDTO dto, Usuario autor) {
        Colecao colecao = colecaoRepository.findById(dto.getColecaoId())
                .orElseThrow(() -> new EntityNotFoundException("Coleção não encontrada com ID: " + dto.getColecaoId()));

        Chaveiro chaveiro = ChaveiroMapper.toModel(dto, colecao);
        Chaveiro salvo = chaveiroRepository.save(chaveiro);

        // --- Log de Criação ---
        registrarLog(autor.getUsuarioId(), "Chaveiro", salvo.getChaveiroId(), "chaveiroModelo", null, salvo.getChaveiroModelo());
        registrarLog(autor.getUsuarioId(), "Chaveiro", salvo.getChaveiroId(), "colecaoId", null, String.valueOf(salvo.getColecao().getColecaoId()));
        // --- Fim do Log ---

        return ChaveiroMapper.toDTO(salvo);
    }

    // Editar chaveiro existente
    public ChaveiroDTO editarChaveiro(int id, ChaveiroDTO dto, Usuario autor) {
        Chaveiro existente = chaveiroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Chaveiro não encontrado com ID: " + id));

        Colecao colecao = colecaoRepository.findById(dto.getColecaoId())
                .orElseThrow(() -> new EntityNotFoundException("Coleção não encontrada com ID: " + dto.getColecaoId()));

        // Guarda valores antigos
        String modeloAntigo = existente.getChaveiroModelo();
        String colecaoAntiga = String.valueOf(existente.getColecao().getColecaoId());

        // Atualiza
        existente.setChaveiroModelo(dto.getChaveiroModelo());
        existente.setColecao(colecao);
        Chaveiro atualizado = chaveiroRepository.save(existente);

        // --- Log de Edição ---
        registrarLog(autor.getUsuarioId(), "Chaveiro", id, "chaveiroModelo", modeloAntigo, atualizado.getChaveiroModelo());
        registrarLog(autor.getUsuarioId(), "Chaveiro", id, "colecaoId", colecaoAntiga, String.valueOf(atualizado.getColecao().getColecaoId()));
        // --- Fim do Log ---

        return ChaveiroMapper.toDTO(atualizado);
    }

    // Deletar chaveiro por ID
    public void deletarChaveiro(int id, Usuario autor) {
        Chaveiro chaveiro = chaveiroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Chaveiro não encontrado com ID: " + id));

        // --- Log de Deleção ---
        registrarLog(autor.getUsuarioId(), "Chaveiro", id, "chaveiroModelo", chaveiro.getChaveiroModelo(), null);
        registrarLog(autor.getUsuarioId(), "Chaveiro", id, "colecaoId", String.valueOf(chaveiro.getColecao().getColecaoId()), null);
        // --- Fim do Log ---

        chaveiroRepository.deleteById(id);
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