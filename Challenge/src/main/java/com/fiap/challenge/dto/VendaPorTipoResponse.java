package com.fiap.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendaPorTipoResponse {

    private String tipo;
    private BigDecimal valorTotal;
    private Long quantidade;
}
