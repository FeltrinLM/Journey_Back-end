package com.example.journey_backend.controller;

import com.example.journey_backend.dto.AdesivoDTO;
import com.example.journey_backend.service.AdesivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
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
        return ResponseEntity.ok(dto); // se não existir, service lança 404
    }

    // POST /api/adesivos
    @PostMapping
    public ResponseEntity<AdesivoDTO> criar(@Valid @RequestBody AdesivoDTO dto) {
        AdesivoDTO criado = adesivoService.criarAdesivo(dto);
        // retorna 201 Created com Location header
        return ResponseEntity.created(URI.create("/api/adesivos/" + criado.getAdesivoId()))
                .body(criado);
    }

    // PUT /api/adesivos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<AdesivoDTO> editar(@PathVariable int id, @Valid @RequestBody AdesivoDTO dto) {
        AdesivoDTO atualizado = adesivoService.editarAdesivo(id, dto);
        return ResponseEntity.ok(atualizado); // se não existir, service lança 404
    }

    // DELETE /api/adesivos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        adesivoService.deletarAdesivo(id);
        return ResponseEntity.noContent().build(); // 204
    }
}
