package com.example.journey_backend.controller;

import com.example.journey_backend.dto.HistoricoAlteracaoDTO;
import com.example.journey_backend.service.HistoricoAlteracaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<HistoricoAlteracaoDTO>> getAll() {
        List<HistoricoAlteracaoDTO> lista = historicoService.listarAlteracoes();
        return ResponseEntity.ok(lista);
    }

    /**
     * Retorna um registro de alteração específico pelo ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<HistoricoAlteracaoDTO> getById(@PathVariable Long id) {
        HistoricoAlteracaoDTO dto = historicoService.buscarPorId(id);
        return ResponseEntity.ok(dto); // se não existir, service lança 404
    }
}
