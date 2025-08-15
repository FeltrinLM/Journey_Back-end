package com.example.journey_backend.controller;

import com.example.journey_backend.dto.ColecaoDTO;
import com.example.journey_backend.service.ColecaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/colecoes")
@CrossOrigin(origins = "*") // Permite acesso do frontend Angular
public class ColecaoController {

    @Autowired
    private ColecaoService colecaoService;

    // GET /api/colecoes
    @GetMapping
    public ResponseEntity<List<ColecaoDTO>> listarTodas() {
        List<ColecaoDTO> colecoes = colecaoService.listarColecoes();
        return ResponseEntity.ok(colecoes);
    }

    // GET /api/colecoes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ColecaoDTO> buscarPorId(@PathVariable int id) {
        ColecaoDTO dto = colecaoService.buscarPorId(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /api/colecoes
    @PostMapping
    public ResponseEntity<ColecaoDTO> criar(@RequestBody ColecaoDTO dto) {
        try {
            ColecaoDTO criado = colecaoService.criarColecao(dto);
            return ResponseEntity.ok(criado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // PUT /api/colecoes/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ColecaoDTO> editar(@PathVariable int id, @RequestBody ColecaoDTO dto) {
        try {
            ColecaoDTO atualizado = colecaoService.editarColecao(id, dto);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/colecoes/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        colecaoService.deletarColecao(id);
        return ResponseEntity.noContent().build();
    }
}
