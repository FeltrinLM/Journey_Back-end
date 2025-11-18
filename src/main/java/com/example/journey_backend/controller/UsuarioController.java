package com.example.journey_backend.controller;

import com.example.journey_backend.dto.UsuarioDTO;
import com.example.journey_backend.security.model.LoginRequest; // Importado
import com.example.journey_backend.security.model.LoginResponse; // Importado
import com.example.journey_backend.security.jwt.JwtTokenProvider; // Importado
import com.example.journey_backend.model.Usuario;
import com.example.journey_backend.repository.UsuarioRepository;
import com.example.journey_backend.service.UsuarioService;

// ... (outros imports)
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager; // Importado
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; // Importado
import org.springframework.security.core.Authentication; // Importado
import org.springframework.security.core.context.SecurityContextHolder; // Importado
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de Usuários e Autenticação")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // --- 1. INJEÇÕES NECESSÁRIAS PARA O LOGIN ---
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    // (O helper getAutor() que adicionei antes)
    private Usuario getAutor(Principal principal) {
        if (principal == null) {
            throw new SecurityException("Usuário não autenticado.");
        }
        String nomeUsuario = principal.getName();
        return usuarioRepository.findByNome(nomeUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuário (autor) não encontrado: " + nomeUsuario));
    }

    // ... (listarTodos, buscarPorId, criar, editar, deletar... continuam aqui como corrigimos antes) ...

    // --- 2. MÉTODO DE LOGIN CORRIGIDO ---
    // Ele não usa mais @RequestParam, ele usa @RequestBody
    // Ele não retorna ResponseEntity<Void>, ele retorna ResponseEntity<LoginResponse>
    @PostMapping("/login")
    @Operation(summary = "Realiza a autenticação do usuário e retorna um token JWT")
    @ApiResponse(responseCode = "200", description = "Login bem-sucedido, token retornado")
    @ApiResponse(responseCode = "401", description = "Nome de usuário ou senha inválidos")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {

        // 1. Tenta autenticar usando o AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getNome(),
                        loginRequest.getSenha()
                )
        );

        // 2. Se for bem-sucedido, define o contexto de segurança
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. Gera o token JWT
        String token = tokenProvider.generateToken(authentication);

        // 4. Retorna o token em um JSON
        return ResponseEntity.ok(new LoginResponse(token));
    }

    // GET /api/usuarios/init
    @GetMapping("/init")
    @Operation(summary = "Cria a conta de administrador padrão se não existir")
    //... (Resto do método)
    public ResponseEntity<UsuarioDTO> criarAdminInicial() {
        UsuarioDTO admin = usuarioService.criarAdminPadrao();
        return ResponseEntity.created(URI.create("/api/usuarios/" + admin.getUsuarioId()))
                .body(admin);
    }
}