package com.example.journey_backend.controller;

import com.example.journey_backend.dto.AdesivoDTO;
import com.example.journey_backend.service.AdesivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adesivos")
@CrossOrigin(origins = "*") // permite chamadas do frontend Angular
public class AdesivoController {

    @Autowired
    private AdesivoService adesivoService;

    // GET /api/adesivos
    @GetMapping
    public ResponseEntity<List<AdesivoDTO>> listarTodos() {
        List<AdesivoDTO> lista = adesivoService.listarAdesivos();
        return ResponseEntity.ok(lista);
    }

    // GET /api/adesivos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<AdesivoDTO> buscarPorId(@PathVariable int id) {
        AdesivoDTO dto = adesivoService.buscarPorId(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /api/adesivos
    @PostMapping
    public ResponseEntity<AdesivoDTO> criar(@RequestBody AdesivoDTO dto) {
        AdesivoDTO criado = adesivoService.criarAdesivo(dto);
        return ResponseEntity.ok(criado);
    }

    // PUT /api/adesivos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<AdesivoDTO> editar(@PathVariable int id, @RequestBody AdesivoDTO dto) {
        try {
            AdesivoDTO atualizado = adesivoService.editarAdesivo(id, dto);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/adesivos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        adesivoService.deletarAdesivo(id);
        return ResponseEntity.noContent().build();
    }
}
