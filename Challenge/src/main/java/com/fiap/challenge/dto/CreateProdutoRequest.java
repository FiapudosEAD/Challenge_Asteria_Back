package com.fiap.challenge.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProdutoRequest {

    @NotBlank(message = "Código do produto é obrigatório")
    private String codigo;

    @NotBlank(message = "Nome do produto é obrigatório")
    private String nome;

    private String descricao;

    @NotBlank(message = "Categoria é obrigatória")
    private String categoria;

    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero")
    private BigDecimal preco;

    @NotNull(message = "Estoque é obrigatório")
    @Min(value = 0, message = "Estoque não pode ser negativo")
    private Integer estoque;

    private String fabricante;

    private String unidadeMedida;

    private Boolean ativo = true;
}
