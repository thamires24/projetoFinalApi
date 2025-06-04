package org.serratec.backend.service;

import java.io.IOException;
import java.util.Optional;

import org.serratec.backend.entity.Foto;
import org.serratec.backend.entity.Produto;
import org.serratec.backend.exception.FotoException;
import org.serratec.backend.repository.FotoRepository;
import org.serratec.backend.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FotoService {

	@Autowired
	private FotoRepository fotoRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Transactional
	public Foto inserirFotoProduto(Long idProduto, MultipartFile file) throws FotoException, IOException {
		Produto produto = produtoRepository.findById(idProduto).orElseThrow(
				() -> new FotoException("Produto com ID " + idProduto + " não encontrado para associar foto."));

		Optional<Foto> fotoExistente = fotoRepository.findByProduto(produto);
		fotoExistente.ifPresent(foto -> fotoRepository.delete(foto));

		Foto novaFoto = new Foto(file.getBytes(), file.getContentType(), file.getOriginalFilename(), produto);
		return fotoRepository.save(novaFoto);
	}

	@Transactional(readOnly = true)
	public Foto buscarFotoPorIdProduto(Long idProduto) throws FotoException {
		Produto produto = new Produto();
		produto.setId(idProduto);

		Optional<Foto> fotoOptional = fotoRepository.findByProduto(produto);

		if (fotoOptional.isPresent()) {
			return fotoOptional.get();
		}
		throw new FotoException("Foto não encontrada para o produto com ID: " + idProduto);
	}

	@Transactional(readOnly = true)
	public Optional<Foto> buscarOptionalFotoPorProduto(Produto produto) {
		return fotoRepository.findByProduto(produto);
	}
}