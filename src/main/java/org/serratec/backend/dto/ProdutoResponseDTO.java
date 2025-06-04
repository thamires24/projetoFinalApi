package org.serratec.backend.dto;

import org.serratec.backend.entity.Produto;
import java.math.BigDecimal;

public record ProdutoResponseDTO(Long id, String nomeProduto, String descricao, BigDecimal preco,
		CategoriaResponseDTO categoria, String urlFoto) {
	
	public ProdutoResponseDTO(Produto produto, String urlFoto) {
		
		this(produto.getId(), produto.getNomeProduto(), produto.getDescricao(),
				(produto.getPreco() != null) ? BigDecimal.valueOf(produto.getPreco()) : null,

				(produto.getCategoria() != null) ? new CategoriaResponseDTO(produto.getCategoria()) : null, urlFoto);
	}

	public ProdutoResponseDTO(Produto produto) {
		this(produto.getId(), produto.getNomeProduto(), produto.getDescricao(),
				(produto.getPreco() != null) ? BigDecimal.valueOf(produto.getPreco()) : null,
				(produto.getCategoria() != null) ? new CategoriaResponseDTO(produto.getCategoria()) : null, null

		);
	}
}