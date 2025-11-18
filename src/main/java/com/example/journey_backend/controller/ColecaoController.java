package com.example.journey_backend.controller;

import com.example.journey_backend.dto.ColecaoDTO;
import com.example.journey_backend.model.Usuario; // Importado
import com.example.journey_backend.repository.UsuarioRepository; // Importado
import com.example.journey_backend.service.ColecaoService;

// Importações do Swagger/OpenAPI
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.persistence.EntityNotFoundException; // Importado
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // Importado
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.security.Principal; // Importado
import java.util.List;

@RestController
@RequestMapping("/api/colecoes")
@CrossOrigin(origins = "*")
@Tag(name = "Coleções", description = "Endpoints para gerenciamento de Coleções de itens")
public class ColecaoController {

    @Autowired
    private ColecaoService colecaoService;

    @Autowired
    private UsuarioRepository usuarioRepository; // Injetado

    /**
     * Helper para buscar o usuário (autor) logado
     */
    private Usuario getAutor(Principal principal) {
        if (principal == null) {
            throw new SecurityException("Usuário não autenticado.");
        }
        String nomeUsuario = principal.getName();
        return usuarioRepository.findByNome(nomeUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuário (autor) não encontrado: " + nomeUsuario));
    }

    // --- Métodos GET (Não mudam) ---
    @GetMapping
    @Operation(summary = "Lista todas as coleções cadastradas")
    public ResponseEntity<List<ColecaoDTO>> listarTodas() {
        List<ColecaoDTO> colecoes = colecaoService.listarColecoes();
        return ResponseEntity.ok(colecoes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma coleção pelo ID")
    @ApiResponse(responseCode = "404", description = "Coleção não encontrada")
    public ResponseEntity<ColecaoDTO> buscarPorId(@PathVariable int id) {
        ColecaoDTO dto = colecaoService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    // --- Métodos de Modificação (ATUALIZADOS) ---

    @PostMapping
    @Operation(summary = "Cria uma nova coleção")
    @ApiResponse(responseCode = "201", description = "Coleção criada com sucesso")
    public ResponseEntity<ColecaoDTO> criar(@Valid @RequestBody ColecaoDTO dto, Principal principal) {
        ColecaoDTO criado = colecaoService.criarColecao(dto, getAutor(principal));
        return ResponseEntity.created(URI.create("/api/colecoes/" + criado.getColecaoId()))
                .body(criado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados de uma coleção existente")
    @ApiResponse(responseCode = "404", description = "Coleção não encontrada para edição")
    public ResponseEntity<ColecaoDTO> editar(@PathVariable int id, @Valid @RequestBody ColecaoDTO dto, Principal principal) {
        ColecaoDTO atualizado = colecaoService.editarColecao(id, dto, getAutor(principal));
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma coleção pelo ID")
    @ApiResponse(responseCode = "204", description = "Coleção deletada com sucesso")
    @ApiResponse(responseCode = "404", description = "Coleção não encontrada para exclusão")
    public ResponseEntity<Void> deletar(@PathVariable int id, Principal principal) {
        colecaoService.deletarColecao(id, getAutor(principal));
        return ResponseEntity.noContent().build();
    }
}