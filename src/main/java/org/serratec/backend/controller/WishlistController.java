package org.serratec.backend.controller;

import java.util.List;

import org.serratec.backend.entity.Wishlist;
import org.serratec.backend.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Wishlist>> listarPorCliente(@PathVariable Long clienteId) {
        try {
            List<Wishlist> lista = wishlistService.listarPorCliente(clienteId);
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/cliente/{clienteId}/produto/{produtoId}")
    public ResponseEntity<Wishlist> adicionarItem(
            @PathVariable Long clienteId,
            @PathVariable Long produtoId,
            @RequestParam(required = false) Integer quantidadeDesejada) {
        try {
            Wishlist wishlist = wishlistService.adicionarItem(clienteId, produtoId, quantidadeDesejada);
            return ResponseEntity.ok(wishlist);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Wishlist> atualizarItem(
            @PathVariable Long id,
            @RequestParam Integer quantidadeDesejada) {
        try {
            Wishlist wishlist = wishlistService.atualizarItem(id, quantidadeDesejada);
            return ResponseEntity.ok(wishlist);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerItem(@PathVariable Long id) {
        try {
            wishlistService.removerItem(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
