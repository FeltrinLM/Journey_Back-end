package com.example.journey_backend.controller;

import com.example.journey_backend.dto.PecaDTO;
import com.example.journey_backend.service.PecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
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
        return ResponseEntity.ok(peca); // se não existir, service lança 404
    }

    // POST /api/pecas
    @PostMapping
    public ResponseEntity<PecaDTO> criar(@Valid @RequestBody PecaDTO dto) {
        PecaDTO criada = pecaService.criarPeca(dto);
        return ResponseEntity.created(URI.create("/api/pecas/" + criada.getPecaId()))
                .body(criada);
    }

    // PUT /api/pecas/{id}
    @PutMapping("/{id}")
    public ResponseEntity<PecaDTO> editar(@PathVariable int id, @Valid @RequestBody PecaDTO dto) {
        PecaDTO atualizada = pecaService.editarPeca(id, dto);
        return ResponseEntity.ok(atualizada); // se não existir, service lança 404
    }

    // DELETE /api/pecas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        pecaService.deletarPeca(id);
        return ResponseEntity.noContent().build(); // 204
    }
}
