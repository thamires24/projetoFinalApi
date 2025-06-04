package org.serratec.backend.dto;

import org.serratec.backend.entity.PedidoProduto;
import java.math.BigDecimal;

public record ItemPedidoResponseDTO(Long idItemPedido,Long idProduto, String nomeProduto, Integer quantidade, BigDecimal precoVendaUnitario, BigDecimal descontoItem,
		BigDecimal subtotalItem) {
	
	public ItemPedidoResponseDTO(PedidoProduto item) {
		this(item.getId(), item.getProduto().getId(), item.getProduto().getNomeProduto(), item.getQuantidadeProduto(),
				item.getPrecoVenda(), item.getDesconto(), item.calcularSubtotal() 
		);
	}
}