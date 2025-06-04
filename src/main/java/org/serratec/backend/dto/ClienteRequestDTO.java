package org.serratec.backend.dto;

import org.hibernate.validator.constraints.br.CPF;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ClienteRequestDTO {

    @NotBlank(message = "Nome não pode ser vazio.")
    @Size(max = 100, message = "Nome não pode exceder 100 caracteres.")
    private String nome;

    @NotBlank(message = "Email não pode ser vazio.")
    @Email(message = "Email deve ser válido.")
    @Size(max = 100, message = "Email não pode exceder 100 caracteres.")
    private String email;

    @NotBlank(message = "CPF não pode ser vazio.")
    @CPF(message = "CPF inválido.")
    private String cpf;

    @NotBlank(message = "Telefone não pode ser vazio.")
    @Size(max = 20, message = "Telefone não pode exceder 20 caracteres.")
    private String telefone;

    @NotBlank(message = "Senha não pode ser vazia.")
    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres.")
    private String senha;

    @NotNull(message = "Endereço é obrigatório.")
    @Valid
    private EnderecoRequestDTO endereco;

    private Long idClientePerfil;

    public ClienteRequestDTO() {
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public EnderecoRequestDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoRequestDTO endereco) {
        this.endereco = endereco;
    }

    public Long getIdClientePerfil() {
        return idClientePerfil;
    }

    public void setIdClientePerfil(Long idClientePerfil) {
        this.idClientePerfil = idClientePerfil;
    }
}