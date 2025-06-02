package org.serratec.backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.serratec.backend.entity.Cliente;
import org.serratec.backend.entity.Pedido;
import org.serratec.backend.entity.PedidoProduto;
import org.serratec.backend.repository.PedidoProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class PedidoProdutoService {

    @Autowired
    private PedidoProdutoRepository pedidoProdutoRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    @Lazy
    private PedidoService pedidoService;

  // Aqui nos consegumos listar os itens dos pedidos, ligar o pedido ao cliente
   // Conseguimos validar o preço do item e a quantidade que tem no peidido 
    // conseguimos buscar itens e fazer as remoções
    
    public Optional<PedidoProduto> listarItensPorPedido(Long clienteId, Long pedidoId) throws Exception {
      
        Optional<Cliente> clienteOpt = clienteService.buscarPorId(clienteId);
        if (!clienteOpt.isPresent()) {
            throw new Exception("Cliente não encontrado com id: " + clienteId);
        }

      
        Optional<Pedido> pedidoOpt = pedidoService.buscarPorId(pedidoId);
        if (!pedidoOpt.isPresent()) {
            throw new Exception("Pedido não encontrado com id: " + pedidoId);
        }

        Pedido pedido = pedidoOpt.get();
        if (!pedido.getCliente().getId().equals(clienteId)) {
            throw new Exception("Pedido não pertence ao cliente informado.");
        }

        return pedidoProdutoRepository.findById(pedidoId);
    }

    
    public PedidoProduto adicionarItem(Long clienteId, Long pedidoId, PedidoProduto pedidoProduto) throws Exception {
      
        Optional<Cliente> clienteOpt = clienteService.buscarPorId(clienteId);
        if (!clienteOpt.isPresent()) {
            throw new Exception("Cliente não encontrado com id: " + clienteId);
        }

        Optional<Pedido> pedidoOpt = pedidoService.buscarPorId(pedidoId);
        if (!pedidoOpt.isPresent()) {
            throw new Exception("Pedido não encontrado com id: " + pedidoId);
        }

        Pedido pedido = pedidoOpt.get();
        if (!pedido.getCliente().getId().equals(clienteId)) {
            throw new Exception("Pedido não pertence ao cliente informado.");
        }

      
        if (pedidoProduto.getQuantidadeProduto() == null || pedidoProduto.getQuantidadeProduto() <= 0) {
            throw new Exception("Quantidade do produto deve ser maior que zero.");
        }
        if (pedidoProduto.getPreco() == null || pedidoProduto.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("Preço do produto deve ser maior que zero.");
        }

      
        pedidoProduto.setPedido(pedido);

        return pedidoProdutoRepository.save(pedidoProduto);
    }

 
    public PedidoProduto atualizarItem(Long clienteId, Long pedidoId, Long pedidoProdutoId, PedidoProduto pedidoProdutoAtualizado) throws Exception {
      
        Optional<Cliente> clienteOpt = clienteService.buscarPorId(clienteId);
        if (!clienteOpt.isPresent()) {
            throw new Exception("Cliente não encontrado com id: " + clienteId);
        }

        Optional<Pedido> pedidoOpt = pedidoService.buscarPorId(pedidoId);
        if (!pedidoOpt.isPresent()) {
            throw new Exception("Pedido não encontrado com id: " + pedidoId);
        }

        Pedido pedido = pedidoOpt.get();
        if (!pedido.getCliente().getId().equals(clienteId)) {
            throw new Exception("Pedido não pertence ao cliente informado.");
        }

       
        Optional<PedidoProduto> pedidoProdutoOpt = pedidoProdutoRepository.findById(pedidoProdutoId);
        if (!pedidoProdutoOpt.isPresent()) {
            throw new Exception("Item do pedido não encontrado com id: " + pedidoProdutoId);
        }

        PedidoProduto pedidoProduto = pedidoProdutoOpt.get();

      
        if (!pedidoProduto.getPedido().getId().equals(pedidoId)) {
            throw new Exception("Item do pedido não pertence ao pedido informado.");
        }

      
        if (pedidoProdutoAtualizado.getQuantidadeProduto() != null && pedidoProdutoAtualizado.getQuantidadeProduto() > 0) {
            pedidoProduto.setQuantidadeProduto(pedidoProdutoAtualizado.getQuantidadeProduto());
        } else {
            throw new Exception("Quantidade do produto deve ser maior que zero.");
        }

        if (pedidoProdutoAtualizado.getPreco() != null && pedidoProdutoAtualizado.getPreco().compareTo(BigDecimal.ZERO) > 0) {
            pedidoProduto.setPreco(pedidoProdutoAtualizado.getPreco());
        } else {
            throw new Exception("Preço do produto deve ser maior que zero.");
        }

        if (pedidoProdutoAtualizado.getDesconto() != null) {
            pedidoProduto.setDesconto(pedidoProdutoAtualizado.getDesconto());
        } else {
            pedidoProduto.setDesconto(BigDecimal.ZERO);
        }

        return pedidoProdutoRepository.save(pedidoProduto);
    }

   
    public void removerItem(Long clienteId, Long pedidoId, Long pedidoProdutoId) throws Exception {
    
        Optional<Cliente> clienteOpt = clienteService.buscarPorId(clienteId);
        if (!clienteOpt.isPresent()) {
            throw new Exception("Cliente não encontrado com id: " + clienteId);
        }

        Optional<Pedido> pedidoOpt = pedidoService.buscarPorId(pedidoId);
        if (!pedidoOpt.isPresent()) {
            throw new Exception("Pedido não encontrado com id: " + pedidoId);
        }

        Pedido pedido = pedidoOpt.get();
        if (!pedido.getCliente().getId().equals(clienteId)) {
            throw new Exception("Pedido não pertence ao cliente informado.");
        }

        Optional<PedidoProduto> pedidoProdutoOpt = pedidoProdutoRepository.findById(pedidoProdutoId);
        if (!pedidoProdutoOpt.isPresent()) {
            throw new Exception("Item do pedido não encontrado com id: " + pedidoProdutoId);
        }

        PedidoProduto pedidoProduto = pedidoProdutoOpt.get();

        if (!pedidoProduto.getPedido().getId().equals(pedidoId)) {
            throw new Exception("Item do pedido não pertence ao pedido informado.");
        }

        pedidoProdutoRepository.deleteById(pedidoProdutoId);
    }

  
    public BigDecimal calcularValorTotalItem(Long pedidoProdutoId) throws Exception {
        Optional<PedidoProduto> pedidoProdutoOpt = pedidoProdutoRepository.findById(pedidoProdutoId);
        if (!pedidoProdutoOpt.isPresent()) {
            throw new Exception("Item do pedido não encontrado com id: " + pedidoProdutoId);
        }
        return pedidoProdutoOpt.get().calcularValorTotal();
    }
}