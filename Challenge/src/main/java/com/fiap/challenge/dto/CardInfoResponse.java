package com.fiap.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardInfoResponse {

    private BigDecimal totalVendas;
    private BigDecimal totalVendasConcluidas;
    private Long quantidadeVendas;
    private Long vendasConcluidas;
    private Long vendasPendentes;
    private Long vendasCanceladas;
    private BigDecimal ticketMedio;
}
