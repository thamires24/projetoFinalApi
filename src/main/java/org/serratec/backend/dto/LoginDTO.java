package org.serratec.backend.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginDTO {

	@NotBlank(message = "Username (email ou CPF) não pode ser vazio.")
	private String username;

	@NotBlank(message = "Senha não pode ser vazia.")
	private String password;

	public LoginDTO() {
	}

	public LoginDTO(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}