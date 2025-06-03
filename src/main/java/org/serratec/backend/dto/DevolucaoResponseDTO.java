package org.serratec.backend.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.serratec.backend.entity.Devolucao;
import org.serratec.backend.entity.Produto;

public class DevolucaoResponseDTO {
    private Long id;
    private LocalDate dataSolicitacao;
    private LocalDate dataEnvioDevolucao;
    private LocalDate dataRecebimento;
    private String status;
    private String motivo;
    private Long idCliente;
    private List<Long> idProdutos;

    public DevolucaoResponseDTO() {}

    public DevolucaoResponseDTO(Devolucao devolucao) {
        this.id = devolucao.getId();
        this.dataSolicitacao = devolucao.getDataSolicitacao();
        this.dataEnvioDevolucao = devolucao.getDataEnvioDevolucao();
        this.dataRecebimento = devolucao.getDataRecebimento();
        this.status = devolucao.getStatus();
        this.motivo = devolucao.getMotivo();

        if (devolucao.getCliente() != null) {
            this.idCliente = devolucao.getCliente().getId();
        }

        if (devolucao.getProduto() != null) {
            this.idProdutos = devolucao.getProduto().stream()
                                     .map(Produto::getId)
                                     .collect(Collectors.toList());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDate dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public LocalDate getDataEnvioDevolucao() {
        return dataEnvioDevolucao;
    }

    public void setDataEnvioDevolucao(LocalDate dataEnvioDevolucao) {
        this.dataEnvioDevolucao = dataEnvioDevolucao;
    }

    public LocalDate getDataRecebimento() {
        return dataRecebimento;
    }

    public void setDataRecebimento(LocalDate dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public List<Long> getIdProdutos() {
        return idProdutos;
    }

    public void setIdProdutos(List<Long> idProdutos) {
        this.idProdutos = idProdutos;
    }
}