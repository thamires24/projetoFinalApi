package org.serratec.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class ItemPedidoRequestDTO {

    @NotNull(message = "ID do produto é obrigatório.")
    private Long idProduto;

    @NotNull(message = "Quantidade é obrigatória.")
    @Positive(message = "Quantidade deve ser positiva.")
    private Integer quantidade;

    @Positive(message = "Desconto, se informado, deve ser positivo ou zero.")
    private BigDecimal desconto;

    public ItemPedidoRequestDTO() {
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }
}