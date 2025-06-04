package org.serratec.backend.repository;

import java.util.Optional;
import org.serratec.backend.entity.Foto;
import org.serratec.backend.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FotoRepository extends JpaRepository<Foto, Long> {
    Optional<Foto> findByProduto(Produto produto);

}