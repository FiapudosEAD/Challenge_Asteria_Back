package com.fiap.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColunaVendaResponse {

    private String campo;
    private String label;
    private String tipo;
    private boolean ordenavel;

    public static ColunaVendaResponse[] getColunas() {
        return new ColunaVendaResponse[] {
            new ColunaVendaResponse("id", "ID", "number", true),
            new ColunaVendaResponse("produto", "Produto", "string", true),
            new ColunaVendaResponse("quantidade", "Quantidade", "number", true),
            new ColunaVendaResponse("valor", "Valor Unitário", "currency", true),
            new ColunaVendaResponse("valorTotal", "Valor Total", "currency", true),
            new ColunaVendaResponse("tipo", "Tipo", "string", true),
            new ColunaVendaResponse("status", "Status", "string", true),
            new ColunaVendaResponse("dataVenda", "Data da Venda", "datetime", true),
            new ColunaVendaResponse("observacoes", "Observações", "string", false)
        };
    }
}
