package com.fiap.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColunaProdutoResponse {

    private String campo;
    private String label;
    private String tipo;
    private boolean ordenavel;

    public static ColunaProdutoResponse[] getColunas() {
        return new ColunaProdutoResponse[] {
            new ColunaProdutoResponse("id", "ID", "number", true),
            new ColunaProdutoResponse("codigo", "Código", "string", true),
            new ColunaProdutoResponse("nome", "Nome do Produto", "string", true),
            new ColunaProdutoResponse("descricao", "Descrição", "string", false),
            new ColunaProdutoResponse("categoria", "Categoria", "string", true),
            new ColunaProdutoResponse("preco", "Preço", "currency", true),
            new ColunaProdutoResponse("estoque", "Estoque", "number", true),
            new ColunaProdutoResponse("ativo", "Status", "boolean", true),
            new ColunaProdutoResponse("fabricante", "Fabricante", "string", true),
            new ColunaProdutoResponse("unidadeMedida", "Unidade", "string", false),
            new ColunaProdutoResponse("dataCriacao", "Data de Criação", "datetime", true),
            new ColunaProdutoResponse("dataAtualizacao", "Data de Atualização", "datetime", true)
        };
    }
}
