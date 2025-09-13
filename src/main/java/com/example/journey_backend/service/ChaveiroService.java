package com.example.journey_backend.service;

import com.example.journey_backend.dto.ChaveiroDTO;
import com.example.journey_backend.mapper.ChaveiroMapper;
import com.example.journey_backend.model.Chaveiro;
import com.example.journey_backend.model.Colecao;
import com.example.journey_backend.repository.ChaveiroRepository;
import com.example.journey_backend.repository.ColecaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChaveiroService {

    @Autowired
    private ChaveiroRepository chaveiroRepository;

    @Autowired
    private ColecaoRepository colecaoRepository;

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
    public ChaveiroDTO criarChaveiro(ChaveiroDTO dto) {
        Colecao colecao = colecaoRepository.findById(dto.getColecaoId())
                .orElseThrow(() -> new EntityNotFoundException("Coleção não encontrada com ID: " + dto.getColecaoId()));

        Chaveiro chaveiro = ChaveiroMapper.toModel(dto, colecao);
        Chaveiro salvo = chaveiroRepository.save(chaveiro);
        return ChaveiroMapper.toDTO(salvo);
    }

    // Editar chaveiro existente
    public ChaveiroDTO editarChaveiro(int id, ChaveiroDTO dto) {
        Chaveiro existente = chaveiroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Chaveiro não encontrado com ID: " + id));

        Colecao colecao = colecaoRepository.findById(dto.getColecaoId())
                .orElseThrow(() -> new EntityNotFoundException("Coleção não encontrada com ID: " + dto.getColecaoId()));

        existente.setChaveiroModelo(dto.getChaveiroModelo());
        existente.setColecao(colecao);

        Chaveiro atualizado = chaveiroRepository.save(existente);
        return ChaveiroMapper.toDTO(atualizado);
    }

    // Deletar chaveiro por ID
    public void deletarChaveiro(int id) {
        if (!chaveiroRepository.existsById(id)) {
            throw new EntityNotFoundException("Chaveiro não encontrado com ID: " + id);
        }
        chaveiroRepository.deleteById(id);
    }
}
