package org.serratec.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.serratec.backend.dto.WishlistRequestDTO;
import org.serratec.backend.dto.WishlistResponseDTO;
import org.serratec.backend.entity.Cliente;
import org.serratec.backend.entity.Produto;
import org.serratec.backend.entity.Wishlist;
import org.serratec.backend.exception.ClienteException;
import org.serratec.backend.exception.ProdutoException;
import org.serratec.backend.exception.WishlistException;
import org.serratec.backend.repository.ClienteRepository;
import org.serratec.backend.repository.ProdutoRepository;
import org.serratec.backend.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishlistService {

	@Autowired
	private WishlistRepository wishlistRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	private Cliente getClienteAutenticado() throws ClienteException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String emailClienteAutenticado = authentication.getName();
		return clienteRepository.findByEmail(emailClienteAutenticado).orElseThrow(
				() -> new ClienteException("Cliente autenticado não encontrado: " + emailClienteAutenticado));
	}

	@Transactional
	public WishlistResponseDTO adicionarItem(WishlistRequestDTO requestDTO)
			throws ClienteException, ProdutoException, WishlistException {
		Cliente cliente = getClienteAutenticado();
		Produto produto = produtoRepository.findById(requestDTO.getIdProduto()).orElseThrow(
				() -> new ProdutoException("Produto com ID " + requestDTO.getIdProduto() + " não encontrado."));

		Wishlist wishlistItem = new Wishlist(cliente, produto, requestDTO.getQuantidadeDesejada());
		Wishlist itemSalvo = wishlistRepository.save(wishlistItem);
		return new WishlistResponseDTO(itemSalvo);
	}

	@Transactional(readOnly = true)
	public List<WishlistResponseDTO> listarItensDoClienteAutenticado() throws ClienteException {
		Cliente cliente = getClienteAutenticado();
		return wishlistRepository.findByClienteId(cliente.getId()).stream().map(WishlistResponseDTO::new)
				.collect(Collectors.toList());
	}

	@Transactional
	public WishlistResponseDTO atualizarItem(Long idWishlistItem, Integer novaQuantidadeDesejada)
			throws WishlistException, ClienteException {
		Cliente cliente = getClienteAutenticado();
		Wishlist item = wishlistRepository.findById(idWishlistItem).orElseThrow(
				() -> new WishlistException("Item da Wishlist com ID " + idWishlistItem + " não encontrado."));

		if (!item.getCliente().getId().equals(cliente.getId())) {
			throw new WishlistException("Você não tem permissão para atualizar este item da wishlist.");
		}

		if (novaQuantidadeDesejada == null || novaQuantidadeDesejada <= 0) {
			throw new WishlistException("Quantidade desejada deve ser positiva.");
		}
		item.setQuantidadeDesejada(novaQuantidadeDesejada);
		Wishlist itemAtualizado = wishlistRepository.save(item);
		return new WishlistResponseDTO(itemAtualizado);
	}

	@Transactional
	public void removerItem(Long idWishlistItem) throws WishlistException, ClienteException {
		Cliente cliente = getClienteAutenticado();
		Wishlist item = wishlistRepository.findById(idWishlistItem).orElseThrow(
				() -> new WishlistException("Item da Wishlist com ID " + idWishlistItem + " não encontrado."));

		if (!item.getCliente().getId().equals(cliente.getId())) {
			throw new WishlistException("Você não tem permissão para remover este item da wishlist.");
		}
		wishlistRepository.deleteById(idWishlistItem);
	}
}