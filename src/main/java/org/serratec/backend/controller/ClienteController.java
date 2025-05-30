package org.serratec.backend.controller;

import java.util.List;
import java.util.Optional;

import org.serratec.backend.entity.Cliente;
import org.serratec.backend.repository.ClienteRepository;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	//private ClienteService ClienteService;
	
	@GetMapping("{id}")
	public ResponseEntity<Cliente> listarPorId(@PathVariable Long id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		return ResponseEntity.ok(cliente.get());
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<Cliente> atualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
		return ResponseEntity.notFound().build();
}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		if (clienteRepository.existsById(id)) {
			clienteRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/cadastro")
	@ResponseStatus(HttpStatus.CREATED)
	public List<Cliente> inserir(@RequestBody List<Cliente> cliente) {
		return clienteRepository.saveAll(cliente);

	}
}
