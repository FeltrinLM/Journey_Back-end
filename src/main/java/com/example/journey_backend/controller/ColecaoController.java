package com.example.journey_backend.controller;

import com.example.journey_backend.dto.ColecaoDTO;
import com.example.journey_backend.service.ColecaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
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
        return ResponseEntity.ok(dto); // se não existir, service lança 404
    }

    // POST /api/colecoes
    @PostMapping
    public ResponseEntity<ColecaoDTO> criar(@Valid @RequestBody ColecaoDTO dto) {
        ColecaoDTO criado = colecaoService.criarColecao(dto);
        return ResponseEntity.created(URI.create("/api/colecoes/" + criado.getColecaoId()))
                .body(criado);
    }

    // PUT /api/colecoes/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ColecaoDTO> editar(@PathVariable int id, @Valid @RequestBody ColecaoDTO dto) {
        ColecaoDTO atualizado = colecaoService.editarColecao(id, dto);
        return ResponseEntity.ok(atualizado); // se não existir, service lança 404
    }

    // DELETE /api/colecoes/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        colecaoService.deletarColecao(id);
        return ResponseEntity.noContent().build(); // 204
    }
}
