package com.fiap.challenge.dto;

import com.fiap.challenge.model.Venda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO mais completo para a aba de Vendas
 * Inclui informações adicionais que podem não estar no VendaResponse básico
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendaDetalhadaResponse {

    private Long id;
    private String produto;
    private Integer quantidade;
    private BigDecimal valor;
    private BigDecimal valorTotal;
    private String tipo;
    private String status;
    private LocalDateTime dataVenda;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private String observacoes;
    private String nomeUsuario;
    private String emailUsuario;

    public VendaDetalhadaResponse(Venda venda) {
        this.id = venda.getId();
        this.produto = venda.getProduto();
        this.quantidade = venda.getQuantidade();
        this.valor = venda.getValor();
        this.valorTotal = venda.getValorTotal();
        this.tipo = venda.getTipo();
        this.status = venda.getStatus();
        this.dataVenda = venda.getDataVenda();
        this.dataCriacao = venda.getDataCriacao();
        this.dataAtualizacao = venda.getDataAtualizacao();
        this.observacoes = venda.getObservacoes();
        this.nomeUsuario = venda.getUsuario().getNome();
        this.emailUsuario = venda.getUsuario().getEmail();
    }
}
