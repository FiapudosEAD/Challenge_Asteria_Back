package com.fiap.challenge.repository;

import com.fiap.challenge.model.PontoVenda;
import com.fiap.challenge.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PontoVendaRepository extends JpaRepository<PontoVenda, Long> {

    /**
     * Busca todos os pontos de venda de um usuário específico
     * @param usuario Usuário proprietário dos PDVs
     * @return Lista de pontos de venda do usuário
     */
    List<PontoVenda> findByUsuarioOrderByDataCriacaoDesc(Usuario usuario);

    /**
     * Busca PDVs ativos de um usuário
     * @param usuario Usuário proprietário dos PDVs
     * @param ativo Status ativo/inativo
     * @return Lista de PDVs filtrados por status
     */
    List<PontoVenda> findByUsuarioAndAtivoOrderByDataCriacaoDesc(Usuario usuario, Boolean ativo);

    /**
     * Busca PDVs por tipo
     * @param usuario Usuário proprietário dos PDVs
     * @param tipo Tipo do PDV (Matriz, Filial, Franquia, Quiosque)
     * @return Lista de PDVs do tipo especificado
     */
    List<PontoVenda> findByUsuarioAndTipoOrderByDataCriacaoDesc(Usuario usuario, String tipo);

    /**
     * Busca PDVs por endereço (pesquisa parcial, case-insensitive)
     * @param usuario Usuário proprietário dos PDVs
     * @param endereco Endereço ou parte do endereço
     * @return Lista de PDVs que contenham o texto no endereço
     */
    @Query("SELECT p FROM PontoVenda p WHERE p.usuario = :usuario AND LOWER(p.endereco) LIKE LOWER(CONCAT('%', :endereco, '%')) ORDER BY p.dataCriacao DESC")
    List<PontoVenda> findByUsuarioAndEnderecoContainingIgnoreCase(@Param("usuario") Usuario usuario, @Param("endereco") String endereco);

    /**
     * Busca PDVs por cidade
     * @param usuario Usuário proprietário dos PDVs
     * @param cidade Nome da cidade
     * @return Lista de PDVs da cidade especificada
     */
    List<PontoVenda> findByUsuarioAndCidadeOrderByDataCriacaoDesc(Usuario usuario, String cidade);

    /**
     * Busca PDVs por estado
     * @param usuario Usuário proprietário dos PDVs
     * @param estado Sigla do estado (UF)
     * @return Lista de PDVs do estado especificado
     */
    List<PontoVenda> findByUsuarioAndEstadoOrderByDataCriacaoDesc(Usuario usuario, String estado);

    /**
     * Busca PDVs por bairro
     * @param usuario Usuário proprietário dos PDVs
     * @param bairro Nome do bairro
     * @return Lista de PDVs do bairro especificado
     */
    @Query("SELECT p FROM PontoVenda p WHERE p.usuario = :usuario AND LOWER(p.bairro) LIKE LOWER(CONCAT('%', :bairro, '%')) ORDER BY p.dataCriacao DESC")
    List<PontoVenda> findByUsuarioAndBairroContainingIgnoreCase(@Param("usuario") Usuario usuario, @Param("bairro") String bairro);

    /**
     * Busca PDV por nome
     * @param usuario Usuário proprietário dos PDVs
     * @param nome Nome ou parte do nome do PDV
     * @return Lista de PDVs que contenham o texto no nome
     */
    @Query("SELECT p FROM PontoVenda p WHERE p.usuario = :usuario AND LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%')) ORDER BY p.dataCriacao DESC")
    List<PontoVenda> findByUsuarioAndNomeContainingIgnoreCase(@Param("usuario") Usuario usuario, @Param("nome") String nome);

    /**
     * Busca tipos únicos de PDVs do usuário
     * @param usuario Usuário proprietário dos PDVs
     * @return Lista de tipos únicos
     */
    @Query("SELECT DISTINCT p.tipo FROM PontoVenda p WHERE p.usuario = :usuario AND p.tipo IS NOT NULL ORDER BY p.tipo")
    List<String> findDistinctTiposByUsuario(@Param("usuario") Usuario usuario);

    /**
     * Busca cidades únicas onde o usuário possui PDVs
     * @param usuario Usuário proprietário dos PDVs
     * @return Lista de cidades únicas
     */
    @Query("SELECT DISTINCT p.cidade FROM PontoVenda p WHERE p.usuario = :usuario AND p.cidade IS NOT NULL ORDER BY p.cidade")
    List<String> findDistinctCidadesByUsuario(@Param("usuario") Usuario usuario);

    /**
     * Conta PDVs por tipo
     * @param usuario Usuário proprietário dos PDVs
     * @param tipo Tipo do PDV
     * @return Número de PDVs do tipo especificado
     */
    Long countByUsuarioAndTipo(Usuario usuario, String tipo);

    /**
     * Conta PDVs ativos do usuário
     * @param usuario Usuário proprietário dos PDVs
     * @return Número de PDVs ativos
     */
    Long countByUsuarioAndAtivo(Usuario usuario, Boolean ativo);
}
