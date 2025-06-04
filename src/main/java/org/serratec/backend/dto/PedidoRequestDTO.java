package org.serratec.backend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class PedidoRequestDTO {


    @NotEmpty(message = "Lista de itens do pedido não pode ser vazia.")
    @Valid
    private List<ItemPedidoRequestDTO> itens;


    public PedidoRequestDTO() {
    }

    public List<ItemPedidoRequestDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoRequestDTO> itens) {
        this.itens = itens;
    }
}