package org.serratec.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public class ProdutoRequestDTO {

	@NotBlank(message = "Nome do produto não pode ser vazio.")
	@Size(max = 100, message = "Nome do produto não pode exceder 100 caracteres.")
	private String nomeProduto;

	@Size(max = 255, message = "Descrição não pode exceder 255 caracteres.")
	private String descricao;

	@NotNull(message = "Preço não pode ser nulo.")
	@Positive(message = "Preço deve ser positivo.")
	private BigDecimal preco;

	@NotNull(message = "ID da categoria é obrigatório.")
	private Long idCategoria;

	public ProdutoRequestDTO() {
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

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}
}