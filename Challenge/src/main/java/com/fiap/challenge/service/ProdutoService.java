package com.fiap.challenge.service;

import com.fiap.challenge.dto.CreateProdutoRequest;
import com.fiap.challenge.dto.ProdutoResponse;
import com.fiap.challenge.model.Produto;
import com.fiap.challenge.model.Usuario;
import com.fiap.challenge.repository.ProdutoRepository;
import com.fiap.challenge.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;

    /**
     * Recupera todos os produtos do usuário logado
     * @return Lista de ProdutoResponse
     */
    @Transactional(readOnly = true)
    public List<ProdutoResponse> getAllProdutos() {
        Usuario usuario = getUsuarioLogado();
        List<Produto> produtos = produtoRepository.findByUsuarioOrderByDataCriacaoDesc(usuario);

        return produtos.stream()
                .map(ProdutoResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca um produto específico por ID
     * @param id ID do produto
     * @return ProdutoResponse com os dados do produto
     */
    @Transactional(readOnly = true)
    public ProdutoResponse getProdutoById(Long id) {
        Usuario usuario = getUsuarioLogado();

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // Verifica se o produto pertence ao usuário logado
        if (!produto.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("Acesso negado: este produto não pertence ao usuário logado");
        }

        return new ProdutoResponse(produto);
    }

    /**
     * Busca produtos filtrados por nome
     * @param nome Nome ou parte do nome do produto
     * @return Lista de ProdutoResponse filtradas por nome
     */
    @Transactional(readOnly = true)
    public List<ProdutoResponse> getProdutosByNome(String nome) {
        Usuario usuario = getUsuarioLogado();
        List<Produto> produtos = produtoRepository.findByUsuarioAndNomeContainingIgnoreCase(usuario, nome);

        return produtos.stream()
                .map(ProdutoResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca produtos filtrados por categoria
     * @param categoria Categoria do produto
     * @return Lista de ProdutoResponse filtradas por categoria
     */
    @Transactional(readOnly = true)
    public List<ProdutoResponse> getProdutosByCategoria(String categoria) {
        Usuario usuario = getUsuarioLogado();
        List<Produto> produtos = produtoRepository.findByUsuarioAndCategoriaOrderByDataCriacaoDesc(usuario, categoria);

        return produtos.stream()
                .map(ProdutoResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca produtos filtrados por status ativo/inativo
     * @param ativo Status do produto
     * @return Lista de ProdutoResponse filtradas por status
     */
    @Transactional(readOnly = true)
    public List<ProdutoResponse> getProdutosByAtivo(Boolean ativo) {
        Usuario usuario = getUsuarioLogado();
        List<Produto> produtos = produtoRepository.findByUsuarioAndAtivoOrderByDataCriacaoDesc(usuario, ativo);

        return produtos.stream()
                .map(ProdutoResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca produto por código
     * @param codigo Código do produto
     * @return ProdutoResponse
     */
    @Transactional(readOnly = true)
    public ProdutoResponse getProdutoByCodigo(String codigo) {
        Usuario usuario = getUsuarioLogado();

        Produto produto = produtoRepository.findByUsuarioAndCodigo(usuario, codigo)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com o código: " + codigo));

        return new ProdutoResponse(produto);
    }

    /**
     * Lista categorias disponíveis
     * @return Lista de categorias únicas
     */
    @Transactional(readOnly = true)
    public List<String> getCategoriasDisponiveis() {
        Usuario usuario = getUsuarioLogado();
        return produtoRepository.findDistinctCategoriasByUsuario(usuario);
    }

    /**
     * Busca produtos com estoque baixo
     * @param limite Limite de estoque considerado baixo
     * @return Lista de produtos com estoque baixo
     */
    @Transactional(readOnly = true)
    public List<ProdutoResponse> getProdutosComEstoqueBaixo(Integer limite) {
        Usuario usuario = getUsuarioLogado();
        List<Produto> produtos = produtoRepository.findProdutosComEstoqueBaixo(usuario, limite);

        return produtos.stream()
                .map(ProdutoResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Cria um novo produto
     * @param request Dados do produto
     * @return ProdutoResponse com o produto criado
     */
    @Transactional
    public ProdutoResponse createProduto(CreateProdutoRequest request) {
        Usuario usuario = getUsuarioLogado();

        // Verifica se o código já existe para o usuário
        if (produtoRepository.existsByCodigoAndUsuario(request.getCodigo(), usuario)) {
            throw new RuntimeException("Já existe um produto com o código: " + request.getCodigo());
        }

        Produto produto = new Produto();
        produto.setCodigo(request.getCodigo());
        produto.setNome(request.getNome());
        produto.setDescricao(request.getDescricao());
        produto.setCategoria(request.getCategoria());
        produto.setPreco(request.getPreco());
        produto.setEstoque(request.getEstoque());
        produto.setFabricante(request.getFabricante());
        produto.setUnidadeMedida(request.getUnidadeMedida());
        produto.setAtivo(request.getAtivo() != null ? request.getAtivo() : true);
        produto.setUsuario(usuario);

        Produto produtoSalvo = produtoRepository.save(produto);
        return new ProdutoResponse(produtoSalvo);
    }

    /**
     * Atualiza um produto existente
     * @param id ID do produto
     * @param request Dados para atualização
     * @return ProdutoResponse com o produto atualizado
     */
    @Transactional
    public ProdutoResponse updateProduto(Long id, CreateProdutoRequest request) {
        Usuario usuario = getUsuarioLogado();

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // Verifica se o produto pertence ao usuário logado
        if (!produto.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("Acesso negado: este produto não pertence ao usuário logado");
        }

        // Verifica se o novo código já existe (se estiver mudando o código)
        if (!produto.getCodigo().equals(request.getCodigo())) {
            if (produtoRepository.existsByCodigoAndUsuario(request.getCodigo(), usuario)) {
                throw new RuntimeException("Já existe um produto com o código: " + request.getCodigo());
            }
        }

        // Atualiza os campos
        produto.setCodigo(request.getCodigo());
        produto.setNome(request.getNome());
        produto.setDescricao(request.getDescricao());
        produto.setCategoria(request.getCategoria());
        produto.setPreco(request.getPreco());
        produto.setEstoque(request.getEstoque());
        produto.setFabricante(request.getFabricante());
        produto.setUnidadeMedida(request.getUnidadeMedida());
        if (request.getAtivo() != null) {
            produto.setAtivo(request.getAtivo());
        }

        Produto produtoAtualizado = produtoRepository.save(produto);
        return new ProdutoResponse(produtoAtualizado);
    }

    /**
     * Deleta um produto
     * @param id ID do produto
     */
    @Transactional
    public void deleteProduto(Long id) {
        Usuario usuario = getUsuarioLogado();

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // Verifica se o produto pertence ao usuário logado
        if (!produto.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("Acesso negado: este produto não pertence ao usuário logado");
        }

        produtoRepository.delete(produto);
    }

    /**
     * Recupera o usuário logado
     * @return Usuario logado
     */
    private Usuario getUsuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Usuário não autenticado");
        }

        String email = authentication.getName();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}
