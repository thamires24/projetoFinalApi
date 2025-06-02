package org.serratec.backend.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.serratec.backend.entity.Cliente;
import org.serratec.backend.entity.Pedido;
import org.serratec.backend.entity.PedidoProduto;

import jakarta.validation.constraints.NotNull;

public class PedidoRequestDTO {

	   @NotNull
	    private LocalDate dataPedido;

	    private LocalDate dataEntrega;
	    
	    private Double valorPedido;

	    @NotNull
	    private Cliente cliente;

	    private List<PedidoProduto> pedidosProdutos = new ArrayList<>();

	    public PedidoRequestDTO(Pedido pedido) {
	        this.dataPedido = pedido.getDataPedido();
	        this.valorPedido = pedido.getValorPedido();
	        this.cliente = pedido.getCliente();
	    }

		public LocalDate getDataPedido() {
			return dataPedido;
		}

		public void setDataPedido(LocalDate dataPedido) {
			this.dataPedido = dataPedido;
		}

		public LocalDate getDataEntrega() {
			return dataEntrega;
		}

		public void setDataEntrega(LocalDate dataEntrega) {
			this.dataEntrega = dataEntrega;
		}

		public Double getValorPedido() {
			return valorPedido;
		}

		public void setValorPedido(Double valorPedido) {
			this.valorPedido = valorPedido;
		}

		public Cliente getCliente() {
			return cliente;
		}

		public void setCliente(Cliente cliente) {
			this.cliente = cliente;
		}

		public List<PedidoProduto> getPedidosProdutos() {
			return pedidosProdutos;
		}

		public void setPedidosProdutos(List<PedidoProduto> pedidosProdutos) {
			this.pedidosProdutos = pedidosProdutos;
		}
	    
	    
}
