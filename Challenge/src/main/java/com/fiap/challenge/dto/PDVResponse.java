package com.fiap.challenge.dto;

import com.fiap.challenge.model.PontoVenda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PDVResponse {

    private Long id;
    private String nome;
    private String endereco;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String telefone;
    private String email;
    private Boolean ativo;
    private String responsavel;
    private String tipo;
    private String observacoes;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    public PDVResponse(PontoVenda pontoVenda) {
        this.id = pontoVenda.getId();
        this.nome = pontoVenda.getNome();
        this.endereco = pontoVenda.getEndereco();
        this.bairro = pontoVenda.getBairro();
        this.cidade = pontoVenda.getCidade();
        this.estado = pontoVenda.getEstado();
        this.cep = pontoVenda.getCep();
        this.telefone = pontoVenda.getTelefone();
        this.email = pontoVenda.getEmail();
        this.ativo = pontoVenda.getAtivo();
        this.responsavel = pontoVenda.getResponsavel();
        this.tipo = pontoVenda.getTipo();
        this.observacoes = pontoVenda.getObservacoes();
        this.dataCriacao = pontoVenda.getDataCriacao();
        this.dataAtualizacao = pontoVenda.getDataAtualizacao();
    }
}
