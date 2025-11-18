package com.example.journey_backend.controller;

import com.example.journey_backend.dto.ChaveiroDTO;
import com.example.journey_backend.model.Usuario; // Importado
import com.example.journey_backend.repository.UsuarioRepository; // Importado
import com.example.journey_backend.service.ChaveiroService;

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
@RequestMapping("/api/chaveiros")
@CrossOrigin(origins = "*")
@Tag(name = "Chaveiros", description = "Endpoints para gerenciamento de Chaveiros")
public class ChaveiroController {

    @Autowired
    private ChaveiroService chaveiroService;

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
    @Operation(summary = "Lista todos os chaveiros disponíveis")
    public ResponseEntity<List<ChaveiroDTO>> listarTodos() {
        List<ChaveiroDTO> chaveiros = chaveiroService.listarChaveiros();
        return ResponseEntity.ok(chaveiros);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um chaveiro pelo ID")
    @ApiResponse(responseCode = "404", description = "Chaveiro não encontrado")
    public ResponseEntity<ChaveiroDTO> buscarPorId(@PathVariable int id) {
        ChaveiroDTO dto = chaveiroService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    // --- Métodos de Modificação (ATUALIZADOS) ---

    @PostMapping
    @Operation(summary = "Cria um novo chaveiro")
    @ApiResponse(responseCode = "201", description = "Chaveiro criado com sucesso")
    public ResponseEntity<ChaveiroDTO> criar(@Valid @RequestBody ChaveiroDTO dto, Principal principal) {
        ChaveiroDTO criado = chaveiroService.criarChaveiro(dto, getAutor(principal));
        return ResponseEntity.created(URI.create("/api/chaveiros/" + criado.getChaveiroId()))
                .body(criado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados de um chaveiro existente")
    @ApiResponse(responseCode = "404", description = "Chaveiro não encontrado para edição")
    public ResponseEntity<ChaveiroDTO> editar(@PathVariable int id, @Valid @RequestBody ChaveiroDTO dto, Principal principal) {
        ChaveiroDTO atualizado = chaveiroService.editarChaveiro(id, dto, getAutor(principal));
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um chaveiro pelo ID")
    @ApiResponse(responseCode = "204", description = "Chaveiro deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Chaveiro não encontrado para exclusão")
    public ResponseEntity<Void> deletar(@PathVariable int id, Principal principal) {
        chaveiroService.deletarChaveiro(id, getAutor(principal));
        return ResponseEntity.noContent().build();
    }
}