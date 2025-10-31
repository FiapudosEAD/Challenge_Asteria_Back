package com.fiap.challenge.controller;

import com.fiap.challenge.dto.ColunaPDVResponse;
import com.fiap.challenge.dto.CreatePDVRequest;
import com.fiap.challenge.dto.PDVResponse;
import com.fiap.challenge.service.PontoVendaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pdv")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PontoVendaController {

    private final PontoVendaService pontoVendaService;

    /**
     * Endpoint para recuperar as colunas da tabela de pontos de venda
     * GET /api/pdv/colunas
     *
     * Retorna a estrutura das colunas para exibir na tabela de PDVs
     * Útil para frontends que precisam construir tabelas dinamicamente
     *
     * @return Array de ColunaPDVResponse com informações das colunas
     */
    @GetMapping("/colunas")
    public ResponseEntity<ColunaPDVResponse[]> getColunas() {
        try {
            ColunaPDVResponse[] colunas = ColunaPDVResponse.getColunas();
            return ResponseEntity.ok(colunas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint para recuperar todos os pontos de venda do usuário logado
     * GET /api/pdv
     *
     * @return Lista de PDVResponse
     */
    @GetMapping
    public ResponseEntity<List<PDVResponse>> getAllPDVs() {
        try {
            List<PDVResponse> pdvs = pontoVendaService.getAllPDVs();
            return ResponseEntity.ok(pdvs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint para buscar um PDV específico por ID
     * GET /api/pdv/{id}
     *
     * @param id ID do PDV
     * @return PDVResponse com os dados do PDV
     */
    @GetMapping("/{id}")
    public ResponseEntity<PDVResponse> getPDVById(@PathVariable Long id) {
        try {
            PDVResponse pdv = pontoVendaService.getPDVById(id);
            return ResponseEntity.ok(pdv);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("não encontrado")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } else if (e.getMessage().contains("Acesso negado")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint para filtrar PDVs por endereço
     * GET /api/pdv/filtrar/endereco?endereco={endereco}
     *
     * Busca PDVs que contenham o texto informado no endereço (case-insensitive)
     *
     * @param endereco Endereço ou parte do endereço
     * @return Lista de PDVResponse filtradas por endereço
     */
    @GetMapping("/filtrar/endereco")
    public ResponseEntity<List<PDVResponse>> getPDVsByEndereco(
            @RequestParam(name = "endereco") String endereco) {
        try {
            List<PDVResponse> pdvs = pontoVendaService.getPDVsByEndereco(endereco);
            return ResponseEntity.ok(pdvs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint para filtrar PDVs por nome
     * GET /api/pdv/filtrar/nome?nome={nome}
     *
     * @param nome Nome ou parte do nome do PDV
     * @return Lista de PDVResponse filtradas por nome
     */
    @GetMapping("/filtrar/nome")
    public ResponseEntity<List<PDVResponse>> getPDVsByNome(
            @RequestParam(name = "nome") String nome) {
        try {
            List<PDVResponse> pdvs = pontoVendaService.getPDVsByNome(nome);
            return ResponseEntity.ok(pdvs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint para filtrar PDVs por cidade
     * GET /api/pdv/filtrar/cidade?cidade={cidade}
     *
     * @param cidade Nome da cidade
     * @return Lista de PDVResponse filtradas por cidade
     */
    @GetMapping("/filtrar/cidade")
    public ResponseEntity<List<PDVResponse>> getPDVsByCidade(
            @RequestParam(name = "cidade") String cidade) {
        try {
            List<PDVResponse> pdvs = pontoVendaService.getPDVsByCidade(cidade);
            return ResponseEntity.ok(pdvs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint para filtrar PDVs por estado
     * GET /api/pdv/filtrar/estado?estado={estado}
     *
     * @param estado Sigla do estado (UF)
     * @return Lista de PDVResponse filtradas por estado
     */
    @GetMapping("/filtrar/estado")
    public ResponseEntity<List<PDVResponse>> getPDVsByEstado(
            @RequestParam(name = "estado") String estado) {
        try {
            List<PDVResponse> pdvs = pontoVendaService.getPDVsByEstado(estado);
            return ResponseEntity.ok(pdvs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint para filtrar PDVs por bairro
     * GET /api/pdv/filtrar/bairro?bairro={bairro}
     *
     * @param bairro Nome do bairro
     * @return Lista de PDVResponse filtradas por bairro
     */
    @GetMapping("/filtrar/bairro")
    public ResponseEntity<List<PDVResponse>> getPDVsByBairro(
            @RequestParam(name = "bairro") String bairro) {
        try {
            List<PDVResponse> pdvs = pontoVendaService.getPDVsByBairro(bairro);
            return ResponseEntity.ok(pdvs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint para filtrar PDVs por tipo
     * GET /api/pdv/filtrar/tipo?tipo={tipo}
     *
     * @param tipo Tipo do PDV (Matriz, Filial, Franquia, Quiosque)
     * @return Lista de PDVResponse filtradas por tipo
     */
    @GetMapping("/filtrar/tipo")
    public ResponseEntity<List<PDVResponse>> getPDVsByTipo(
            @RequestParam(name = "tipo") String tipo) {
        try {
            List<PDVResponse> pdvs = pontoVendaService.getPDVsByTipo(tipo);
            return ResponseEntity.ok(pdvs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint para filtrar PDVs por status (ativo/inativo)
     * GET /api/pdv/filtrar/ativo?ativo={true/false}
     *
     * @param ativo Status do PDV
     * @return Lista de PDVResponse filtradas por status
     */
    @GetMapping("/filtrar/ativo")
    public ResponseEntity<List<PDVResponse>> getPDVsByAtivo(
            @RequestParam(name = "ativo") Boolean ativo) {
        try {
            List<PDVResponse> pdvs = pontoVendaService.getPDVsByAtivo(ativo);
            return ResponseEntity.ok(pdvs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint para listar tipos disponíveis de PDVs
     * GET /api/pdv/tipos
     *
     * @return Lista de tipos únicos
     */
    @GetMapping("/tipos")
    public ResponseEntity<List<String>> getTiposDisponiveis() {
        try {
            List<String> tipos = pontoVendaService.getTiposDisponiveis();
            return ResponseEntity.ok(tipos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint para listar cidades onde existem PDVs
     * GET /api/pdv/cidades
     *
     * @return Lista de cidades únicas
     */
    @GetMapping("/cidades")
    public ResponseEntity<List<String>> getCidadesDisponiveis() {
        try {
            List<String> cidades = pontoVendaService.getCidadesDisponiveis();
            return ResponseEntity.ok(cidades);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint para criar um novo PDV
     * POST /api/pdv
     *
     * @param request Dados do novo PDV
     * @return PDVResponse com o PDV criado
     */
    @PostMapping
    public ResponseEntity<PDVResponse> createPDV(@Valid @RequestBody CreatePDVRequest request) {
        try {
            PDVResponse pdv = pontoVendaService.createPDV(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(pdv);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Endpoint para atualizar um PDV existente
     * PUT /api/pdv/{id}
     *
     * @param id ID do PDV
     * @param request Dados para atualização
     * @return PDVResponse com o PDV atualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<PDVResponse> updatePDV(
            @PathVariable Long id,
            @Valid @RequestBody CreatePDVRequest request) {
        try {
            PDVResponse pdv = pontoVendaService.updatePDV(id, request);
            return ResponseEntity.ok(pdv);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("não encontrado")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } else if (e.getMessage().contains("Acesso negado")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Endpoint para deletar um PDV
     * DELETE /api/pdv/{id}
     *
     * @param id ID do PDV
     * @return Status 204 No Content em caso de sucesso
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePDV(@PathVariable Long id) {
        try {
            pontoVendaService.deletePDV(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            if (e.getMessage().contains("não encontrado")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } else if (e.getMessage().contains("Acesso negado")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
