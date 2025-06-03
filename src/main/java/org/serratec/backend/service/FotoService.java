package org.serratec.backend.service;

import java.io.IOException;
import java.util.Optional;

import org.serratec.backend.entity.Foto;
import org.serratec.backend.entity.Produto;
import org.serratec.backend.exception.FotoException;
import org.serratec.backend.repository.FotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FotoService {
	@Autowired
	private FotoRepository repository;

	public Foto inserir(Produto produto, MultipartFile file) throws IOException {
		Foto foto = new Foto(null, file.getBytes(), file.getContentType(), file.getName(), produto);
		return repository.save(foto);
	}

	public Foto buscarPorIdProduto(Long id) {
		Produto produto = new Produto();
		produto.setId(id);

		Optional<Foto> foto = repository.findByProduto(produto);
		if (foto.isPresent()) {
			return foto.get();
		}
		throw new FotoException("Id do funcionário não encontrado!");
	}
}
