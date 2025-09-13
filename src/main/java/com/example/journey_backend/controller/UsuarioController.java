package com.example.journey_backend.controller;

import com.example.journey_backend.dto.UsuarioDTO;
import com.example.journey_backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*") // permite requisições de qualquer origem (ajustável no deploy)
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // GET /api/usuarios
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarTodos() {
        List<UsuarioDTO> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    // GET /api/usuarios/{id}
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable int id) {
        UsuarioDTO usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario); // se não existir, service lança 404
    }

    // POST /api/usuarios
    @PostMapping
    public ResponseEntity<UsuarioDTO> criar(@Valid @RequestBody UsuarioDTO dto) {
        UsuarioDTO criado = usuarioService.criarUsuario(dto);
        return ResponseEntity.created(URI.create("/api/usuarios/" + criado.getUsuarioId()))
                .body(criado);
    }

    // PUT /api/usuarios/{id}
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> editar(@PathVariable int id, @Valid @RequestBody UsuarioDTO dto) {
        UsuarioDTO atualizado = usuarioService.editarUsuario(id, dto);
        return ResponseEntity.ok(atualizado); // se não existir, service lança 404
    }

    // DELETE /api/usuarios/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build(); // se tiver histórico, service lança IllegalStateException → 409 via ControllerAdvice
    }

    // POST /api/usuarios/login
    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestParam String nome, @RequestParam String senha) {
        boolean valido = usuarioService.validarLogin(nome, senha);
        return valido ? ResponseEntity.ok().build() : ResponseEntity.status(401).build();
    }

    // GET /api/usuarios/init
    @GetMapping("/init")
    public ResponseEntity<UsuarioDTO> criarAdminInicial() {
        UsuarioDTO admin = usuarioService.criarAdminPadrao();
        return ResponseEntity.created(URI.create("/api/usuarios/" + admin.getUsuarioId()))
                .body(admin);
    }
}
