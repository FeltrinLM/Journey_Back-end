package com.example.journey_backend.controller;

import com.example.journey_backend.dto.EstampaDTO;
import com.example.journey_backend.service.EstampaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estampas")
@CrossOrigin(origins = "*") // Libera acesso do frontend (ex: Angular)
public class EstampaController {

    @Autowired
    private EstampaService estampaService;

    // GET /api/estampas
    @GetMapping
    public ResponseEntity<List<EstampaDTO>> listarTodas() {
        List<EstampaDTO> estampas = estampaService.listarEstampas();
        return ResponseEntity.ok(estampas);
    }

    // GET /api/estampas/{id}
    @GetMapping("/{id}")
    public ResponseEntity<EstampaDTO> buscarPorId(@PathVariable int id) {
        EstampaDTO dto = estampaService.buscarPorId(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /api/estampas
    @PostMapping
    public ResponseEntity<EstampaDTO> criar(@RequestBody EstampaDTO dto) {
        try {
            EstampaDTO criada = estampaService.criarEstampa(dto);
            return ResponseEntity.ok(criada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // PUT /api/estampas/{id}
    @PutMapping("/{id}")
    public ResponseEntity<EstampaDTO> editar(@PathVariable int id, @RequestBody EstampaDTO dto) {
        try {
            EstampaDTO atualizada = estampaService.editarEstampa(id, dto);
            return ResponseEntity.ok(atualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/estampas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        estampaService.deletarEstampa(id);
        return ResponseEntity.noContent().build();
    }
}
