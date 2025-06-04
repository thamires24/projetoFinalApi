package org.serratec.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoriaRequestDTO {

    @NotBlank(message = "O nome da categoria não pode ser vazio.")
    @Size(max = 50, message = "O nome da categoria não pode exceder 50 caracteres.")
    private String nomeCategoria;

    public CategoriaRequestDTO() {
    }

    public CategoriaRequestDTO(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }
}