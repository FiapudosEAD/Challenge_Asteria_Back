package com.fiap.challenge.controller;

import com.fiap.challenge.dto.ColunaVendaResponse;
import com.fiap.challenge.dto.VendaDetalhadaResponse;
import com.fiap.challenge.service.VendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VendaController {

    private final VendaService vendaService;

    /**
     * Endpoint para recuperar as colunas da tabela de vendas
     * GET /api/vendas/colunas
     *
     * Retorna a estrutura das colunas para exibir na tabela de vendas
     * Útil para frontends que precisam construir tabelas dinamicamente
     *
     * @return Array de ColunaVendaResponse com informações das colunas
     */
    @GetMapping("/colunas")
    public ResponseEntity<ColunaVendaResponse[]> getColunas() {
        try {
            ColunaVendaResponse[] colunas = ColunaVendaResponse.getColunas();
            return ResponseEntity.ok(colunas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint para recuperar todas as vendas do usuário logado
     * GET /api/vendas
     *
     * Retorna todas as vendas com informações detalhadas
     *
     * @return Lista de VendaDetalhadaResponse
     */
    @GetMapping
    public ResponseEntity<List<VendaDetalhadaResponse>> getAllVendas() {
        try {
            List<VendaDetalhadaResponse> vendas = vendaService.getAllVendas();
            return ResponseEntity.ok(vendas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint para buscar uma venda específica por ID
     * GET /api/vendas/{id}
     *
     * Busca uma venda específica do usuário logado pelo ID
     *
     * @param id ID da venda
     * @return VendaDetalhadaResponse com os dados da venda
     */
    @GetMapping("/{id}")
    public ResponseEntity<VendaDetalhadaResponse> getVendaById(@PathVariable Long id) {
        try {
            VendaDetalhadaResponse venda = vendaService.getVendaById(id);
            return ResponseEntity.ok(venda);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("não encontrada")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } else if (e.getMessage().contains("Acesso negado")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint para filtrar vendas por lista de IDs
     * GET /api/vendas/filtrar?ids=1,2,3
     *
     * Busca múltiplas vendas de uma vez usando uma lista de IDs
     * Útil para filtros e seleções múltiplas
     *
     * @param ids Lista de IDs separados por vírgula
     * @return Lista de VendaDetalhadaResponse filtradas
     */
    @GetMapping("/filtrar")
    public ResponseEntity<List<VendaDetalhadaResponse>> getVendasByIds(
            @RequestParam(name = "ids") List<Long> ids) {
        try {
            List<VendaDetalhadaResponse> vendas = vendaService.getVendasByIds(ids);
            return ResponseEntity.ok(vendas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint para atualizar uma venda
     * PUT /api/vendas/{id}
     *
     * @param id ID da venda
     * @param updateRequest Dados para atualização
     * @return VendaDetalhadaResponse com a venda atualizada
     */
    @PutMapping("/{id}")
    public ResponseEntity<VendaDetalhadaResponse> updateVenda(
            @PathVariable Long id,
            @RequestBody UpdateVendaRequest updateRequest) {
        try {
            VendaDetalhadaResponse venda = vendaService.updateVenda(
                id,
                updateRequest.getProduto(),
                updateRequest.getQuantidade(),
                updateRequest.getValor(),
                updateRequest.getTipo(),
                updateRequest.getStatus(),
                updateRequest.getObservacoes()
            );
            return ResponseEntity.ok(venda);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("não encontrada")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } else if (e.getMessage().contains("Acesso negado")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Endpoint para deletar uma venda
     * DELETE /api/vendas/{id}
     *
     * @param id ID da venda
     * @return Status 204 No Content em caso de sucesso
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenda(@PathVariable Long id) {
        try {
            vendaService.deleteVenda(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            if (e.getMessage().contains("não encontrada")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } else if (e.getMessage().contains("Acesso negado")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Inner class para requisição de atualização de venda
     */
    @lombok.Data
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class UpdateVendaRequest {
        private String produto;
        private Integer quantidade;
        private java.math.BigDecimal valor;
        private String tipo;
        private String status;
        private String observacoes;
    }
}
