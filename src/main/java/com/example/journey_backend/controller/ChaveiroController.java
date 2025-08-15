package com.example.journey_backend.controller;

import com.example.journey_backend.dto.ChaveiroDTO;
import com.example.journey_backend.service.ChaveiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chaveiros")
@CrossOrigin(origins = "*") // Permite chamadas de qualquer origem (ex: Angular)
public class ChaveiroController {

    @Autowired
    private ChaveiroService chaveiroService;

    // GET /api/chaveiros
    @GetMapping
    public ResponseEntity<List<ChaveiroDTO>> listarTodos() {
        List<ChaveiroDTO> chaveiros = chaveiroService.listarChaveiros();
        return ResponseEntity.ok(chaveiros);
    }

    // GET /api/chaveiros/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ChaveiroDTO> buscarPorId(@PathVariable int id) {
        ChaveiroDTO dto = chaveiroService.buscarPorId(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /api/chaveiros
    @PostMapping
    public ResponseEntity<ChaveiroDTO> criar(@RequestBody ChaveiroDTO dto) {
        try {
            ChaveiroDTO criado = chaveiroService.criarChaveiro(dto);
            return ResponseEntity.ok(criado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // PUT /api/chaveiros/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ChaveiroDTO> editar(@PathVariable int id, @RequestBody ChaveiroDTO dto) {
        try {
            ChaveiroDTO atualizado = chaveiroService.editarChaveiro(id, dto);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/chaveiros/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        chaveiroService.deletarChaveiro(id);
        return ResponseEntity.noContent().build();
    }
}
