package com.example.journey_backend.controller;

import com.example.journey_backend.dto.EstampaDTO;
import com.example.journey_backend.service.EstampaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
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
        return ResponseEntity.ok(dto); // se não existir, service lança 404
    }

    // POST /api/estampas
    @PostMapping
    public ResponseEntity<EstampaDTO> criar(@Valid @RequestBody EstampaDTO dto) {
        EstampaDTO criada = estampaService.criarEstampa(dto);
        return ResponseEntity.created(URI.create("/api/estampas/" + criada.getEstampaId()))
                .body(criada);
    }

    // PUT /api/estampas/{id}
    @PutMapping("/{id}")
    public ResponseEntity<EstampaDTO> editar(@PathVariable int id, @Valid @RequestBody EstampaDTO dto) {
        EstampaDTO atualizada = estampaService.editarEstampa(id, dto);
        return ResponseEntity.ok(atualizada); // se não existir, service lança 404
    }

    // DELETE /api/estampas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        estampaService.deletarEstampa(id);
        return ResponseEntity.noContent().build(); // 204
    }
}
