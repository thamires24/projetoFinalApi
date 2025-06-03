package org.serratec.backend.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.serratec.backend.dto.ProdutoFotoDTO;
import org.serratec.backend.entity.Foto;
import org.serratec.backend.entity.Produto;
import org.serratec.backend.repository.ProdutoRepository;
import org.serratec.backend.service.FotoService;
import org.serratec.backend.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private FotoService fotoService;
	
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
	
	@GetMapping("/{id}/foto")
	public ResponseEntity<byte[]> buscarFoto(@PathVariable Long id){
		Foto foto =		fotoService.buscarPorIdProduto(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", foto.getTipo());
		headers.add("Content-length", String.valueOf(foto.getDados().length));
		return new ResponseEntity<>(foto.getDados(), headers,HttpStatus.OK);
	}
	
	@PostMapping
	public ProdutoFotoDTO inserir(@RequestPart MultipartFile file, @RequestPart Produto produto) throws IOException {
		return produtoService.inserir(produto, file);
	}
	
}
