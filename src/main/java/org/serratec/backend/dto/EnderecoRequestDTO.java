package org.serratec.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class EnderecoRequestDTO {

	@NotBlank(message = "CEP não pode ser vazio.")
	@Pattern(regexp = "^\\d{8}$", message = "CEP deve conter 8 dígitos numéricos.")
	private String cep;


	@NotBlank(message = "Número não pode ser vazio.")
	@Size(max = 20, message = "Número não pode exceder 20 caracteres.")
	private String numero;

	@Size(max = 100, message = "Complemento não pode exceder 100 caracteres.")
	private String complemento;

	public EnderecoRequestDTO() {
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
}