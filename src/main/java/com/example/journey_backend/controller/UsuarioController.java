package com.example.journey_backend.controller;

// 1. Imports do Projeto (DTOs, Models, Security, Service)
import com.example.journey_backend.dto.UsuarioDTO;
import com.example.journey_backend.model.Usuario;
import com.example.journey_backend.repository.UsuarioRepository;
import com.example.journey_backend.security.jwt.JwtTokenProvider;
import com.example.journey_backend.security.model.LoginRequest;
import com.example.journey_backend.security.model.LoginResponse;
import com.example.journey_backend.service.UsuarioService;

// 2. Imports do Swagger/OpenAPI
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

// 3. Imports do Jakarta
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

// 4. Imports do Spring Framework (Core & Security)
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

// 5. Imports do Spring Web (Endpoints explícitos)
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 6. Imports do Java Padrão
import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
// @CrossOrigin(origins = "*") <--- REMOVIDO PARA EVITAR CONFLITO COM SECURITY CONFIG
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de Usuários e Autenticação")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    // =================================================================================
    //  1. ENDPOINTS DE LEITURA (Resolve o erro 404 na tabela)
    // =================================================================================

    @GetMapping
    @Operation(summary = "Lista todos os usuários cadastrados")
    public ResponseEntity<List<UsuarioDTO>> listarTodos() {
        // Este método chama o listarUsuarios() do seu Service
        List<UsuarioDTO> lista = usuarioService.listarUsuarios();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um usuário pelo ID")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable int id) {
        UsuarioDTO dto = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    // =================================================================================
    //  2. ENDPOINTS DE AUTENTICAÇÃO (Login e Init)
    // =================================================================================

    @PostMapping("/login")
    @Operation(summary = "Realiza a autenticação do usuário e retorna um token JWT")
    @ApiResponse(responseCode = "200", description = "Login bem-sucedido, token retornado")
    @ApiResponse(responseCode = "401", description = "Nome de usuário ou senha inválidos")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getNome(),
                        loginRequest.getSenha()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new LoginResponse(token));
    }

    @GetMapping("/init")
    @Operation(summary = "Cria a conta de administrador padrão se não existir")
    public ResponseEntity<UsuarioDTO> criarAdminInicial() {
        UsuarioDTO admin = usuarioService.criarAdminPadrao();
        return ResponseEntity.created(URI.create("/api/usuarios/" + admin.getUsuarioId()))
                .body(admin);
    }

    // =================================================================================
    //  3. ENDPOINTS DE ESCRITA (Criação, Edição, Deleção)
    // =================================================================================

    /*
       NOTA: Para criar/editar/deletar usuários normais, geralmente precisamos
       saber QUEM está fazendo a ação para logar no histórico.
       Usamos o Principal para isso.
    */

    @PostMapping
    @Operation(summary = "Cria um novo usuário (Requer Auth)")
    public ResponseEntity<UsuarioDTO> criar(@Valid @RequestBody UsuarioDTO dto, Principal principal) {
        // Pega quem está logado para registrar no histórico
        Usuario autor = getAutor(principal);

        UsuarioDTO novoUsuario = usuarioService.criarUsuario(dto, autor);
        return ResponseEntity.created(URI.create("/api/usuarios/" + novoUsuario.getUsuarioId()))
                .body(novoUsuario);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um usuário existente")
    public ResponseEntity<UsuarioDTO> editar(@PathVariable int id, @Valid @RequestBody UsuarioDTO dto, Principal principal) {
        Usuario autor = getAutor(principal);
        UsuarioDTO usuarioAtualizado = usuarioService.editarUsuario(id, dto, autor);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um usuário (se não tiver histórico)")
    public ResponseEntity<Void> deletar(@PathVariable int id, Principal principal) {
        Usuario autor = getAutor(principal);
        usuarioService.deletarUsuario(id, autor);
        return ResponseEntity.noContent().build();
    }

    // =================================================================================
    //  HELPER
    // =================================================================================
    private Usuario getAutor(Principal principal) {
        if (principal == null) {
            // Se por acaso a segurança falhar e chegar aqui nulo
            throw new SecurityException("Usuário não autenticado.");
        }
        String nomeUsuario = principal.getName();
        return usuarioRepository.findByNome(nomeUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuário (autor) não encontrado: " + nomeUsuario));
    }
}