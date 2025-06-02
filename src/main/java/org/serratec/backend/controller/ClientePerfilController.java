package org.serratec.backend.controller;

import java.util.List;
import java.util.Optional;

import org.serratec.backend.entity.ClientePerfil;
import org.serratec.backend.repository.ClientePerfilRepository;
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
@RequestMapping("/perfis")
public class ClientePerfilController {
	
	@Autowired
	private ClientePerfilRepository clienteRepository;
	//private clientePerfilPerfilService clientePerfilPerfilService;
	
	@GetMapping("{id}")
	public ResponseEntity<ClientePerfil> listarPorId(@PathVariable Long id) {
		Optional<ClientePerfil> cliente = clienteRepository.findById(id);
		return ResponseEntity.ok(cliente.get());
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<ClientePerfil> atualizar(@PathVariable Long id, @RequestBody ClientePerfil cliente) {
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
	public List<ClientePerfil> inserir(@RequestBody List<ClientePerfil> cliente) {
		return clienteRepository.saveAll(cliente);

	}
}
