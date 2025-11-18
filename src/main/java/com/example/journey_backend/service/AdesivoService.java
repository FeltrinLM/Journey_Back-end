package com.example.journey_backend.service;

import com.example.journey_backend.dto.AdesivoDTO;
import com.example.journey_backend.dto.HistoricoAlteracaoDTO; // Importado
import com.example.journey_backend.mapper.AdesivoMapper;
import com.example.journey_backend.model.Adesivo;
import com.example.journey_backend.model.Usuario; // Importado
import com.example.journey_backend.repository.AdesivoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects; // Importado
import java.util.stream.Collectors;

@Service
public class AdesivoService {

    @Autowired
    private AdesivoRepository adesivoRepository;

    @Autowired
    private HistoricoAlteracaoService historicoService; // Injetado

    // Listar todos os adesivos
    public List<AdesivoDTO> listarAdesivos() {
        return adesivoRepository.findAll().stream()
                .map(AdesivoMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar adesivo por ID
    public AdesivoDTO buscarPorId(int id) {
        Adesivo adesivo = adesivoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Adesivo não encontrado com ID: " + id));
        return AdesivoMapper.toDTO(adesivo);
    }

    // Criar novo adesivo
    public AdesivoDTO criarAdesivo(AdesivoDTO dto, Usuario autor) {
        Adesivo novoAdesivo = AdesivoMapper.toModel(dto);
        Adesivo salvo = adesivoRepository.save(novoAdesivo);

        // --- Log de Criação ---
        registrarLog(autor.getUsuarioId(), "Adesivo", salvo.getAdesivoId(), "adesivoModelo", null, salvo.getAdesivoModelo());
        registrarLog(autor.getUsuarioId(), "Adesivo", salvo.getAdesivoId(), "cromatico", null, String.valueOf(salvo.isCromatico()));
        // --- Fim do Log ---

        return AdesivoMapper.toDTO(salvo);
    }

    // Editar adesivo existente
    public AdesivoDTO editarAdesivo(int id, AdesivoDTO dto, Usuario autor) {
        Adesivo adesivoExistente = adesivoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Adesivo não encontrado com ID: " + id));

        // Guarda valores antigos
        String modeloAntigo = adesivoExistente.getAdesivoModelo();
        boolean cromaticoAntigo = adesivoExistente.isCromatico();

        // Atualiza
        adesivoExistente.setAdesivoModelo(dto.getAdesivoModelo());
        adesivoExistente.setCromatico(dto.isCromatico());
        Adesivo atualizado = adesivoRepository.save(adesivoExistente);

        // --- Log de Edição ---
        registrarLog(autor.getUsuarioId(), "Adesivo", id, "adesivoModelo", modeloAntigo, atualizado.getAdesivoModelo());
        registrarLog(autor.getUsuarioId(), "Adesivo", id, "cromatico", String.valueOf(cromaticoAntigo), String.valueOf(atualizado.isCromatico()));
        // --- Fim do Log ---

        return AdesivoMapper.toDTO(atualizado);
    }

    // Deletar adesivo por ID
    public void deletarAdesivo(int id, Usuario autor) {
        Adesivo adesivo = adesivoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Adesivo não encontrado com ID: " + id));

        // --- Log de Deleção (Registra antes de deletar) ---
        registrarLog(autor.getUsuarioId(), "Adesivo", id, "adesivoModelo", adesivo.getAdesivoModelo(), null);
        registrarLog(autor.getUsuarioId(), "Adesivo", id, "cromatico", String.valueOf(adesivo.isCromatico()), null);
        // --- Fim do Log ---

        adesivoRepository.deleteById(id);
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