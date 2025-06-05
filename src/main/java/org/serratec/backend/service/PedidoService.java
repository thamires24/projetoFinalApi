package org.serratec.backend.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.serratec.backend.dto.ItemPedidoRequestDTO;
import org.serratec.backend.dto.PedidoRequestDTO;
import org.serratec.backend.dto.PedidoResponseDTO;
import org.serratec.backend.entity.Cliente;
import org.serratec.backend.entity.Pedido;
import org.serratec.backend.entity.PedidoProduto;
import org.serratec.backend.entity.Produto;
import org.serratec.backend.enums.StatusPedidoEnum;
import org.serratec.backend.exception.ClienteException;
import org.serratec.backend.exception.PedidoException;
import org.serratec.backend.exception.ProdutoException;
import org.serratec.backend.repository.ClienteRepository;
import org.serratec.backend.repository.PedidoRepository;
import org.serratec.backend.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	private Cliente getClienteAutenticado() throws ClienteException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()
				|| "anonymousUser".equals(authentication.getPrincipal())) {
			throw new ClienteException("Nenhum cliente autenticado para realizar a operação.");
		}
		String emailClienteAutenticado = authentication.getName(); 
		return clienteRepository.findByEmail(emailClienteAutenticado).orElseThrow(
				() -> new ClienteException("Cliente autenticado não encontrado com email: " + emailClienteAutenticado));
	}

	@Transactional
	public PedidoResponseDTO criarPedido(PedidoRequestDTO pedidoRequestDTO)
			throws PedidoException, ClienteException, ProdutoException {

		Cliente cliente = getClienteAutenticado();

		Pedido pedido = new Pedido();
		pedido.setCliente(cliente);
		pedido.setDataPedido(LocalDate.now());
		pedido.setStatusPedidoEnum(StatusPedidoEnum.REALIZADO);

		List<PedidoProduto> itensPedidoEntities = new ArrayList<>();
		BigDecimal valorTotalCalculado = BigDecimal.ZERO;

		if (pedidoRequestDTO.getItens() == null || pedidoRequestDTO.getItens().isEmpty()) {
			throw new PedidoException("O pedido deve conter pelo menos um item.");
		}

		for (ItemPedidoRequestDTO itemDto : pedidoRequestDTO.getItens()) {
			Produto produto = produtoRepository.findById(itemDto.getIdProduto()).orElseThrow(
					() -> new ProdutoException("Produto com ID " + itemDto.getIdProduto() + " não encontrado."));

			BigDecimal precoVenda = (produto.getPreco() != null) ? BigDecimal.valueOf(produto.getPreco())
					: BigDecimal.ZERO;
			BigDecimal descontoItem = (itemDto.getDesconto() != null) ? itemDto.getDesconto() : BigDecimal.ZERO;

			if (precoVenda.compareTo(BigDecimal.ZERO) <= 0) {
				throw new PedidoException(
						"Produto '" + produto.getNomeProduto() + "' está com preço zerado ou inválido no catálogo.");
			}
			if (descontoItem.compareTo(precoVenda) > 0) {
				throw new PedidoException("Desconto (" + descontoItem + ") não pode ser maior que o preço de venda ("
						+ precoVenda + ") do produto '" + produto.getNomeProduto() + "'.");
			}
			if (itemDto.getQuantidade() == null || itemDto.getQuantidade() <= 0) {
				throw new PedidoException(
						"Quantidade para o produto '" + produto.getNomeProduto() + "' deve ser positiva.");
			}

			PedidoProduto pedidoProduto = new PedidoProduto(pedido, produto, itemDto.getQuantidade(), precoVenda,
					descontoItem);
			itensPedidoEntities.add(pedidoProduto);
			valorTotalCalculado = valorTotalCalculado.add(pedidoProduto.calcularSubtotal()); 
		}

		pedido.setPedidoProduto(itensPedidoEntities);
		pedido.setValorPedido(valorTotalCalculado.doubleValue());

		Pedido pedidoSalvo = pedidoRepository.save(pedido); 
		return new PedidoResponseDTO(pedidoSalvo, valorTotalCalculado);
	}

	@Transactional(readOnly = true)
	public PedidoResponseDTO buscarPorId(Long id) throws PedidoException {
		Pedido pedido = pedidoRepository.findById(id)
				.orElseThrow(() -> new PedidoException("Pedido com ID " + id + " não encontrado."));

		BigDecimal valorTotalCalculado = BigDecimal.ZERO;
		if (pedido.getPedidoProduto() != null) {
			for (PedidoProduto item : pedido.getPedidoProduto()) {
				valorTotalCalculado = valorTotalCalculado.add(item.calcularSubtotal());
			}
		}
		return new PedidoResponseDTO(pedido, valorTotalCalculado);
	}

	@Transactional
	public List<PedidoResponseDTO> listarPedidosDoClienteAutenticado() throws ClienteException {
		Cliente cliente = getClienteAutenticado();
		List<Pedido> pedidos = pedidoRepository.findByCliente(cliente);

		return pedidos.stream().map(pedido -> {
			BigDecimal valorTotalCalculado = BigDecimal.ZERO;
			if (pedido.getPedidoProduto() != null) {
				for (PedidoProduto item : pedido.getPedidoProduto()) {
					valorTotalCalculado = valorTotalCalculado.add(item.calcularSubtotal());
				}
			}
			return new PedidoResponseDTO(pedido, valorTotalCalculado);
		}).collect(Collectors.toList());
	}

	@Transactional
	public PedidoResponseDTO atualizarStatusPedido(Long idPedido, StatusPedidoEnum novoStatus)
			throws PedidoException, ClienteException {

		Pedido pedido = pedidoRepository.findById(idPedido).orElseThrow(
				() -> new PedidoException("Pedido com ID " + idPedido + " não encontrado para atualização de status."));

		pedido.setStatusPedidoEnum(novoStatus);
		Pedido pedidoAtualizado = pedidoRepository.save(pedido);

		BigDecimal valorTotalCalculado = BigDecimal.ZERO;
		if (pedidoAtualizado.getPedidoProduto() != null) {
			for (PedidoProduto item : pedidoAtualizado.getPedidoProduto()) {
				valorTotalCalculado = valorTotalCalculado.add(item.calcularSubtotal());
			}
		}
		return new PedidoResponseDTO(pedidoAtualizado, valorTotalCalculado);
	}

}