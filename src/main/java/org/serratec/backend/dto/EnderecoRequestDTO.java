package org.serratec.backend.dto;

import jakarta.validation.constraints.NotBlank;
import org.serratec.backend.entity.Endereco;

public class EnderecoRequestDTO {
    @NotBlank
    private String cep;
    private String rua;
    private String bairro;
    private String cidade;
    private String uf;

    public EnderecoRequestDTO() {
    }

    public EnderecoRequestDTO(Endereco endereco) {
        this.cep = endereco.getCep();
        this.rua = endereco.getRua();
        this.bairro = endereco.getBairro();
        this.cidade = endereco.getCidade();
        this.uf = endereco.getUf();
    }

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	} 
    
}