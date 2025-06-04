package org.serratec.backend.controller;
import org.serratec.backend.dto.PedidoAgendadoDTO;
import org.serratec.backend.enums.StatusEntrega;

import org.serratec.backend.dto.PedidoAgendadoDTO;
import org.serratec.backend.enums.StatusEntrega;
import org.serratec.backend.entity.Pedido;
import org.serratec.backend.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos-agendados")
public class PedidoAgendadoController {

    @Autowired	
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Pedido> criar(@RequestBody PedidoAgendadoDTO dto) throws Exception {
        Pedido pedido = pedidoService.criarPedidoAgendado(dto);
        return ResponseEntity.ok(pedido);
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listar() {
        return ResponseEntity.ok(pedidoService.listarTodosAgendados());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestParam StatusEntrega status) throws Exception {
        pedidoService.atualizarStatusEntrega(id, status);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws Exception {
        pedidoService.deletarPedido(id);
        return ResponseEntity.noContent().build();
    }
}
