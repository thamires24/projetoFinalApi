package org.serratec.backend.controller;

import java.io.IOException;
import java.util.List;

import org.serratec.backend.dto.ProdutoFotoDTO;
import org.serratec.backend.dto.ProdutoRequestDTO;
import org.serratec.backend.dto.ProdutoResponseDTO;
import org.serratec.backend.entity.Foto;
import org.serratec.backend.service.FotoService;
import org.serratec.backend.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private FotoService fotoService;

	@GetMapping
	public ResponseEntity<List<ProdutoResponseDTO>> listarTodos() {
		return ResponseEntity.ok(produtoService.listarTodos());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Long id) {
		return ResponseEntity.ok(produtoService.buscarPorId(id));
	}

	@PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<ProdutoResponseDTO> inserirComFoto(
			@Valid @RequestPart("produto") ProdutoRequestDTO produtoRequestDTO,
			@RequestPart(name = "file", required = false) MultipartFile file) throws IOException {
		ProdutoResponseDTO produtoSalvo = produtoService.salvar(produtoRequestDTO, file);
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProdutoResponseDTO> atualizar(@PathVariable Long id,
			@Valid @RequestPart("produto") ProdutoRequestDTO produtoRequestDTO) {
		ProdutoResponseDTO produtoAtualizado = produtoService.atualizar(id, produtoRequestDTO);
		return ResponseEntity.ok(produtoAtualizado);
	}

	@PutMapping("/{id}/foto")
	public ResponseEntity<ProdutoFotoDTO> atualizarFoto(@PathVariable Long id, @RequestPart("file") MultipartFile file)
			throws IOException {
		ProdutoFotoDTO fotoInfo = produtoService.atualizarFoto(id, file);
		return ResponseEntity.ok(fotoInfo);
	}

	@GetMapping("/{id}/foto")
	public ResponseEntity<byte[]> buscarFoto(@PathVariable Long id) {
		Foto foto = fotoService.buscarFotoPorIdProduto(id);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(foto.getTipo()));
		return new ResponseEntity<>(foto.getDados(), headers, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		produtoService.deletar(id);
		return ResponseEntity.noContent().build();
	}
}