package com.example.journey_backend.controller;

import com.example.journey_backend.dto.PecaDTO;
import com.example.journey_backend.service.PecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pecas")
@CrossOrigin(origins = "*") // permite requisições do Angular (ou qualquer frontend)
public class PecaController {

    @Autowired
    private PecaService pecaService;

    // GET /api/pecas
    @GetMapping
    public ResponseEntity<List<PecaDTO>> listarTodas() {
        List<PecaDTO> pecas = pecaService.listarPecas();
        return ResponseEntity.ok(pecas);
    }

    // GET /api/pecas/{id}
    @GetMapping("/{id}")
    public ResponseEntity<PecaDTO> buscarPorId(@PathVariable int id) {
        PecaDTO peca = pecaService.buscarPorId(id);
        if (peca != null) {
            return ResponseEntity.ok(peca);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /api/pecas
    @PostMapping
    public ResponseEntity<PecaDTO> criar(@RequestBody PecaDTO dto) {
        PecaDTO criada = pecaService.criarPeca(dto);
        return ResponseEntity.ok(criada);
    }

    // PUT /api/pecas/{id}
    @PutMapping("/{id}")
    public ResponseEntity<PecaDTO> editar(@PathVariable int id, @RequestBody PecaDTO dto) {
        try {
            PecaDTO atualizada = pecaService.editarPeca(id, dto);
            return ResponseEntity.ok(atualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/pecas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        pecaService.deletarPeca(id);
        return ResponseEntity.noContent().build();
    }
}
