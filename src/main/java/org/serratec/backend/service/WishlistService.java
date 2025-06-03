package org.serratec.backend.service;

import java.util.List;
import java.util.Optional;

import org.serratec.backend.entity.Cliente;
import org.serratec.backend.entity.Produto;
import org.serratec.backend.entity.Wishlist;
import org.serratec.backend.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProdutoService produtoService;

    public List<Wishlist> listarPorCliente(Long clienteId) throws Exception {
        Optional<Cliente> clienteOpt = clienteService.buscarPorId(clienteId);
        if (!clienteOpt.isPresent()) {
            throw new Exception("Cliente não encontrado com id: " + clienteId);
        }
        return wishlistRepository.findByClienteId(clienteId);
    }

    public Wishlist adicionarItem(Long clienteId, Long produtoId, Integer quantidadeDesejada) throws Exception {
        Optional<Cliente> clienteOpt = clienteService.buscarPorId(clienteId);
        if (!clienteOpt.isPresent()) {
            throw new Exception("Cliente não encontrado com id: " + clienteId);
        }

        Optional<Produto> produtoOpt = produtoService.buscarPorId(produtoId);
        if (!produtoOpt.isPresent()) {
            throw new Exception("Produto não encontrado com id: " + produtoId);
        }

        Wishlist wishlist = new Wishlist();
        wishlist.setCliente(clienteOpt.get());
        wishlist.setProduto(produtoOpt.get());
        wishlist.setQuantidadeDesejada(quantidadeDesejada != null ? quantidadeDesejada : 1);

        return wishlistRepository.save(wishlist);
    }

    public Wishlist atualizarItem(Long id, Integer quantidadeDesejada) throws Exception {
        Optional<Wishlist> wishlistOpt = wishlistRepository.findById(id);
        if (!wishlistOpt.isPresent()) {
            throw new Exception("Item da wishlist não encontrado com id: " + id);
        }

        Wishlist wishlist = wishlistOpt.get();
        wishlist.setQuantidadeDesejada(quantidadeDesejada);

        return wishlistRepository.save(wishlist);
    }

    public void removerItem(Long id) throws Exception {
        Optional<Wishlist> wishlistOpt = wishlistRepository.findById(id);
        if (!wishlistOpt.isPresent()) {
            throw new Exception("Item da wishlist não encontrado com id: " + id);
        }
        wishlistRepository.deleteById(id);
    }
}
