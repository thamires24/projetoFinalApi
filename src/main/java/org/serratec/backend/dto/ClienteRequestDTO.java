package org.serratec.backend.dto;


import org.hibernate.validator.constraints.br.CPF;
import org.serratec.backend.entity.Cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ClienteRequestDTO {

	@NotBlank
	private String nome;
	@CPF
	private String cpf;
	@Email
	private String email;
	@NotBlank
	private String telefone;

	@Pattern(regexp = "\\d{5}-?d{3}", message = "CEP inválido!")
	private String cep;

	public ClienteRequestDTO() {
	}

	public ClienteRequestDTO(Cliente cliente) {
		this.nome = cliente.getNome();
		this.cpf = cliente.getCpf();
		this.email = cliente.getEmail();
		this.telefone = cliente.getTelefone();
		this.cep = cliente.getEndereco().getCep();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

}
