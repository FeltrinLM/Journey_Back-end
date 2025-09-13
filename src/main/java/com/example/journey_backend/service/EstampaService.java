package com.example.journey_backend.service;

import com.example.journey_backend.dto.EstampaDTO;
import com.example.journey_backend.mapper.EstampaMapper;
import com.example.journey_backend.model.Colecao;
import com.example.journey_backend.model.Estampa;
import com.example.journey_backend.repository.ColecaoRepository;
import com.example.journey_backend.repository.EstampaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstampaService {

    @Autowired
    private EstampaRepository estampaRepository;

    @Autowired
    private ColecaoRepository colecaoRepository;

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
    public EstampaDTO criarEstampa(EstampaDTO dto) {
        Colecao colecao = colecaoRepository.findById(dto.getColecaoId())
                .orElseThrow(() -> new EntityNotFoundException("Coleção não encontrada com ID: " + dto.getColecaoId()));

        Estampa nova = EstampaMapper.toModel(dto, colecao);
        Estampa salva = estampaRepository.save(nova);
        return EstampaMapper.toDTO(salva);
    }

    // Editar estampa existente
    public EstampaDTO editarEstampa(int id, EstampaDTO dto) {
        Estampa existente = estampaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estampa não encontrada com ID: " + id));

        Colecao colecao = colecaoRepository.findById(dto.getColecaoId())
                .orElseThrow(() -> new EntityNotFoundException("Coleção não encontrada com ID: " + dto.getColecaoId()));

        existente.setNome(dto.getNome());
        existente.setQuantidade(dto.getQuantidade());
        existente.setColecao(colecao);

        Estampa atualizada = estampaRepository.save(existente);
        return EstampaMapper.toDTO(atualizada);
    }

    // Deletar estampa por ID
    public void deletarEstampa(int id) {
        if (!estampaRepository.existsById(id)) {
            throw new EntityNotFoundException("Estampa não encontrada com ID: " + id);
        }
        estampaRepository.deleteById(id);
    }
}
