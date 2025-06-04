package org.serratec.backend.controller;

import java.util.List;

import org.serratec.backend.dto.ClienteRequestDTO;
import org.serratec.backend.dto.ClienteResponseDTO;
import org.serratec.backend.service.ClienteService;
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
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@PostMapping
	public ResponseEntity<ClienteResponseDTO> inserir(@Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {
		ClienteResponseDTO clienteSalvo = clienteService.salvar(clienteRequestDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long id) {
		return ResponseEntity.ok(clienteService.buscarPorId(id));
	}

	@GetMapping
	public ResponseEntity<List<ClienteResponseDTO>> listarTodos() {
		return ResponseEntity.ok(clienteService.listarTodos());
	}

	@PutMapping("/{id}")
	public ResponseEntity<ClienteResponseDTO> atualizar(@PathVariable Long id,
			@Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {
		ClienteResponseDTO clienteAtualizado = clienteService.atualizar(id, clienteRequestDTO);
		return ResponseEntity.ok(clienteAtualizado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		clienteService.deletar(id);
		return ResponseEntity.noContent().build();
	}
}