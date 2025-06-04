package org.serratec.backend.entity;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_categoria", nullable = false, length = 50, unique = true) // Exemplo de detalhamento da coluna
    private String nomeCategoria;

    @OneToMany(mappedBy = "categoria") 
    @JsonManagedReference  
    private List<Produto> produtos;

    public Categoria() {
    }

    public Categoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomeCategoria);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Categoria other = (Categoria) obj;
        return Objects.equals(id, other.id) && Objects.equals(nomeCategoria, other.nomeCategoria);
    }
}