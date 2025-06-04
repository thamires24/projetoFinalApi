package org.serratec.backend.dto;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class DevolucaoRequestDTO {

    @NotNull(message = "O ID do cliente é obrigatório.")
    private Long idCliente;

    @NotNull(message = "O ID do pedido original é obrigatório.")
    private Long idPedidoOriginal;

    @NotEmpty(message = "A lista de IDs de produtos para devolução não pode ser vazia.")
    private List<Long> idProdutosDevolvidos;


    @NotBlank(message = "O motivo da devolução é obrigatório.")
    @Size(max = 500, message = "Motivo não pode exceder 500 caracteres.")
    private String motivo;

    public DevolucaoRequestDTO() {
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Long getIdPedidoOriginal() {
        return idPedidoOriginal;
    }

    public void setIdPedidoOriginal(Long idPedidoOriginal) {
        this.idPedidoOriginal = idPedidoOriginal;
    }

    public List<Long> getIdProdutosDevolvidos() {
        return idProdutosDevolvidos;
    }

    public void setIdProdutosDevolvidos(List<Long> idProdutosDevolvidos) {
        this.idProdutosDevolvidos = idProdutosDevolvidos;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}