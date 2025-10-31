package com.fiap.challenge.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "pontos_venda")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PontoVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do PDV é obrigatório")
    @Size(min = 3, max = 200, message = "Nome deve ter entre 3 e 200 caracteres")
    @Column(nullable = false, length = 200)
    private String nome;

    @NotBlank(message = "Endereço é obrigatório")
    @Column(nullable = false, length = 500)
    private String endereco;

    @Column(length = 100)
    private String bairro;

    @Column(length = 100)
    private String cidade;

    @Column(length = 2)
    private String estado;

    @Pattern(regexp = "\\d{5}-?\\d{3}", message = "CEP deve estar no formato 00000-000")
    @Column(length = 10)
    private String cep;

    @Column(length = 20)
    private String telefone;

    @Email(message = "Email deve ser válido")
    @Column(length = 150)
    private String email;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(length = 100)
    private String responsavel;

    @Column(length = 50)
    private String tipo; // Ex: "Matriz", "Filial", "Franquia", "Quiosque"

    @Column(length = 1000)
    private String observacoes;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
        if (ativo == null) {
            ativo = true;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }
}
