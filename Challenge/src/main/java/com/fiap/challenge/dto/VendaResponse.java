package com.fiap.challenge.dto;

import com.fiap.challenge.model.Venda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendaResponse {

    private Long id;
    private String produto;
    private Integer quantidade;
    private BigDecimal valor;
    private BigDecimal valorTotal;
    private String tipo;
    private String status;
    private LocalDateTime dataVenda;
    private String observacoes;

    public VendaResponse(Venda venda) {
        this.id = venda.getId();
        this.produto = venda.getProduto();
        this.quantidade = venda.getQuantidade();
        this.valor = venda.getValor();
        this.valorTotal = venda.getValorTotal();
        this.tipo = venda.getTipo();
        this.status = venda.getStatus();
        this.dataVenda = venda.getDataVenda();
        this.observacoes = venda.getObservacoes();
    }
}
