package org.serratec.backend.dto;

import java.time.LocalDate;

import org.serratec.backend.entity.Pedido;

public record PedidoResponseDTO(Long id, LocalDate dataPedido, Double valorPedido) {

	public PedidoResponseDTO(Pedido pedido) {
		this(pedido.getId(), pedido.getDataPedido(), pedido.getValorPedido());
	}
}