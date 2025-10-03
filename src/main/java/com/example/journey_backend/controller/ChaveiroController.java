package com.example.journey_backend.controller;

import com.example.journey_backend.dto.ChaveiroDTO;
import com.example.journey_backend.service.ChaveiroService;

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
@RequestMapping("/api/chaveiros")
@CrossOrigin(origins = "*") // Permite chamadas de qualquer origem (ex: Angular)
@Tag(name = "Chaveiros", description = "Endpoints para gerenciamento de Chaveiros") // Define o grupo na documentação
public class ChaveiroController {

    @Autowired
    private ChaveiroService chaveiroService;

    // GET /api/chaveiros
    @GetMapping
    @Operation(summary = "Lista todos os chaveiros disponíveis") // Descrição do endpoint
    public ResponseEntity<List<ChaveiroDTO>> listarTodos() {
        List<ChaveiroDTO> chaveiros = chaveiroService.listarChaveiros();
        return ResponseEntity.ok(chaveiros);
    }

    // GET /api/chaveiros/{id}
    @GetMapping("/{id}")
    @Operation(summary = "Busca um chaveiro pelo ID")
    @ApiResponse(responseCode = "404", description = "Chaveiro não encontrado")
    public ResponseEntity<ChaveiroDTO> buscarPorId(@PathVariable int id) {
        ChaveiroDTO dto = chaveiroService.buscarPorId(id);
        return ResponseEntity.ok(dto); // se não existir, service lança 404
    }

    // POST /api/chaveiros
    @PostMapping
    @Operation(summary = "Cria um novo chaveiro")
    @ApiResponse(responseCode = "201", description = "Chaveiro criado com sucesso")
    public ResponseEntity<ChaveiroDTO> criar(@Valid @RequestBody ChaveiroDTO dto) {
        ChaveiroDTO criado = chaveiroService.criarChaveiro(dto);
        return ResponseEntity.created(URI.create("/api/chaveiros/" + criado.getChaveiroId()))
                .body(criado);
    }

    // PUT /api/chaveiros/{id}
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados de um chaveiro existente")
    @ApiResponse(responseCode = "404", description = "Chaveiro não encontrado para edição")
    public ResponseEntity<ChaveiroDTO> editar(@PathVariable int id, @Valid @RequestBody ChaveiroDTO dto) {
        ChaveiroDTO atualizado = chaveiroService.editarChaveiro(id, dto);
        return ResponseEntity.ok(atualizado); // se não existir, service lança 404
    }

    // DELETE /api/chaveiros/{id}
    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um chaveiro pelo ID")
    @ApiResponse(responseCode = "204", description = "Chaveiro deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Chaveiro não encontrado para exclusão")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        chaveiroService.deletarChaveiro(id);
        return ResponseEntity.noContent().build(); // 204
    }
}