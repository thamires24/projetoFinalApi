package org.serratec.backend.entity;

import java.util.Arrays;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;

@Entity
public class Foto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Lob
	private byte[] dados;

	private String tipo; 
	private String nome; 

	@OneToOne
	@JoinColumn 
	private Produto produto;

	public Foto() {
	}

	public Foto(byte[] dados, String tipo, String nome, Produto produto) {
		this.dados = dados;
		this.tipo = tipo;
		this.nome = nome;
		this.produto = produto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getDados() {
		return dados;
	}

	public void setDados(byte[] dados) {
		this.dados = dados;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	// hashCode e equals
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(dados); 
		result = prime * result + Objects.hash(id, nome, produto, tipo);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Foto other = (Foto) obj;
		return Arrays.equals(dados, other.dados) && Objects.equals(id, other.id) && Objects.equals(nome, other.nome)
				&& Objects.equals(produto, other.produto) && Objects.equals(tipo, other.tipo);
	}
}