package org.serratec.backend.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.serratec.backend.enums.StatusPedidoEnum;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataPedido;
    private Double valorPedido; 
    @Enumerated(EnumType.STRING)
    private StatusPedidoEnum statusPedidoEnum;

    @ManyToOne
    @JoinColumn
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
                          
    private List<PedidoProduto> pedidoProduto;

    public Pedido() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

    public Double getValorPedido() {
        return valorPedido;
    }

    public void setValorPedido(Double valorPedido) {
        this.valorPedido = valorPedido;
    }

    public StatusPedidoEnum getStatusPedidoEnum() {
        return statusPedidoEnum;
    }

    public void setStatusPedidoEnum(StatusPedidoEnum statusPedidoEnum) {
        this.statusPedidoEnum = statusPedidoEnum;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<PedidoProduto> getPedidoProduto() {
        return pedidoProduto;
    }

    public void setPedidoProduto(List<PedidoProduto> pedidoProduto) {
        this.pedidoProduto = pedidoProduto;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dataPedido, cliente);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pedido other = (Pedido) obj;
        return Objects.equals(id, other.id) && Objects.equals(dataPedido, other.dataPedido)
                && Objects.equals(cliente, other.cliente);
    }
}