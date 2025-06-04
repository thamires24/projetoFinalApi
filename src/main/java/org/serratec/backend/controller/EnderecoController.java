package org.serratec.backend.controller;

import org.serratec.backend.dto.EnderecoResponseDTO;
import org.serratec.backend.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping("/cep/{cep}")
    public ResponseEntity<EnderecoResponseDTO> consultarCep(@PathVariable String cep) {
        return ResponseEntity.ok(enderecoService.buscarDtoPorCep(cep));
    }
}