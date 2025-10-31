package com.fiap.challenge.controller;

import com.fiap.challenge.dto.CardInfoResponse;
import com.fiap.challenge.dto.CreateVendaRequest;
import com.fiap.challenge.dto.VendaPorTipoResponse;
import com.fiap.challenge.dto.VendaResponse;
import com.fiap.challenge.service.DashboardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DashboardController {

    private final DashboardService dashboardService;

    /**
     * Endpoint para recuperar informações dos cards do dashboard
     * GET /api/dashboard/cards
     *
     * Retorna estatísticas gerais de vendas:
     * - Total de vendas
     * - Total de vendas concluídas
     * - Quantidade de vendas
     * - Vendas por status (concluídas, pendentes, canceladas)
     * - Ticket médio
     *
     * @return CardInfoResponse com as estatísticas
     */
    @GetMapping("/cards")
    public ResponseEntity<CardInfoResponse> getCardInfo() {
        try {
            CardInfoResponse response = dashboardService.getCardInfo();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint para recuperar todas as vendas do usuário logado
     * GET /api/dashboard/vendas
     *
     * Retorna a lista completa de vendas ordenadas por data (mais recente primeiro)
     *
     * @return Lista de VendaResponse
     */
    @GetMapping("/vendas")
    public ResponseEntity<List<VendaResponse>> getVendas() {
        try {
            List<VendaResponse> vendas = dashboardService.getVendasUsuario();
            return ResponseEntity.ok(vendas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint para recuperar vendas filtradas por tipo
     * GET /api/dashboard/vendas/tipo/{tipo}
     *
     * Exemplos de tipos: Eletrônicos, Roupas, Alimentos, Livros, etc.
     *
     * @param tipo Tipo da venda para filtrar
     * @return Lista de VendaResponse filtradas por tipo
     */
    @GetMapping("/vendas/tipo/{tipo}")
    public ResponseEntity<List<VendaResponse>> getVendasPorTipo(@PathVariable String tipo) {
        try {
            List<VendaResponse> vendas = dashboardService.getVendasPorTipo(tipo);
            return ResponseEntity.ok(vendas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint para recuperar vendas filtradas por status
     * GET /api/dashboard/vendas/status/{status}
     *
     * Status possíveis: Concluída, Pendente, Cancelada
     *
     * @param status Status da venda para filtrar
     * @return Lista de VendaResponse filtradas por status
     */
    @GetMapping("/vendas/status/{status}")
    public ResponseEntity<List<VendaResponse>> getVendasPorStatus(@PathVariable String status) {
        try {
            List<VendaResponse> vendas = dashboardService.getVendasPorStatus(status);
            return ResponseEntity.ok(vendas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint para recuperar estatísticas de vendas agrupadas por tipo
     * GET /api/dashboard/vendas/por-tipo
     *
     * Retorna valor total e quantidade de vendas para cada tipo
     *
     * @return Lista de VendaPorTipoResponse
     */
    @GetMapping("/vendas/por-tipo")
    public ResponseEntity<List<VendaPorTipoResponse>> getVendasAgrupadasPorTipo() {
        try {
            List<VendaPorTipoResponse> vendas = dashboardService.getVendasAgrupadasPorTipo();
            return ResponseEntity.ok(vendas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint para criar uma nova venda
     * POST /api/dashboard/vendas
     *
     * @param request Dados da venda a ser criada
     * @return VendaResponse com a venda criada
     */
    @PostMapping("/vendas")
    public ResponseEntity<VendaResponse> criarVenda(@Valid @RequestBody CreateVendaRequest request) {
        try {
            VendaResponse response = dashboardService.criarVenda(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Endpoint para recuperar os tipos de vendas disponíveis
     * GET /api/dashboard/tipos
     *
     * @return Lista de tipos únicos de vendas do usuário
     */
    @GetMapping("/tipos")
    public ResponseEntity<List<String>> getTiposDisponiveis() {
        try {
            List<String> tipos = dashboardService.getTiposDisponiveis();
            return ResponseEntity.ok(tipos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
