package org.serratec.backend.controller;

import java.util.List;
import java.util.Optional;

import org.serratec.backend.entity.Produto;
import org.serratec.backend.repository.ProdutoRepository;
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
@RequestMapping("/produtos")
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	//private ProdutoService ProdutoService;
	
	@GetMapping("{id}")
	public ResponseEntity<Produto> listarPorId(@PathVariable Long id) {
		Optional<Produto> produto = produtoRepository.findById(id);
		return ResponseEntity.ok(produto.get());
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<Produto> atualizar(@PathVariable Long id, @RequestBody Produto produto) {
		return ResponseEntity.notFound().build();
}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		if (produtoRepository.existsById(id)) {
			produtoRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/cadastro")
	@ResponseStatus(HttpStatus.CREATED)
	public List<Produto> inserir(@RequestBody List<Produto> produto) {
		return produtoRepository.saveAll(produto);

	}
}
