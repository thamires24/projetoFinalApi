package org.serratec.backend.dto;

import org.serratec.backend.entity.Endereco;

public record EnderecoResponseDTO(String cep, String rua, String bairro, String cidade, String uf) {

	public EnderecoResponseDTO(Endereco endereco) {
		this(endereco.getCep(), endereco.getRua(), endereco.getBairro(), endereco.getCidade(),
				endereco.getUf());
	}
}
