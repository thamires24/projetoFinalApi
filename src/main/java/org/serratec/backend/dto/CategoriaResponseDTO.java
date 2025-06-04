package org.serratec.backend.dto;

import org.serratec.backend.entity.Categoria;

public record CategoriaResponseDTO(Long id, String nomeCategoria) {
	public CategoriaResponseDTO(Categoria categoria) {
		this(categoria.getId(), categoria.getNomeCategoria());
	}
}