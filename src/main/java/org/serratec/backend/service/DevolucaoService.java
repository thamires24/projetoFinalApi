package org.serratec.backend.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.serratec.backend.dto.DevolucaoRequestDTO;
import org.serratec.backend.dto.DevolucaoResponseDTO;
import org.serratec.backend.entity.Cliente;
import org.serratec.backend.entity.Devolucao;
import org.serratec.backend.entity.Pedido;
import org.serratec.backend.entity.Produto;
import org.serratec.backend.exception.ClienteException;
import org.serratec.backend.exception.DevolucaoException;
import org.serratec.backend.exception.PedidoException;
import org.serratec.backend.exception.ProdutoException;
import org.serratec.backend.repository.ClienteRepository;
import org.serratec.backend.repository.DevolucaoRepository;
import org.serratec.backend.repository.PedidoRepository;
import org.serratec.backend.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DevolucaoService {

	@Autowired
	private DevolucaoRepository devolucaoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Transactional
	public DevolucaoResponseDTO registrarNovaDevolucao(DevolucaoRequestDTO requestDTO)
			throws ClienteException, ProdutoException, DevolucaoException, PedidoException {

		Cliente cliente = clienteRepository.findById(requestDTO.getIdCliente())
				.orElseThrow(() -> new ClienteException("Cliente não encontrado com ID: " + requestDTO.getIdCliente()));

		Pedido pedidoOriginal = pedidoRepository.findById(requestDTO.getIdPedidoOriginal())
				.orElseThrow(() -> new PedidoException(
						"Pedido original com ID " + requestDTO.getIdPedidoOriginal() + " não encontrado."));

		if (!pedidoOriginal.getCliente().getId().equals(cliente.getId())) {
			throw new DevolucaoException("O pedido original não pertence ao cliente informado.");
		}

		if (requestDTO.getIdProdutosDevolvidos() == null || requestDTO.getIdProdutosDevolvidos().isEmpty()) {
			throw new DevolucaoException("A lista de produtos para devolução não pode ser vazia.");
		}

		Devolucao devolucao = new Devolucao();
		devolucao.setCliente(cliente);
		devolucao.setMotivo(requestDTO.getMotivo());
		devolucao.setDataSolicitacao(LocalDate.now());
		devolucao.setStatus("SOLICITADA");

		List<Produto> produtosParaDevolucao = new ArrayList<>();
		for (Long idProduto : requestDTO.getIdProdutosDevolvidos()) {
			Produto produto = produtoRepository.findById(idProduto)
					.orElseThrow(() -> new ProdutoException("Produto com ID: " + idProduto + " não encontrado."));

			produtosParaDevolucao.add(produto);
		}

		Devolucao devolucaoSalva = devolucaoRepository.save(devolucao);
		for (Produto produto : produtosParaDevolucao) {
			if (produto.getDevolucao() != null && !produto.getDevolucao().getId().equals(devolucaoSalva.getId())) {
				throw new DevolucaoException("Produto '" + produto.getNomeProduto() + "' já está em outra devolução.");
			}
			produto.setDevolucao(devolucaoSalva);
			produtoRepository.save(produto);
		}

		return new DevolucaoResponseDTO(devolucaoSalva, pedidoOriginal.getId());
	}

	@Transactional(readOnly = true)
	public List<DevolucaoResponseDTO> listarDevolucoesPorCliente(Long idCliente) throws ClienteException {
		if (!clienteRepository.existsById(idCliente)) {
			throw new ClienteException("Cliente com ID " + idCliente + " não encontrado.");
		}
		List<Devolucao> devolucoes = devolucaoRepository.findByClienteId(idCliente);
		return devolucoes.stream().map(d -> new DevolucaoResponseDTO(d)).collect(Collectors.toList());
	}
}