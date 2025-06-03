package org.serratec.backend.repository;

import java.util.List;

import org.serratec.backend.entity.Devolucao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DevolucaoRepository extends JpaRepository<Devolucao, Long> {
    List<Devolucao> findByClienteId(Long clienteId);
}
