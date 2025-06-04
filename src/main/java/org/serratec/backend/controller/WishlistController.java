package org.serratec.backend.controller;

import java.util.List;

import org.serratec.backend.dto.WishlistRequestDTO;
import org.serratec.backend.dto.WishlistResponseDTO;
import org.serratec.backend.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @PostMapping("/itens")
    public ResponseEntity<WishlistResponseDTO> adicionarItem(
            @Valid @RequestBody WishlistRequestDTO requestDTO) {
        WishlistResponseDTO itemAdicionado = wishlistService.adicionarItem(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemAdicionado);
    }

    @GetMapping("/meus-itens")
    public ResponseEntity<List<WishlistResponseDTO>> listarMeusItens() {
        return ResponseEntity.ok(wishlistService.listarItensDoClienteAutenticado());
    }

    @PutMapping("/itens/{idWishlistItem}")
    public ResponseEntity<WishlistResponseDTO> atualizarItem(
            @PathVariable Long idWishlistItem,
            @RequestParam Integer novaQuantidadeDesejada) {
        WishlistResponseDTO itemAtualizado = wishlistService.atualizarItem(idWishlistItem, novaQuantidadeDesejada);
        return ResponseEntity.ok(itemAtualizado);
    }

    @DeleteMapping("/itens/{idWishlistItem}")
    public ResponseEntity<Void> removerItem(@PathVariable Long idWishlistItem) {
        wishlistService.removerItem(idWishlistItem);
        return ResponseEntity.noContent().build();
    }
}