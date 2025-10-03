package com.example.journey_backend.controller;

import com.example.journey_backend.dto.ColecaoDTO;
import com.example.journey_backend.service.ColecaoService;

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
@RequestMapping("/api/colecoes")
@CrossOrigin(origins = "*") // Permite acesso do frontend Angular
@Tag(name = "Coleções", description = "Endpoints para gerenciamento de Coleções de itens") // Define o grupo
public class ColecaoController {

    @Autowired
    private ColecaoService colecaoService;

    // GET /api/colecoes
    @GetMapping
    @Operation(summary = "Lista todas as coleções cadastradas")
    public ResponseEntity<List<ColecaoDTO>> listarTodas() {
        List<ColecaoDTO> colecoes = colecaoService.listarColecoes();
        return ResponseEntity.ok(colecoes);
    }

    // GET /api/colecoes/{id}
    @GetMapping("/{id}")
    @Operation(summary = "Busca uma coleção pelo ID")
    @ApiResponse(responseCode = "404", description = "Coleção não encontrada")
    public ResponseEntity<ColecaoDTO> buscarPorId(@PathVariable int id) {
        ColecaoDTO dto = colecaoService.buscarPorId(id);
        return ResponseEntity.ok(dto); // se não existir, service lança 404
    }

    // POST /api/colecoes
    @PostMapping
    @Operation(summary = "Cria uma nova coleção")
    @ApiResponse(responseCode = "201", description = "Coleção criada com sucesso")
    public ResponseEntity<ColecaoDTO> criar(@Valid @RequestBody ColecaoDTO dto) {
        ColecaoDTO criado = colecaoService.criarColecao(dto);
        return ResponseEntity.created(URI.create("/api/colecoes/" + criado.getColecaoId()))
                .body(criado);
    }

    // PUT /api/colecoes/{id}
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados de uma coleção existente")
    @ApiResponse(responseCode = "404", description = "Coleção não encontrada para edição")
    public ResponseEntity<ColecaoDTO> editar(@PathVariable int id, @Valid @RequestBody ColecaoDTO dto) {
        ColecaoDTO atualizado = colecaoService.editarColecao(id, dto);
        return ResponseEntity.ok(atualizado); // se não existir, service lança 404
    }

    // DELETE /api/colecoes/{id}
    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma coleção pelo ID")
    @ApiResponse(responseCode = "204", description = "Coleção deletada com sucesso")
    @ApiResponse(responseCode = "404", description = "Coleção não encontrada para exclusão")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        colecaoService.deletarColecao(id);
        return ResponseEntity.noContent().build(); // 204
    }
}