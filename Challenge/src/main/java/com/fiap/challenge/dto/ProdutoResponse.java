package com.fiap.challenge.dto;

import com.fiap.challenge.model.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoResponse {

    private Long id;
    private String codigo;
    private String nome;
    private String descricao;
    private String categoria;
    private BigDecimal preco;
    private Integer estoque;
    private Boolean ativo;
    private String fabricante;
    private String unidadeMedida;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    public ProdutoResponse(Produto produto) {
        this.id = produto.getId();
        this.codigo = produto.getCodigo();
        this.nome = produto.getNome();
        this.descricao = produto.getDescricao();
        this.categoria = produto.getCategoria();
        this.preco = produto.getPreco();
        this.estoque = produto.getEstoque();
        this.ativo = produto.getAtivo();
        this.fabricante = produto.getFabricante();
        this.unidadeMedida = produto.getUnidadeMedida();
        this.dataCriacao = produto.getDataCriacao();
        this.dataAtualizacao = produto.getDataAtualizacao();
    }
}
