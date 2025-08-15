package com.example.journey_backend.service;

import com.example.journey_backend.dto.PecaDTO;
import com.example.journey_backend.mapper.PecaMapper;
import com.example.journey_backend.model.Peca;
import com.example.journey_backend.repository.PecaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PecaService {

    @Autowired
    private PecaRepository pecaRepository;

    // Listar todas as peças
    public List<PecaDTO> listarPecas() {
        return pecaRepository.findAll().stream()
                .map(PecaMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar peça por ID
    public PecaDTO buscarPorId(int id) {
        Optional<Peca> peca = pecaRepository.findById(id);
        return peca.map(PecaMapper::toDTO).orElse(null);
    }

    // Criar nova peça
    public PecaDTO criarPeca(PecaDTO dto) {
        Peca novaPeca = PecaMapper.toModel(dto);
        Peca salva = pecaRepository.save(novaPeca);
        return PecaMapper.toDTO(salva);
    }

    // Editar peça existente
    public PecaDTO editarPeca(int id, PecaDTO dto) {
        Peca existente = pecaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Peça não encontrada"));

        existente.setTipo(dto.getTipo());
        existente.setTamanho(dto.getTamanho());
        existente.setCor(dto.getCor());
        existente.setQuantidade(dto.getQuantidade());

        Peca atualizada = pecaRepository.save(existente);
        return PecaMapper.toDTO(atualizada);
    }

    // Deletar peça por ID
    public void deletarPeca(int id) {
        pecaRepository.deleteById(id);
    }
}
