package org.serratec.backend.dto;

import org.serratec.backend.entity.Endereco;

public record EnderecoResponseDTO(String cep, String logradouro, String complemento, String bairro, String localidade,String uf, String numero) {
	public EnderecoResponseDTO(Endereco endereco) {
		this(endereco.getCep(), endereco.getLogradouro(), endereco.getComplemento(), endereco.getBairro(),
				endereco.getLocalidade(), endereco.getUf(), endereco.getNumero());
	}
}