package org.serratec.backend.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.serratec.backend.dto.DevolucaoRequestDTO;
import org.serratec.backend.dto.DevolucaoResponseDTO;
import org.serratec.backend.entity.Cliente;
import org.serratec.backend.entity.Devolucao;
import org.serratec.backend.entity.Produto;
import org.serratec.backend.repository.ClienteRepository;
import org.serratec.backend.repository.DevolucaoRepository;
import org.serratec.backend.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DevolucaoService {

    @Autowired
    private DevolucaoRepository devolucaoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public DevolucaoResponseDTO registrarNovaDevolucao(DevolucaoRequestDTO requestDTO) {
        Cliente cliente = clienteRepository.findById(requestDTO.getIdCliente())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com ID: " + requestDTO.getIdCliente()));

        if (requestDTO.getIdProdutos() == null || requestDTO.getIdProdutos().isEmpty()) {
            throw new IllegalArgumentException("A lista de produtos para devolução não pode ser vazia.");
        }

        Devolucao devolucao = new Devolucao();
        devolucao.setCliente(cliente);
        devolucao.setMotivo(requestDTO.getMotivo());
        devolucao.setDataSolicitacao(LocalDate.now());
        devolucao.setStatus("SOLICITADA"); 

        Devolucao devolucaoSalva = devolucaoRepository.save(devolucao);

        List<Produto> produtosAssociados = new ArrayList<>();
        for (Long idProduto : requestDTO.getIdProdutos()) {
            Produto produto = produtoRepository.findById(idProduto)
                    .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + idProduto));

            if (produto.getDevolucao() != null) {
                throw new IllegalStateException("Produto com ID: " + idProduto + " já está associado a outra devolução (ID da devolução: " + produto.getDevolucao().getId() + ").");
            }
            
            produto.setDevolucao(devolucaoSalva); 
            produtoRepository.save(produto);    
            produtosAssociados.add(produto);
        }

        devolucaoSalva.setProduto(produtosAssociados);

        return new DevolucaoResponseDTO(devolucaoSalva);
    }
}