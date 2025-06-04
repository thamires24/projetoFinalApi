package org.serratec.backend.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.serratec.backend.dto.ProdutoFotoDTO;
import org.serratec.backend.dto.ProdutoRequestDTO;
import org.serratec.backend.dto.ProdutoResponseDTO;
import org.serratec.backend.entity.Categoria;
import org.serratec.backend.entity.Foto;
import org.serratec.backend.entity.Produto;
import org.serratec.backend.exception.CategoriaException;
import org.serratec.backend.exception.ProdutoException;
import org.serratec.backend.repository.CategoriaRepository;
import org.serratec.backend.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private FotoService fotoService;

	@Transactional(readOnly = true)
	public List<ProdutoResponseDTO> listarTodos() {
		return produtoRepository.findAll().stream()
				.map(produto -> new ProdutoResponseDTO(produto, construirUrlFoto(produto)))
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public ProdutoResponseDTO buscarPorId(Long id) throws ProdutoException {
		Produto produto = produtoRepository.findById(id)
				.orElseThrow(() -> new ProdutoException("Produto com ID " + id + " não encontrado."));
		return new ProdutoResponseDTO(produto, construirUrlFoto(produto));
	}

	@Transactional
	public ProdutoResponseDTO salvar(ProdutoRequestDTO produtoRequestDTO, MultipartFile file)
			throws ProdutoException, CategoriaException, IOException {
		Categoria categoria = categoriaRepository.findById(produtoRequestDTO.getIdCategoria())
				.orElseThrow(() -> new CategoriaException(
						"Categoria com ID " + produtoRequestDTO.getIdCategoria() + " não encontrada."));

		Produto produto = new Produto();
		produto.setNomeProduto(produtoRequestDTO.getNomeProduto());
		produto.setDescricao(produtoRequestDTO.getDescricao());
		produto.setPreco(produtoRequestDTO.getPreco() != null ? produtoRequestDTO.getPreco().doubleValue() : null);
		produto.setCategoria(categoria);

		Produto produtoSalvo = produtoRepository.save(produto);

		if (file != null && !file.isEmpty()) {
			fotoService.inserirFotoProduto(produtoSalvo.getId(), file);
		}
		return new ProdutoResponseDTO(produtoSalvo, construirUrlFoto(produtoSalvo));
	}

	@Transactional
	public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO produtoRequestDTO)
			throws ProdutoException, CategoriaException {
		Produto produtoExistente = produtoRepository.findById(id)
				.orElseThrow(() -> new ProdutoException("Produto com ID " + id + " não encontrado para atualização."));

		Categoria categoria = categoriaRepository.findById(produtoRequestDTO.getIdCategoria())
				.orElseThrow(() -> new CategoriaException(
						"Categoria com ID " + produtoRequestDTO.getIdCategoria() + " não encontrada."));

		produtoExistente.setNomeProduto(produtoRequestDTO.getNomeProduto());
		produtoExistente.setDescricao(produtoRequestDTO.getDescricao());
		produtoExistente
				.setPreco(produtoRequestDTO.getPreco() != null ? produtoRequestDTO.getPreco().doubleValue() : null);
		produtoExistente.setCategoria(categoria);

		Produto produtoAtualizado = produtoRepository.save(produtoExistente);
		return new ProdutoResponseDTO(produtoAtualizado, construirUrlFoto(produtoAtualizado));
	}

	@Transactional
	public ProdutoFotoDTO atualizarFoto(Long idProduto, MultipartFile file) throws ProdutoException, IOException {
		Produto produto = produtoRepository.findById(idProduto).orElseThrow(
				() -> new ProdutoException("Produto com ID " + idProduto + " não encontrado para atualizar foto."));
		fotoService.inserirFotoProduto(idProduto, file);
		return new ProdutoFotoDTO(produto.getNomeProduto(), construirUrlFoto(produto));
	}

	@Transactional
	public void deletar(Long id) throws ProdutoException {
		if (!produtoRepository.existsById(id)) {
			throw new ProdutoException("Produto com ID " + id + " não encontrado para exclusão.");
		}
		produtoRepository.deleteById(id);
	}

	private String construirUrlFoto(Produto produto) {
		Optional<Foto> fotoOptional = fotoService.buscarOptionalFotoPorProduto(produto);
		if (fotoOptional.isPresent() && fotoOptional.get().getId() != null) {
			URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/produtos/{idProduto}/foto")
					.buildAndExpand(produto.getId()).toUri();
			return uri.toString();
		}
		return null;
	}
}