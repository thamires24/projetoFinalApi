package org.serratec.backend.controller;

import java.util.List;

import org.serratec.backend.dto.CategoriaRequestDTO;
import org.serratec.backend.dto.CategoriaResponseDTO;
import org.serratec.backend.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(categoriaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> inserir(@Valid @RequestBody CategoriaRequestDTO categoriaRequestDTO) {
        CategoriaResponseDTO categoriaSalva = categoriaService.salvar(categoriaRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody CategoriaRequestDTO categoriaRequestDTO) {
        CategoriaResponseDTO categoriaAtualizada = categoriaService.atualizar(id, categoriaRequestDTO);
        return ResponseEntity.ok(categoriaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        categoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}