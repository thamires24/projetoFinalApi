package org.serratec.backend.entity;

import java.util.Objects;
import java.util.Set; 

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ClientePerfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;



    public ClientePerfil() {
    }

    public ClientePerfil(String nome) {
        this.nome = nome;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }



    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ClientePerfil other = (ClientePerfil) obj;
        return Objects.equals(id, other.id) && Objects.equals(nome, other.nome);
    }
}