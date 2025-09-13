package com.example.journey_backend.service;

import com.example.journey_backend.dto.AdesivoDTO;
import com.example.journey_backend.mapper.AdesivoMapper;
import com.example.journey_backend.model.Adesivo;
import com.example.journey_backend.repository.AdesivoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdesivoService {

    @Autowired
    private AdesivoRepository adesivoRepository;

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
    public AdesivoDTO criarAdesivo(AdesivoDTO dto) {
        Adesivo novoAdesivo = AdesivoMapper.toModel(dto);
        Adesivo salvo = adesivoRepository.save(novoAdesivo);
        return AdesivoMapper.toDTO(salvo);
    }

    // Editar adesivo existente
    public AdesivoDTO editarAdesivo(int id, AdesivoDTO dto) {
        Adesivo adesivoExistente = adesivoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Adesivo não encontrado com ID: " + id));

        adesivoExistente.setAdesivoModelo(dto.getAdesivoModelo());
        adesivoExistente.setCromatico(dto.isCromatico());

        Adesivo atualizado = adesivoRepository.save(adesivoExistente);
        return AdesivoMapper.toDTO(atualizado);
    }

    // Deletar adesivo por ID
    public void deletarAdesivo(int id) {
        if (!adesivoRepository.existsById(id)) {
            throw new EntityNotFoundException("Adesivo não encontrado com ID: " + id);
        }
        adesivoRepository.deleteById(id);
    }
}
