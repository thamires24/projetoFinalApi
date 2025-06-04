package org.serratec.backend.dto;

import org.serratec.backend.entity.Wishlist;

public record WishlistResponseDTO(Long idWishlistItem, Long idCliente, ProdutoResumoDTO produto,
		Integer quantidadeDesejada) {
	public record ProdutoResumoDTO(Long idProduto, String nomeProduto) {
	}

	public WishlistResponseDTO(Wishlist wishlistItem) {
		this(wishlistItem.getId(), (wishlistItem.getCliente() != null) ? wishlistItem.getCliente().getId() : null,
				(wishlistItem.getProduto() != null) ? new ProdutoResumoDTO(wishlistItem.getProduto().getId(),
						wishlistItem.getProduto().getNomeProduto()) : null,
				wishlistItem.getQuantidadeDesejada());
	}
}