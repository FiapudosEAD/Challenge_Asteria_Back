package com.fiap.challenge.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "vendas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do produto é obrigatório")
    @Column(nullable = false, length = 200)
    private String produto;

    @NotNull(message = "Quantidade é obrigatória")
    @Column(nullable = false)
    private Integer quantidade;

    @NotNull(message = "Valor é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @NotBlank(message = "Tipo é obrigatório")
    @Column(nullable = false, length = 50)
    private String tipo; // Exemplos: "Eletrônicos", "Roupas", "Alimentos", "Livros", etc.

    @NotBlank(message = "Status é obrigatório")
    @Column(nullable = false, length = 30)
    private String status; // Exemplos: "Concluída", "Pendente", "Cancelada"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "data_venda", nullable = false)
    private LocalDateTime dataVenda;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column(length = 500)
    private String observacoes;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
        if (dataVenda == null) {
            dataVenda = LocalDateTime.now();
        }
        calcularValorTotal();
    }

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
        calcularValorTotal();
    }

    private void calcularValorTotal() {
        if (valor != null && quantidade != null) {
            this.valorTotal = valor.multiply(BigDecimal.valueOf(quantidade));
        }
    }
}
