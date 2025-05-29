package org.serratec.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.serratec.backend.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
