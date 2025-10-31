package com.fiap.challenge.repository;

import com.fiap.challenge.model.Usuario;
import com.fiap.challenge.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

    /**
     * Busca todas as vendas de um usuário específico
     * @param usuario Usuário proprietário das vendas
     * @return Lista de vendas do usuário
     */
    List<Venda> findByUsuarioOrderByDataVendaDesc(Usuario usuario);

    /**
     * Busca vendas de um usuário filtradas por tipo
     * @param usuario Usuário proprietário das vendas
     * @param tipo Tipo da venda (Eletrônicos, Roupas, etc)
     * @return Lista de vendas filtradas por tipo
     */
    List<Venda> findByUsuarioAndTipoOrderByDataVendaDesc(Usuario usuario, String tipo);

    /**
     * Busca vendas de um usuário filtradas por status
     * @param usuario Usuário proprietário das vendas
     * @param status Status da venda (Concluída, Pendente, Cancelada)
     * @return Lista de vendas filtradas por status
     */
    List<Venda> findByUsuarioAndStatusOrderByDataVendaDesc(Usuario usuario, String status);

    /**
     * Calcula o total de vendas de um usuário
     * @param usuario Usuário proprietário das vendas
     * @return Valor total das vendas
     */
    @Query("SELECT COALESCE(SUM(v.valorTotal), 0) FROM Venda v WHERE v.usuario = :usuario")
    BigDecimal calcularTotalVendas(@Param("usuario") Usuario usuario);

    /**
     * Calcula o total de vendas concluídas de um usuário
     * @param usuario Usuário proprietário das vendas
     * @return Valor total das vendas concluídas
     */
    @Query("SELECT COALESCE(SUM(v.valorTotal), 0) FROM Venda v WHERE v.usuario = :usuario AND v.status = 'Concluída'")
    BigDecimal calcularTotalVendasConcluidas(@Param("usuario") Usuario usuario);

    /**
     * Conta o número total de vendas de um usuário
     * @param usuario Usuário proprietário das vendas
     * @return Número total de vendas
     */
    Long countByUsuario(Usuario usuario);

    /**
     * Conta vendas por status de um usuário
     * @param usuario Usuário proprietário das vendas
     * @param status Status da venda
     * @return Número de vendas com o status especificado
     */
    Long countByUsuarioAndStatus(Usuario usuario, String status);

    /**
     * Busca tipos de vendas únicos de um usuário
     * @param usuario Usuário proprietário das vendas
     * @return Lista de tipos únicos
     */
    @Query("SELECT DISTINCT v.tipo FROM Venda v WHERE v.usuario = :usuario ORDER BY v.tipo")
    List<String> findDistinctTiposByUsuario(@Param("usuario") Usuario usuario);

    /**
     * Calcula total de vendas por tipo
     * @param usuario Usuário proprietário das vendas
     * @param tipo Tipo da venda
     * @return Valor total das vendas do tipo especificado
     */
    @Query("SELECT COALESCE(SUM(v.valorTotal), 0) FROM Venda v WHERE v.usuario = :usuario AND v.tipo = :tipo")
    BigDecimal calcularTotalVendasPorTipo(@Param("usuario") Usuario usuario, @Param("tipo") String tipo);
}
