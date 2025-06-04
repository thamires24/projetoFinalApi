package org.serratec.backend.controller;

import org.serratec.backend.entity.ClientePerfil;
import org.serratec.backend.repository.ClientePerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/perfis-cliente")
public class ClientePerfilController {

	@Autowired
	private ClientePerfilRepository clientePerfilRepository;

	@PostMapping
	public ResponseEntity<ClientePerfil> criarPerfil(@RequestBody ClientePerfil clientePerfil) {
		ClientePerfil perfilSalvo = clientePerfilRepository.save(clientePerfil);
		return ResponseEntity.status(HttpStatus.CREATED).body(perfilSalvo);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ClientePerfil> buscarPerfilPorId(@PathVariable Long id) {
		return clientePerfilRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping
	public ResponseEntity<List<ClientePerfil>> listarTodosPerfis() {
		return ResponseEntity.ok(clientePerfilRepository.findAll());
	}

}