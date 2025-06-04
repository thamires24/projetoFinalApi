package org.serratec.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class WishlistRequestDTO {

	@NotNull(message = "ID do produto é obrigatório.")
	private Long idProduto;

	@Positive(message = "Quantidade desejada, se informada, deve ser positiva.")
	private Integer quantidadeDesejada;

	public WishlistRequestDTO() {
	}

	public Long getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}

	public Integer getQuantidadeDesejada() {
		return quantidadeDesejada;
	}

	public void setQuantidadeDesejada(Integer quantidadeDesejada) {
		this.quantidadeDesejada = quantidadeDesejada;
	}
}