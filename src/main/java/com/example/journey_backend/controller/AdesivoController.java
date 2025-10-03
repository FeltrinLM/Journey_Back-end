package com.example.journey_backend.controller;

import com.example.journey_backend.dto.AdesivoDTO;
import com.example.journey_backend.service.AdesivoService;

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
@RequestMapping("/api/adesivos")
@CrossOrigin(origins = "*")
@Tag(name = "Adesivos", description = "Endpoints para gerenciamento de Adesivos") // Adicione um TAG para agrupar
public class AdesivoController {

    @Autowired
    private AdesivoService adesivoService;

    // GET /api/adesivos
    @GetMapping
    @Operation(summary = "Lista todos os adesivos disponíveis") // Título da operação
    public ResponseEntity<List<AdesivoDTO>> listarTodos() {
        List<AdesivoDTO> lista = adesivoService.listarAdesivos();
        return ResponseEntity.ok(lista);
    }

    // GET /api/adesivos/{id}
    @GetMapping("/{id}")
    @Operation(summary = "Busca um adesivo pelo ID")
    @ApiResponse(responseCode = "404", description = "Adesivo não encontrado") // Documenta o erro
    public ResponseEntity<AdesivoDTO> buscarPorId(@PathVariable int id) {
        AdesivoDTO dto = adesivoService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    // POST /api/adesivos
    @PostMapping
    @Operation(summary = "Cria um novo adesivo")
    @ApiResponse(responseCode = "201", description = "Adesivo criado com sucesso")
    public ResponseEntity<AdesivoDTO> criar(@Valid @RequestBody AdesivoDTO dto) {
        AdesivoDTO criado = adesivoService.criarAdesivo(dto);
        return ResponseEntity.created(URI.create("/api/adesivos/" + criado.getAdesivoId()))
                .body(criado);
    }

    // PUT /api/adesivos/{id}
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados de um adesivo existente")
    @ApiResponse(responseCode = "404", description = "Adesivo não encontrado para edição")
    public ResponseEntity<AdesivoDTO> editar(@PathVariable int id, @Valid @RequestBody AdesivoDTO dto) {
        AdesivoDTO atualizado = adesivoService.editarAdesivo(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    // DELETE /api/adesivos/{id}
    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um adesivo pelo ID")
    @ApiResponse(responseCode = "204", description = "Adesivo deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Adesivo não encontrado para exclusão")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        adesivoService.deletarAdesivo(id);
        return ResponseEntity.noContent().build();
    }
}