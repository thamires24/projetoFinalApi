package org.serratec.backend.dto;

import java.time.LocalDate;
import java.util.List;

public class PedidoAgendadoDTO {
    private Long idCliente;
    private LocalDate dataEntrega;
    private List<ItemPedidoRequestDTO> itens;

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public LocalDate getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public List<ItemPedidoRequestDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoRequestDTO> itens) {
        this.itens = itens;
    }
}
