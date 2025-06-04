package org.serratec.backend.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.serratec.backend.entity.Devolucao;

public record DevolucaoResponseDTO(Long id, LocalDate dataSolicitacao, LocalDate dataEnvioDevolucao,
		LocalDate dataRecebimento, String status, String motivo, Long idCliente, Long idPedidoOriginal,
		List<ProdutoResumoDTO> produtosDevolvidos) {
	public record ProdutoResumoDTO(Long idProduto, String nomeProduto) {
	}

	public DevolucaoResponseDTO(Devolucao devolucao) {
		this(devolucao.getId(), devolucao.getDataSolicitacao(), devolucao.getDataEnvioDevolucao(),
				devolucao.getDataRecebimento(), devolucao.getStatus(), devolucao.getMotivo(),
				(devolucao.getCliente() != null) ? devolucao.getCliente().getId() : null, null,
				(devolucao.getProdutos() != null) ? devolucao.getProdutos().stream()
						.map(produto -> new ProdutoResumoDTO(produto.getId(), produto.getNomeProduto()))
						.collect(Collectors.toList()) : List.of());
	}

	public DevolucaoResponseDTO(Devolucao devolucao, Long idPedidoOriginal) {
		this(devolucao.getId(), devolucao.getDataSolicitacao(), devolucao.getDataEnvioDevolucao(),
				devolucao.getDataRecebimento(), devolucao.getStatus(), devolucao.getMotivo(),
				(devolucao.getCliente() != null) ? devolucao.getCliente().getId() : null, idPedidoOriginal,
				(devolucao.getProdutos() != null) ? devolucao.getProdutos().stream()
						.map(produto -> new ProdutoResumoDTO(produto.getId(), produto.getNomeProduto()))
						.collect(Collectors.toList()) : List.of());
	}
}