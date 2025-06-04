package org.serratec.backend.repository;

import java.util.List;
import org.serratec.backend.entity.Cliente;
import org.serratec.backend.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByCliente(Cliente cliente);

}