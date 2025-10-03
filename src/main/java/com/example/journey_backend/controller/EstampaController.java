package com.example.journey_backend.controller;

import com.example.journey_backend.dto.EstampaDTO;
import com.example.journey_backend.service.EstampaService;

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
@RequestMapping("/api/estampas")
@CrossOrigin(origins = "*") // Libera acesso do frontend (ex: Angular)
@Tag(name = "Estampas", description = "Endpoints para gerenciamento de Estampas de produtos") // Define o grupo
public class EstampaController {

    @Autowired
    private EstampaService estampaService;

    // GET /api/estampas
    @GetMapping
    @Operation(summary = "Lista todas as estampas cadastradas")
    public ResponseEntity<List<EstampaDTO>> listarTodas() {
        List<EstampaDTO> estampas = estampaService.listarEstampas();
        return ResponseEntity.ok(estampas);
    }

    // GET /api/estampas/{id}
    @GetMapping("/{id}")
    @Operation(summary = "Busca uma estampa pelo ID")
    @ApiResponse(responseCode = "404", description = "Estampa não encontrada")
    public ResponseEntity<EstampaDTO> buscarPorId(@PathVariable int id) {
        EstampaDTO dto = estampaService.buscarPorId(id);
        return ResponseEntity.ok(dto); // se não existir, service lança 404
    }

    // POST /api/estampas
    @PostMapping
    @Operation(summary = "Cria uma nova estampa")
    @ApiResponse(responseCode = "201", description = "Estampa criada com sucesso")
    public ResponseEntity<EstampaDTO> criar(@Valid @RequestBody EstampaDTO dto) {
        EstampaDTO criada = estampaService.criarEstampa(dto);
        return ResponseEntity.created(URI.create("/api/estampas/" + criada.getEstampaId()))
                .body(criada);
    }

    // PUT /api/estampas/{id}
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados de uma estampa existente")
    @ApiResponse(responseCode = "404", description = "Estampa não encontrada para edição")
    public ResponseEntity<EstampaDTO> editar(@PathVariable int id, @Valid @RequestBody EstampaDTO dto) {
        EstampaDTO atualizada = estampaService.editarEstampa(id, dto);
        return ResponseEntity.ok(atualizada); // se não existir, service lança 404
    }

    // DELETE /api/estampas/{id}
    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma estampa pelo ID")
    @ApiResponse(responseCode = "204", description = "Estampa deletada com sucesso")
    @ApiResponse(responseCode = "404", description = "Estampa não encontrada para exclusão")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        estampaService.deletarEstampa(id);
        return ResponseEntity.noContent().build(); // 204
    }
}