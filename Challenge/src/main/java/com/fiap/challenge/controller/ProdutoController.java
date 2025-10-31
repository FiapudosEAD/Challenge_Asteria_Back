package com.fiap.challenge.controller;

import com.fiap.challenge.dto.ColunaProdutoResponse;
import com.fiap.challenge.dto.CreateProdutoRequest;
import com.fiap.challenge.dto.ProdutoResponse;
import com.fiap.challenge.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProdutoController {

    private final ProdutoService produtoService;

    /**
     * Endpoint para recuperar as colunas da tabela de produtos
     * GET /api/produtos/colunas
     *
     * Retorna a estrutura das colunas para exibir na tabela de produtos
     * Útil para frontends que precisam construir tabelas dinamicamente
     *
     * @return Array de ColunaProdutoResponse com informações das colunas
     */
    @GetMapping("/colunas")
    public ResponseEntity<ColunaProdutoResponse[]> getColunas() {
        try {
            ColunaProdutoResponse[] colunas = ColunaProdutoResponse.getColunas();
            return ResponseEntity.ok(colunas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint para recuperar todos os produtos do usuário logado
     * GET /api/produtos
     *
     * @return Lista de ProdutoResponse
     */
    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> getAllProdutos() {
        try {
            List<ProdutoResponse> produtos = produtoService.getAllProdutos();
            return ResponseEntity.ok(produtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint para buscar um produto específico por ID
     * GET /api/produtos/{id}
     *
     * @param id ID do produto
     * @return ProdutoResponse com os dados do produto
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> getProdutoById(@PathVariable Long id) {
        try {
            ProdutoResponse produto = produtoService.getProdutoById(id);
            return ResponseEntity.ok(produto);
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
     * Endpoint para filtrar produtos por nome
     * GET /api/produtos/filtrar/nome?nome={nome}
     *
     * Busca produtos que contenham o texto informado no nome (case-insensitive)
     *
     * @param nome Nome ou parte do nome do produto
     * @return Lista de ProdutoResponse filtradas por nome
     */
    @GetMapping("/filtrar/nome")
    public ResponseEntity<List<ProdutoResponse>> getProdutosByNome(
            @RequestParam(name = "nome") String nome) {
        try {
            List<ProdutoResponse> produtos = produtoService.getProdutosByNome(nome);
            return ResponseEntity.ok(produtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint para filtrar produtos por categoria
     * GET /api/produtos/filtrar/categoria?categoria={categoria}
     *
     * @param categoria Categoria do produto
     * @return Lista de ProdutoResponse filtradas por categoria
     */
    @GetMapping("/filtrar/categoria")
    public ResponseEntity<List<ProdutoResponse>> getProdutosByCategoria(
            @RequestParam(name = "categoria") String categoria) {
        try {
            List<ProdutoResponse> produtos = produtoService.getProdutosByCategoria(categoria);
            return ResponseEntity.ok(produtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint para filtrar produtos por status (ativo/inativo)
     * GET /api/produtos/filtrar/ativo?ativo={true/false}
     *
     * @param ativo Status do produto
     * @return Lista de ProdutoResponse filtradas por status
     */
    @GetMapping("/filtrar/ativo")
    public ResponseEntity<List<ProdutoResponse>> getProdutosByAtivo(
            @RequestParam(name = "ativo") Boolean ativo) {
        try {
            List<ProdutoResponse> produtos = produtoService.getProdutosByAtivo(ativo);
            return ResponseEntity.ok(produtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint para buscar produto por código
     * GET /api/produtos/codigo/{codigo}
     *
     * @param codigo Código único do produto
     * @return ProdutoResponse
     */
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<ProdutoResponse> getProdutoByCodigo(@PathVariable String codigo) {
        try {
            ProdutoResponse produto = produtoService.getProdutoByCodigo(codigo);
            return ResponseEntity.ok(produto);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("não encontrado")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint para listar categorias disponíveis
     * GET /api/produtos/categorias
     *
     * @return Lista de categorias únicas
     */
    @GetMapping("/categorias")
    public ResponseEntity<List<String>> getCategoriasDisponiveis() {
        try {
            List<String> categorias = produtoService.getCategoriasDisponiveis();
            return ResponseEntity.ok(categorias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint para buscar produtos com estoque baixo
     * GET /api/produtos/estoque-baixo?limite={numero}
     *
     * @param limite Limite de estoque considerado baixo (padrão: 10)
     * @return Lista de produtos com estoque baixo ou igual ao limite
     */
    @GetMapping("/estoque-baixo")
    public ResponseEntity<List<ProdutoResponse>> getProdutosComEstoqueBaixo(
            @RequestParam(name = "limite", defaultValue = "10") Integer limite) {
        try {
            List<ProdutoResponse> produtos = produtoService.getProdutosComEstoqueBaixo(limite);
            return ResponseEntity.ok(produtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint para criar um novo produto
     * POST /api/produtos
     *
     * @param request Dados do novo produto
     * @return ProdutoResponse com o produto criado
     */
    @PostMapping
    public ResponseEntity<ProdutoResponse> createProduto(@Valid @RequestBody CreateProdutoRequest request) {
        try {
            ProdutoResponse produto = produtoService.createProduto(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(produto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Endpoint para atualizar um produto existente
     * PUT /api/produtos/{id}
     *
     * @param id ID do produto
     * @param request Dados para atualização
     * @return ProdutoResponse com o produto atualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> updateProduto(
            @PathVariable Long id,
            @Valid @RequestBody CreateProdutoRequest request) {
        try {
            ProdutoResponse produto = produtoService.updateProduto(id, request);
            return ResponseEntity.ok(produto);
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
     * Endpoint para deletar um produto
     * DELETE /api/produtos/{id}
     *
     * @param id ID do produto
     * @return Status 204 No Content em caso de sucesso
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        try {
            produtoService.deleteProduto(id);
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
