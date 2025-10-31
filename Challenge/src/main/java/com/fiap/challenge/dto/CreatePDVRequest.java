package com.fiap.challenge.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePDVRequest {

    @NotBlank(message = "Nome do PDV é obrigatório")
    private String nome;

    @NotBlank(message = "Endereço é obrigatório")
    private String endereco;

    private String bairro;

    private String cidade;

    private String estado;

    @Pattern(regexp = "\\d{5}-?\\d{3}", message = "CEP deve estar no formato 00000-000")
    private String cep;

    private String telefone;

    @Email(message = "Email deve ser válido")
    private String email;

    private String responsavel;

    private String tipo;

    private String observacoes;

    private Boolean ativo = true;
}
