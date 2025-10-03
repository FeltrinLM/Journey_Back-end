package com.example.journey_backend.controller;

import com.example.journey_backend.dto.UsuarioDTO;
import com.example.journey_backend.service.UsuarioService;

// Importações do Swagger/OpenAPI
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*") // permite requisições de qualquer origem (ajustável no deploy)
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de Usuários e Autenticação") // Define o grupo
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // GET /api/usuarios
    @GetMapping
    @Operation(summary = "Lista todos os usuários cadastrados")
    public ResponseEntity<List<UsuarioDTO>> listarTodos() {
        List<UsuarioDTO> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    // GET /api/usuarios/{id}
    @GetMapping("/{id}")
    @Operation(summary = "Busca um usuário pelo ID")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable int id) {
        UsuarioDTO usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario); // se não existir, service lança 404
    }

    // POST /api/usuarios
    @PostMapping
    @Operation(summary = "Cria um novo usuário (Cadastro)")
    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso")
    public ResponseEntity<UsuarioDTO> criar(@Valid @RequestBody UsuarioDTO dto) {
        UsuarioDTO criado = usuarioService.criarUsuario(dto);
        return ResponseEntity.created(URI.create("/api/usuarios/" + criado.getUsuarioId()))
                .body(criado);
    }

    // PUT /api/usuarios/{id}
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados de um usuário existente")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado para edição")
    public ResponseEntity<UsuarioDTO> editar(@PathVariable int id, @Valid @RequestBody UsuarioDTO dto) {
        UsuarioDTO atualizado = usuarioService.editarUsuario(id, dto);
        return ResponseEntity.ok(atualizado); // se não existir, service lança 404
    }

    // DELETE /api/usuarios/{id}
    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um usuário pelo ID")
    @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado para exclusão")
    @ApiResponse(responseCode = "409", description = "Conflito: Não é possível deletar devido a restrições de histórico (se aplicável)")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build(); // se tiver histórico, service lança IllegalStateException → 409 via ControllerAdvice
    }

    // POST /api/usuarios/login
    @PostMapping("/login")
    @Operation(summary = "Realiza a autenticação do usuário")
    @ApiResponse(responseCode = "200", description = "Login bem-sucedido")
    @ApiResponse(responseCode = "401", description = "Nome de usuário ou senha inválidos")
    public ResponseEntity<Void> login(
            @Parameter(description = "Nome de usuário para login") @RequestParam String nome,
            @Parameter(description = "Senha do usuário") @RequestParam String senha) {
        boolean valido = usuarioService.validarLogin(nome, senha);
        return valido ? ResponseEntity.ok().build() : ResponseEntity.status(401).build();
    }

    // GET /api/usuarios/init
    @GetMapping("/init")
    @Operation(summary = "Cria a conta de administrador padrão se não existir")
    @ApiResponse(responseCode = "201", description = "Conta de administrador criada ou já existente")
    public ResponseEntity<UsuarioDTO> criarAdminInicial() {
        UsuarioDTO admin = usuarioService.criarAdminPadrao();
        return ResponseEntity.created(URI.create("/api/usuarios/" + admin.getUsuarioId()))
                .body(admin);
    }
}