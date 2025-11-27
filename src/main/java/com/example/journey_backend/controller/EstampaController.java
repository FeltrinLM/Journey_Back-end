package com.example.journey_backend.controller;

// 1. Imports do Projeto
import com.example.journey_backend.dto.EstampaDTO;
import com.example.journey_backend.model.Usuario;
import com.example.journey_backend.repository.UsuarioRepository;
import com.example.journey_backend.service.EstampaService;

// 2. Imports do Swagger/OpenAPI
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

// 3. Imports do Jakarta
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

// 4. Imports do Spring Framework
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
// Imports explícitos das anotações Web
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 5. Imports do Java Padrão
import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/estampas")
@CrossOrigin(origins = "*")
@Tag(name = "Estampas", description = "Endpoints para gerenciamento de Estampas de produtos")
public class EstampaController {

    @Autowired
    private EstampaService estampaService;

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
    @Operation(summary = "Lista todas as estampas cadastradas")
    public ResponseEntity<List<EstampaDTO>> listarTodas() {
        List<EstampaDTO> estampas = estampaService.listarEstampas();
        return ResponseEntity.ok(estampas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma estampa pelo ID")
    @ApiResponse(responseCode = "404", description = "Estampa não encontrada")
    public ResponseEntity<EstampaDTO> buscarPorId(@PathVariable int id) {
        EstampaDTO dto = estampaService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    // --- Métodos de Modificação (ATUALIZADOS) ---

    @PostMapping
    @Operation(summary = "Cria uma nova estampa")
    @ApiResponse(responseCode = "201", description = "Estampa criada com sucesso")
    public ResponseEntity<EstampaDTO> criar(@Valid @RequestBody EstampaDTO dto, Principal principal) {
        EstampaDTO criada = estampaService.criarEstampa(dto, getAutor(principal));
        return ResponseEntity.created(URI.create("/api/estampas/" + criada.getEstampaId()))
                .body(criada);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados de uma estampa existente")
    @ApiResponse(responseCode = "404", description = "Estampa não encontrada para edição")
    public ResponseEntity<EstampaDTO> editar(@PathVariable int id, @Valid @RequestBody EstampaDTO dto, Principal principal) {
        EstampaDTO atualizada = estampaService.editarEstampa(id, dto, getAutor(principal));
        return ResponseEntity.ok(atualizada);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma estampa pelo ID")
    @ApiResponse(responseCode = "204", description = "Estampa deletada com sucesso")
    @ApiResponse(responseCode = "404", description = "Estampa não encontrada para exclusão")
    public ResponseEntity<Void> deletar(@PathVariable int id, Principal principal) {
        estampaService.deletarEstampa(id, getAutor(principal));
        return ResponseEntity.noContent().build();
    }
}