package org.serratec.backend.repository;

import java.util.Optional;

import org.serratec.backend.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	Optional<Cliente> findByCpf(String cpf);
}