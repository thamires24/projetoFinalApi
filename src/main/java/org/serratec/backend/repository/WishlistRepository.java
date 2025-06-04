package org.serratec.backend.repository;

import java.util.List;
import org.serratec.backend.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByClienteId(Long clienteId);
}