package org.serratec.backend.entity;

import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class PedidoProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantidadeProduto;
    private BigDecimal precoVenda;
    private BigDecimal desconto;

    @ManyToOne
    @JoinColumn
    private Pedido pedido;

    @ManyToOne
    @JoinColumn
    private Produto produto;

    public PedidoProduto() {
    }

    public PedidoProduto(Pedido pedido, Produto produto, Integer quantidadeProduto, BigDecimal precoVenda, BigDecimal desconto) {
        this.pedido = pedido;
        this.produto = produto;
        this.quantidadeProduto = quantidadeProduto;
        this.precoVenda = precoVenda;
        this.desconto = (desconto != null) ? desconto : BigDecimal.ZERO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantidadeProduto() {
        return quantidadeProduto;
    }

    public void setQuantidadeProduto(Integer quantidadeProduto) {
        this.quantidadeProduto = quantidadeProduto;
    }

    public BigDecimal getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(BigDecimal precoVenda) {
        this.precoVenda = precoVenda;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = (desconto != null) ? desconto : BigDecimal.ZERO;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public BigDecimal calcularSubtotal() {
        if (precoVenda == null || quantidadeProduto == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal precoComDesconto = precoVenda.subtract(getDesconto());
        return precoComDesconto.multiply(new BigDecimal(quantidadeProduto));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pedido, produto);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PedidoProduto other = (PedidoProduto) obj;
        return Objects.equals(id, other.id) && Objects.equals(pedido, other.pedido)
                && Objects.equals(produto, other.produto);
    }
}