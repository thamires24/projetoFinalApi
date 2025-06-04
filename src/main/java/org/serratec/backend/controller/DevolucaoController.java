package org.serratec.backend.controller;

import java.util.List;

import org.serratec.backend.dto.DevolucaoRequestDTO;
import org.serratec.backend.dto.DevolucaoResponseDTO;
import org.serratec.backend.service.DevolucaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/devolucoes")
public class DevolucaoController {

	@Autowired
	private DevolucaoService devolucaoService;

	@PostMapping("/solicitar")
	public ResponseEntity<DevolucaoResponseDTO> solicitarNovaDevolucao(
			@Valid @RequestBody DevolucaoRequestDTO requestDTO) {
		DevolucaoResponseDTO responseDTO = devolucaoService.registrarNovaDevolucao(requestDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
	}

	@GetMapping("/cliente/{idCliente}")
	public ResponseEntity<List<DevolucaoResponseDTO>> listarDevolucoesPorCliente(@PathVariable Long idCliente) {
		List<DevolucaoResponseDTO> devolucoes = devolucaoService.listarDevolucoesPorCliente(idCliente);
		return ResponseEntity.ok(devolucoes);
	}

}