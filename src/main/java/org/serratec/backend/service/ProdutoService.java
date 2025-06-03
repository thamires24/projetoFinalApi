package org.serratec.backend.service;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.serratec.backend.dto.ProdutoFotoDTO;
import org.serratec.backend.entity.Categoria;
import org.serratec.backend.entity.Produto;
import org.serratec.backend.exception.ProdutoException;
import org.serratec.backend.repository.CategoriaRepository;
import org.serratec.backend.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.transaction.Transactional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Autowired
    private FotoService fotoService;

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    public Produto salvar(Produto produto) throws Exception {
        if (produto.getCategoria() != null) {
            Optional<Categoria> categoriaOpt = categoriaRepository.findById(produto.getCategoria().getId());
            if (!categoriaOpt.isPresent()) {
                throw new Exception("Categoria n達o encontrada com id: " + produto.getCategoria().getId());
            }
            produto.setCategoria(categoriaOpt.get());
        }
        return produtoRepository.save(produto);
    }

    public Produto atualizar(Long id, Produto produtoAtualizado) throws Exception {
        Optional<Produto> produtoExistente = produtoRepository.findById(id);
        if (!produtoExistente.isPresent()) {
            throw new Exception("Produto não encontrado com id: " + id);
        }
        Produto produto = produtoExistente.get();

        produto.setNomeProduto(produtoAtualizado.getNomeProduto());
        produto.setDescricao(produtoAtualizado.getDescricao());
        produto.setPreco(produtoAtualizado.getPreco());

        if (produtoAtualizado.getCategoria() != null) {
            Optional<Categoria> categoriaOpt = categoriaRepository.findById(produtoAtualizado.getCategoria().getId());
            if (!categoriaOpt.isPresent()) {
                throw new Exception("Categoria não encontrada com id: " + produtoAtualizado.getCategoria().getId());
            }
            produto.setCategoria(categoriaOpt.get());
        } else {
            produto.setCategoria(null);
        }

        return produtoRepository.save(produto);
    }

    public void deletar(Long id) throws Exception {
        Optional<Produto> produtoExistente = produtoRepository.findById(id);
        if (!produtoExistente.isPresent()) {
            throw new Exception("Produto não encontrado com id: " + id);
        }
        produtoRepository.deleteById(id);
    }
    
    public ProdutoFotoDTO adicionarImagemUri(Produto produto) {
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/produtos/{id}/foto")
				.buildAndExpand(produto.getId()).toUri();

		ProdutoFotoDTO dto = new ProdutoFotoDTO();
		dto.setNome(produto.getNomeProduto());
		dto.setUrl(uri.toString());
		return dto;
	}

	public ProdutoFotoDTO buscar(Long id) {
		Optional<Produto> produto = produtoRepository.findById(id);
		if (produto.isPresent()) {
			return adicionarImagemUri(produto.get());
		}
		throw new ProdutoException("Produto não encontrado");
	}

	@Transactional
	public ProdutoFotoDTO inserir(Produto produto, MultipartFile file) throws IOException {
		produto = produtoRepository.save(produto);
		fotoService.inserir(produto, file);
		return adicionarImagemUri(produto);
	}
}