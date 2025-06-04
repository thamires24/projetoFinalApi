package org.serratec.backend.dto;

import org.serratec.backend.entity.Pedido;
import org.serratec.backend.enums.StatusPedidoEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public record PedidoResponseDTO(Long idPedido, LocalDate dataPedido, StatusPedidoEnum status, ClienteResumoDTO cliente,List<ItemPedidoResponseDTO> itens, BigDecimal valorTotalPedido) {
	
	public record ClienteResumoDTO(Long idCliente, String nomeCliente, String emailCliente) {
	}

	public PedidoResponseDTO(Pedido pedido, BigDecimal valorTotalCalculado) {
		this(pedido.getId(), pedido.getDataPedido(), pedido.getStatusPedidoEnum(),
				(pedido.getCliente() != null)
						? new ClienteResumoDTO(pedido.getCliente().getId(), pedido.getCliente().getNome(),
								pedido.getCliente().getEmail())
						: null,
				(pedido.getPedidoProduto() != null) ? pedido.getPedidoProduto().stream().map(ItemPedidoResponseDTO::new)
						.collect(Collectors.toList()) : List.of(),
				valorTotalCalculado);
	}

	public PedidoResponseDTO(Pedido pedido) {
		this(pedido.getId(), pedido.getDataPedido(), pedido.getStatusPedidoEnum(),
				(pedido.getCliente() != null)
						? new ClienteResumoDTO(pedido.getCliente().getId(), pedido.getCliente().getNome(),
								pedido.getCliente().getEmail())
						: null,
				(pedido.getPedidoProduto() != null) ? pedido.getPedidoProduto().stream().map(ItemPedidoResponseDTO::new)
						.collect(Collectors.toList()) : List.of(),
				(pedido.getValorPedido() != null) ? BigDecimal.valueOf(pedido.getValorPedido()) : BigDecimal.ZERO);
	}
}