package org.serratec.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class ClientePerfil {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;

	@OneToOne
	private Cliente cliente;

	public ClientePerfil() {
	}

	public ClientePerfil(Cliente cliente, ClientePerfil perfil) {
		this.id = perfil.getId();
		this.nome = cliente.getNome();
		this.setClientePerfil(cliente);
	}

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

	public Cliente getCliente() {
		return cliente;
	}

	public void setClientePerfil(Cliente cliente) {
		this.cliente = cliente;
	}

}