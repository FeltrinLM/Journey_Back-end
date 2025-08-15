package com.example.journey_backend.controller;

import com.example.journey_backend.dto.UsuarioDTO;
import com.example.journey_backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /api/usuarios
    @PostMapping
    public ResponseEntity<UsuarioDTO> criar(@RequestBody UsuarioDTO dto) {
        try {
            UsuarioDTO criado = usuarioService.criarUsuario(dto);
            return ResponseEntity.ok(criado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // PUT /api/usuarios/{id}
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> editar(@PathVariable int id, @RequestBody UsuarioDTO dto) {
        try {
            UsuarioDTO atualizado = usuarioService.editarUsuario(id, dto);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/usuarios/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    // POST /api/usuarios/login
    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestParam String nome, @RequestParam String senha) {
        boolean valido = usuarioService.validarLogin(nome, senha);
        return ResponseEntity.ok(valido);
    }

    // GET /api/usuarios/init
    @GetMapping("/init")
    public ResponseEntity<UsuarioDTO> criarAdminInicial() {
        try {
            UsuarioDTO admin = usuarioService.criarAdminPadrao();
            return ResponseEntity.ok(admin);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
