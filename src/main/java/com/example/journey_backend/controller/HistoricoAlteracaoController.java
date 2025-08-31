package com.example.journey_backend.controller;

import com.example.journey_backend.dto.HistoricoAlteracaoDTO;
import com.example.journey_backend.service.HistoricoAlteracaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historico")
@CrossOrigin(origins = "*") // mantém consistente com UsuarioController
public class HistoricoAlteracaoController {

    @Autowired
    private HistoricoAlteracaoService historicoService;

    /**
     * Retorna todos os registros de alterações do sistema
     */
    @GetMapping
    public List<HistoricoAlteracaoDTO> getAll() {
        return historicoService.listarAlteracoes();
    }

    /**
     * Retorna um registro de alteração específico pelo ID
     */
    @GetMapping("/{id}")
    public HistoricoAlteracaoDTO getById(@PathVariable Long id) {
        return historicoService.buscarPorId(id);
    }
}
