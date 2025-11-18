package com.example.journey_backend.controller;

import com.example.journey_backend.dto.PecaDTO;
import com.example.journey_backend.model.Usuario; // Importado
import com.example.journey_backend.repository.UsuarioRepository; // Importado
import com.example.journey_backend.service.PecaService;

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
@RequestMapping("/api/pecas")
@CrossOrigin(origins = "*")
@Tag(name = "Peças", description = "Endpoints para gerenciamento de Peças de produtos (Ex: Camiseta, Caneca)")
public class PecaController {

    @Autowired
    private PecaService pecaService;

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
    @Operation(summary = "Lista todas as peças (tipos de produto) cadastradas")
    public ResponseEntity<List<PecaDTO>> listarTodas() {
        List<PecaDTO> pecas = pecaService.listarPecas();
        return ResponseEntity.ok(pecas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma peça pelo ID")
    @ApiResponse(responseCode = "404", description = "Peça não encontrada")
    public ResponseEntity<PecaDTO> buscarPorId(@PathVariable int id) {
        PecaDTO peca = pecaService.buscarPorId(id);
        return ResponseEntity.ok(peca);
    }

    // --- Métodos de Modificação (ATUALIZADOS) ---

    @PostMapping
    @Operation(summary = "Cria uma nova peça")
    @ApiResponse(responseCode = "201", description = "Peça criada com sucesso")
    public ResponseEntity<PecaDTO> criar(@Valid @RequestBody PecaDTO dto, Principal principal) {
        PecaDTO criada = pecaService.criarPeca(dto, getAutor(principal));
        return ResponseEntity.created(URI.create("/api/pecas/" + criada.getPecaId()))
                .body(criada);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados de uma peça existente")
    @ApiResponse(responseCode = "404", description = "Peça não encontrada para edição")
    public ResponseEntity<PecaDTO> editar(@PathVariable int id, @Valid @RequestBody PecaDTO dto, Principal principal) {
        PecaDTO atualizada = pecaService.editarPeca(id, dto, getAutor(principal));
        return ResponseEntity.ok(atualizada);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma peça pelo ID")
    @ApiResponse(responseCode = "204", description = "Peça deletada com sucesso")
    @ApiResponse(responseCode = "404", description = "Peça não encontrada para exclusão")
    public ResponseEntity<Void> deletar(@PathVariable int id, Principal principal) {
        pecaService.deletarPeca(id, getAutor(principal));
        return ResponseEntity.noContent().build();
    }
}