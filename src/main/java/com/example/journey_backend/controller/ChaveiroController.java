package com.example.journey_backend.controller;

import com.example.journey_backend.dto.ChaveiroDTO;
import com.example.journey_backend.service.ChaveiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
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
        return ResponseEntity.ok(dto); // se não existir, service lança 404
    }

    // POST /api/chaveiros
    @PostMapping
    public ResponseEntity<ChaveiroDTO> criar(@Valid @RequestBody ChaveiroDTO dto) {
        ChaveiroDTO criado = chaveiroService.criarChaveiro(dto);
        return ResponseEntity.created(URI.create("/api/chaveiros/" + criado.getChaveiroId()))
                .body(criado);
    }

    // PUT /api/chaveiros/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ChaveiroDTO> editar(@PathVariable int id, @Valid @RequestBody ChaveiroDTO dto) {
        ChaveiroDTO atualizado = chaveiroService.editarChaveiro(id, dto);
        return ResponseEntity.ok(atualizado); // se não existir, service lança 404
    }

    // DELETE /api/chaveiros/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        chaveiroService.deletarChaveiro(id);
        return ResponseEntity.noContent().build(); // 204
    }
}
