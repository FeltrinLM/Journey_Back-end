package com.example.journey_backend.controller;

import com.example.journey_backend.dto.PecaDTO;
import com.example.journey_backend.service.PecaService;

// Importações do Swagger/OpenAPI
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/pecas")
@CrossOrigin(origins = "*") // permite requisições do Angular (ou qualquer frontend)
@Tag(name = "Peças", description = "Endpoints para gerenciamento de Peças de produtos (Ex: Camiseta, Caneca)") // Define o grupo
public class PecaController {

    @Autowired
    private PecaService pecaService;

    // GET /api/pecas
    @GetMapping
    @Operation(summary = "Lista todas as peças (tipos de produto) cadastradas")
    public ResponseEntity<List<PecaDTO>> listarTodas() {
        List<PecaDTO> pecas = pecaService.listarPecas();
        return ResponseEntity.ok(pecas);
    }

    // GET /api/pecas/{id}
    @GetMapping("/{id}")
    @Operation(summary = "Busca uma peça pelo ID")
    @ApiResponse(responseCode = "404", description = "Peça não encontrada")
    public ResponseEntity<PecaDTO> buscarPorId(@PathVariable int id) {
        PecaDTO peca = pecaService.buscarPorId(id);
        return ResponseEntity.ok(peca); // se não existir, service lança 404
    }

    // POST /api/pecas
    @PostMapping
    @Operation(summary = "Cria uma nova peça")
    @ApiResponse(responseCode = "201", description = "Peça criada com sucesso")
    public ResponseEntity<PecaDTO> criar(@Valid @RequestBody PecaDTO dto) {
        PecaDTO criada = pecaService.criarPeca(dto);
        return ResponseEntity.created(URI.create("/api/pecas/" + criada.getPecaId()))
                .body(criada);
    }

    // PUT /api/pecas/{id}
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados de uma peça existente")
    @ApiResponse(responseCode = "404", description = "Peça não encontrada para edição")
    public ResponseEntity<PecaDTO> editar(@PathVariable int id, @Valid @RequestBody PecaDTO dto) {
        PecaDTO atualizada = pecaService.editarPeca(id, dto);
        return ResponseEntity.ok(atualizada); // se não existir, service lança 404
    }

    // DELETE /api/pecas/{id}
    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma peça pelo ID")
    @ApiResponse(responseCode = "204", description = "Peça deletada com sucesso")
    @ApiResponse(responseCode = "404", description = "Peça não encontrada para exclusão")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        pecaService.deletarPeca(id);
        return ResponseEntity.noContent().build(); // 204
    }
}