package org.serratec.backend.dto;

import org.serratec.backend.entity.Cliente;

public record ClienteResponseDTO(Long id, String nome, String email, String cpf, String telefone,
		EnderecoResponseDTO endereco) {
	
	public ClienteResponseDTO(Cliente cliente) {
		this(cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getCpf(), cliente.getTelefone(),
				(cliente.getEndereco() != null) ? new EnderecoResponseDTO(cliente.getEndereco()) : null);
	}
}