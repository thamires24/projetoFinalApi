package org.serratec.backend.dto;

public class ProdutoFotoDTO {

    private String nomeProduto;
    private String url;

    public ProdutoFotoDTO() {
    }

    public ProdutoFotoDTO(String nomeProduto, String url) {
        this.nomeProduto = nomeProduto;
        this.url = url;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}