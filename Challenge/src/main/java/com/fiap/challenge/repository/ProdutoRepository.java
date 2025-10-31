package com.fiap.challenge.repository;

import com.fiap.challenge.model.Produto;
import com.fiap.challenge.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    /**
     * Busca todos os produtos de um usuário específico
     * @param usuario Usuário proprietário dos produtos
     * @return Lista de produtos do usuário
     */
    List<Produto> findByUsuarioOrderByDataCriacaoDesc(Usuario usuario);

    /**
     * Busca produtos ativos de um usuário
     * @param usuario Usuário proprietário dos produtos
     * @param ativo Status ativo/inativo
     * @return Lista de produtos filtrados por status
     */
    List<Produto> findByUsuarioAndAtivoOrderByDataCriacaoDesc(Usuario usuario, Boolean ativo);

    /**
     * Busca produtos por categoria
     * @param usuario Usuário proprietário dos produtos
     * @param categoria Categoria do produto
     * @return Lista de produtos da categoria especificada
     */
    List<Produto> findByUsuarioAndCategoriaOrderByDataCriacaoDesc(Usuario usuario, String categoria);

    /**
     * Busca produtos por nome (pesquisa parcial, case-insensitive)
     * @param usuario Usuário proprietário dos produtos
     * @param nome Nome ou parte do nome do produto
     * @return Lista de produtos que contenham o texto no nome
     */
    @Query("SELECT p FROM Produto p WHERE p.usuario = :usuario AND LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%')) ORDER BY p.dataCriacao DESC")
    List<Produto> findByUsuarioAndNomeContainingIgnoreCase(@Param("usuario") Usuario usuario, @Param("nome") String nome);

    /**
     * Busca produto por código
     * @param usuario Usuário proprietário dos produtos
     * @param codigo Código único do produto
     * @return Optional contendo o produto se encontrado
     */
    Optional<Produto> findByUsuarioAndCodigo(Usuario usuario, String codigo);

    /**
     * Verifica se existe produto com o código informado para o usuário
     * @param codigo Código do produto
     * @param usuario Usuário proprietário
     * @return true se o código já existe, false caso contrário
     */
    Boolean existsByCodigoAndUsuario(String codigo, Usuario usuario);

    /**
     * Busca categorias únicas dos produtos do usuário
     * @param usuario Usuário proprietário dos produtos
     * @return Lista de categorias únicas
     */
    @Query("SELECT DISTINCT p.categoria FROM Produto p WHERE p.usuario = :usuario ORDER BY p.categoria")
    List<String> findDistinctCategoriasByUsuario(@Param("usuario") Usuario usuario);

    /**
     * Conta produtos por categoria
     * @param usuario Usuário proprietário dos produtos
     * @param categoria Categoria
     * @return Número de produtos na categoria
     */
    Long countByUsuarioAndCategoria(Usuario usuario, String categoria);

    /**
     * Conta produtos ativos do usuário
     * @param usuario Usuário proprietário dos produtos
     * @return Número de produtos ativos
     */
    Long countByUsuarioAndAtivo(Usuario usuario, Boolean ativo);

    /**
     * Busca produtos com estoque baixo (menor ou igual ao limite)
     * @param usuario Usuário proprietário dos produtos
     * @param limite Limite de estoque
     * @return Lista de produtos com estoque baixo
     */
    @Query("SELECT p FROM Produto p WHERE p.usuario = :usuario AND p.estoque <= :limite ORDER BY p.estoque ASC")
    List<Produto> findProdutosComEstoqueBaixo(@Param("usuario") Usuario usuario, @Param("limite") Integer limite);
}
