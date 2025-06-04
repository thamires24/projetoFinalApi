package org.serratec.backend.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Cliente cliente;

    @ManyToOne
    @JoinColumn
    private Produto produto;

    private Integer quantidadeDesejada;

    public Wishlist() {
    }

    public Wishlist(Cliente cliente, Produto produto, Integer quantidadeDesejada) {
        this.cliente = cliente;
        this.produto = produto;
        this.quantidadeDesejada = (quantidadeDesejada != null && quantidadeDesejada > 0) ? quantidadeDesejada : 1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidadeDesejada() {
        return quantidadeDesejada;
    }

    public void setQuantidadeDesejada(Integer quantidadeDesejada) {
        this.quantidadeDesejada = (quantidadeDesejada != null && quantidadeDesejada > 0) ? quantidadeDesejada : 1;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cliente, produto);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Wishlist other = (Wishlist) obj;
        return Objects.equals(id, other.id) && Objects.equals(cliente, other.cliente)
                && Objects.equals(produto, other.produto);
    }
}