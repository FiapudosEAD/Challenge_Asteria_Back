package com.fiap.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColunaPDVResponse {

    private String campo;
    private String label;
    private String tipo;
    private boolean ordenavel;

    public static ColunaPDVResponse[] getColunas() {
        return new ColunaPDVResponse[] {
            new ColunaPDVResponse("id", "ID", "number", true),
            new ColunaPDVResponse("nome", "Nome do PDV", "string", true),
            new ColunaPDVResponse("endereco", "Endereço", "string", true),
            new ColunaPDVResponse("bairro", "Bairro", "string", true),
            new ColunaPDVResponse("cidade", "Cidade", "string", true),
            new ColunaPDVResponse("estado", "Estado", "string", true),
            new ColunaPDVResponse("cep", "CEP", "string", false),
            new ColunaPDVResponse("telefone", "Telefone", "string", false),
            new ColunaPDVResponse("email", "Email", "string", false),
            new ColunaPDVResponse("responsavel", "Responsável", "string", true),
            new ColunaPDVResponse("tipo", "Tipo", "string", true),
            new ColunaPDVResponse("ativo", "Status", "boolean", true),
            new ColunaPDVResponse("dataCriacao", "Data de Criação", "datetime", true),
            new ColunaPDVResponse("dataAtualizacao", "Data de Atualização", "datetime", true),
            new ColunaPDVResponse("observacoes", "Observações", "string", false)
        };
    }
}
