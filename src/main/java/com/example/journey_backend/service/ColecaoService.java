package com.example.journey_backend.service;

import com.example.journey_backend.dto.ColecaoDTO;
import com.example.journey_backend.mapper.ColecaoMapper;
import com.example.journey_backend.model.Colecao;
import com.example.journey_backend.repository.ColecaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ColecaoService {

    @Autowired
    private ColecaoRepository colecaoRepository;

    // Listar todas as coleções
    public List<ColecaoDTO> listarColecoes() {
        return colecaoRepository.findAll().stream()
                .map(ColecaoMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar coleção por ID
    public ColecaoDTO buscarPorId(int id) {
        Optional<Colecao> colecao = colecaoRepository.findById(id);
        return colecao.map(ColecaoMapper::toDTO).orElse(null);
    }

    // Criar nova coleção
    public ColecaoDTO criarColecao(ColecaoDTO dto) {
        Colecao novaColecao = ColecaoMapper.toModel(dto);
        Colecao salva = colecaoRepository.save(novaColecao);
        return ColecaoMapper.toDTO(salva);
    }

    // Editar coleção existente
    public ColecaoDTO editarColecao(int id, ColecaoDTO dto) {
        Colecao existente = colecaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coleção não encontrada"));

        existente.setNome(dto.getNome());
        existente.setDataInicio(dto.getDataInicio());
        existente.setDataFim(dto.getDataFim());

        Colecao atualizada = colecaoRepository.save(existente);
        return ColecaoMapper.toDTO(atualizada);
    }

    // Deletar coleção por ID
    public void deletarColecao(int id) {
        colecaoRepository.deleteById(id);
    }
}
