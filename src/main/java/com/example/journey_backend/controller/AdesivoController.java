package com.example.journey_backend.controller;

// 1. Imports do Projeto (Models, DTOs, Repos, Services)
import com.example.journey_backend.dto.AdesivoDTO;
import com.example.journey_backend.model.Usuario;
import com.example.journey_backend.repository.UsuarioRepository;
import com.example.journey_backend.service.AdesivoService;

// 2. Imports do Swagger/OpenAPI
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

// 3. Imports do Jakarta (Validação e Persistência)
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

// 4. Imports do Spring Framework
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
// Aqui substituímos o ".*" pelas classes específicas usadas:
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
@RequestMapping("/api/adesivos")
@CrossOrigin(origins = "*")
@Tag(name = "Adesivos", description = "Endpoints para gerenciamento de Adesivos")
public class AdesivoController {

    @Autowired
    private AdesivoService adesivoService;

    // --- 1. Injetar o repositório de usuário para buscar o autor ---
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Helper para buscar o usuário (autor) logado
     */
    private Usuario getAutor(Principal principal) {
        if (principal == null) {
            // Isso não deve acontecer se o Spring Security estiver configurado
            throw new SecurityException("Usuário não autenticado.");
        }
        String nomeUsuario = principal.getName();
        return usuarioRepository.findByNome(nomeUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuário (autor) não encontrado: " + nomeUsuario));
    }

    // --- Métodos GET (Não mudam) ---
    @GetMapping
    @Operation(summary = "Lista todos os adesivos disponíveis")
    public ResponseEntity<List<AdesivoDTO>> listarTodos() {
        List<AdesivoDTO> lista = adesivoService.listarAdesivos();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um adesivo pelo ID")
    @ApiResponse(responseCode = "404", description = "Adesivo não encontrado")
    public ResponseEntity<AdesivoDTO> buscarPorId(@PathVariable int id) {
        AdesivoDTO dto = adesivoService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    // --- Métodos de Modificação (ATUALIZADOS) ---

    // POST /api/adesivos
    @PostMapping
    @Operation(summary = "Cria um novo adesivo")
    @ApiResponse(responseCode = "201", description = "Adesivo criado com sucesso")
    public ResponseEntity<AdesivoDTO> criar(@Valid @RequestBody AdesivoDTO dto, Principal principal) {
        // 2. Buscar o autor e passá-lo para o serviço
        AdesivoDTO criado = adesivoService.criarAdesivo(dto, getAutor(principal));
        return ResponseEntity.created(URI.create("/api/adesivos/" + criado.getAdesivoId()))
                .body(criado);
    }

    // PUT /api/adesivos/{id}
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados de um adesivo existente")
    @ApiResponse(responseCode = "404", description = "Adesivo não encontrado para edição")
    public ResponseEntity<AdesivoDTO> editar(@PathVariable int id, @Valid @RequestBody AdesivoDTO dto, Principal principal) {
        // 2. Buscar o autor e passá-lo para o serviço
        AdesivoDTO atualizado = adesivoService.editarAdesivo(id, dto, getAutor(principal));
        return ResponseEntity.ok(atualizado);
    }

    // DELETE /api/adesivos/{id}
    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um adesivo pelo ID")
    @ApiResponse(responseCode = "204", description = "Adesivo deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Adesivo não encontrado para exclusão")
    public ResponseEntity<Void> deletar(@PathVariable int id, Principal principal) {
        // 2. Buscar o autor e passá-lo para o serviço
        adesivoService.deletarAdesivo(id, getAutor(principal));
        return ResponseEntity.noContent().build();
    }
}