package org.serratec.backend.dto;

import org.serratec.backend.entity.Produto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProdutoRequestDTO {
	
	@NotBlank
	private String nomeProduto;
	@NotBlank
	private String descricao;
	@NotNull
	private Double preco;
	
	public ProdutoRequestDTO() {
	}
	
	public ProdutoRequestDTO(Produto produto) {
		this.nomeProduto = produto.getNomeProduto();
		this.descricao = produto.getDescricao();
		this.preco = produto.getPreco();
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}
	
	

}
