package com.example.journey_backend.controller;

// 1. Imports do Projeto
import com.example.journey_backend.dto.HistoricoAlteracaoDTO;
import com.example.journey_backend.service.HistoricoAlteracaoService;

// 2. Imports do Swagger/OpenAPI
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

// 3. Imports do Spring Framework
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
// Imports explícitos das anotações Web
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 4. Imports do Java Padrão
import java.util.List;

@RestController
@RequestMapping("/api/historico")
@Tag(name = "Histórico de Alterações", description = "Endpoints de leitura para o registro de alterações do sistema")
public class HistoricoAlteracaoController {

    @Autowired
    private HistoricoAlteracaoService historicoService;

    /**
     * Retorna todos os registros de alterações do sistema
     */
    @GetMapping
    @Operation(summary = "Lista todos os registros de alterações do sistema")
    public ResponseEntity<List<HistoricoAlteracaoDTO>> getAll() {
        List<HistoricoAlteracaoDTO> lista = historicoService.listarAlteracoes();
        return ResponseEntity.ok(lista);
    }

    /**
     * Retorna um registro de alteração específico pelo ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Busca um registro de alteração pelo ID")
    @ApiResponse(responseCode = "404", description = "Registro de histórico não encontrado")
    public ResponseEntity<HistoricoAlteracaoDTO> getById(@PathVariable Long id) {
        HistoricoAlteracaoDTO dto = historicoService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }
}