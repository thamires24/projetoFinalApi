package org.serratec.backend.controller;

import java.util.List;
import java.util.Optional;

import org.serratec.backend.entity.PedidoProduto;
import org.serratec.backend.repository.PedidoProdutoRepository;
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
@RequestMapping("/pedidoProdutos")
public class PedidoProdutoController {

	@Autowired
	private PedidoProdutoRepository pedidoProdutoRepository;
	//private PedidoProdutoService PedidoProdutoService;
	
//	@GetMapping()
//	public ResponseEntity<PedidoProduto> listar(){
//		return new ResponseEntity<>(pedidoProdutoService.getAllPedidoProdutos(),HttpStatus.OK);
//	}
	
	@GetMapping("{id}")
	public ResponseEntity<PedidoProduto> listarPorId(@PathVariable Long id) {
		Optional<PedidoProduto> pedidoProduto = pedidoProdutoRepository.findById(id);
		return ResponseEntity.ok(pedidoProduto.get());
	}
	
	@PutMapping
	public ResponseEntity<PedidoProduto> atualizar(@PathVariable Long id, @RequestBody PedidoProduto pedidoProduto) {
		return ResponseEntity.notFound().build();
}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		if (pedidoProdutoRepository.existsById(id)) {
			pedidoProdutoRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public List<PedidoProduto> inserir(@RequestBody List<PedidoProduto> pedidoProduto) {
		return pedidoProdutoRepository.saveAll(pedidoProduto);

	}
}
