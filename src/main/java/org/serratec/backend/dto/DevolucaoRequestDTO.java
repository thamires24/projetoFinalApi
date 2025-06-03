package org.serratec.backend.dto;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class DevolucaoRequestDTO {

	@NotNull(message = "O ID do cliente é obrigatório.")
	private Long idCliente;

	@NotEmpty(message = "A lista de IDs de produtos não pode ser vazia.")
	private List<Long> idProdutos;

	@NotBlank(message = "O motivo da devolução é obrigatório.")
	private String motivo;

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public List<Long> getIdProdutos() {
		return idProdutos;
	}

	public void setIdProdutos(List<Long> idProdutos) {
		this.idProdutos = idProdutos;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
}