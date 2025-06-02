package org.serratec.backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.serratec.backend.entity.Cliente;
import org.serratec.backend.entity.Pedido;
import org.serratec.backend.entity.PedidoProduto;
import org.serratec.backend.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PedidoProdutoService pedidoProdutoService;

    
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    
    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }

   
    public Pedido criarPedido(Long clienteId, Pedido pedido) throws Exception {
        Optional<Cliente> clienteOpt = clienteService.buscarPorId(clienteId);
        if (!clienteOpt.isPresent()) {
            throw new Exception("Cliente n達o encontrado com id: " + clienteId);
        }
        pedido.setCliente(clienteOpt.get());
        pedido.setValorPedido(BigDecimal.ZERO.doubleValue()); 

        
        if (pedido.getDataPedido() == null) {
            pedido.setDataPedido(java.time.LocalDate.now());
        }

        pedido.setPedidoProduto(null); 

        return pedidoRepository.save(pedido);
    }

    
    public Pedido atualizarPedido(Long pedidoId, Pedido pedidoAtualizado) throws Exception {
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(pedidoId);
        if (!pedidoOpt.isPresent()) {
            throw new Exception("Pedido n達o encontrado com id: " + pedidoId);
        }
        Pedido pedido = pedidoOpt.get();

       
        pedido.setStatusPedidoEnum(pedidoAtualizado.getStatusPedidoEnum());
        pedido.setDataPedido(pedidoAtualizado.getDataPedido());

       

        return pedidoRepository.save(pedido);
    }

    
    public void deletarPedido(Long pedidoId) throws Exception {
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(pedidoId);
        if (!pedidoOpt.isPresent()) {
            throw new Exception("Pedido n達o encontrado com id: " + pedidoId);
        }
        pedidoRepository.deleteById(pedidoId);
    }

   
    public BigDecimal calcularValorTotalPedido(Long pedidoId) throws Exception {
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(pedidoId);
        if (!pedidoOpt.isPresent()) {
            throw new Exception("Pedido n達o encontrado com id: " + pedidoId);
        }
        Pedido pedido = pedidoOpt.get();

        List<PedidoProduto> itens = pedido.getPedidoProduto();
        if (itens == null || itens.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal total = BigDecimal.ZERO;
        for (PedidoProduto item : itens) {
            total = total.add(item.calcularValorTotal());
        }

        
        pedido.setValorPedido(total.doubleValue());
        pedidoRepository.save(pedido);

        return total;
    }
}