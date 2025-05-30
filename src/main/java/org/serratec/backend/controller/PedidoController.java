package org.serratec.backend.controller;

import java.util.List;
import java.util.Optional;

import org.serratec.backend.entity.Pedido;
import org.serratec.backend.repository.PedidoRepository;
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
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private PedidoRepository pedidoRepository;
	//private PedidoService PedidoService;
	
//	@GetMapping()
//	public ResponseEntity<Pedido> listar(){
//		return new ResponseEntity<>(pedidoService.getAllPedidos(),HttpStatus.OK);
//	}
	
	@GetMapping("{id}")
	public ResponseEntity<Pedido> listarPorId(@PathVariable Long id) {
		Optional<Pedido> pedido = pedidoRepository.findById(id);
		return ResponseEntity.ok(pedido.get());
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<Pedido> atualizar(@PathVariable Long id, @RequestBody Pedido pedido) {
		return ResponseEntity.notFound().build();
}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		if (pedidoRepository.existsById(id)) {
			pedidoRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public List<Pedido> inserir(@RequestBody List<Pedido> pedido) {
		return pedidoRepository.saveAll(pedido);

	}
}
