package com.fiap.challenge.service;

import com.fiap.challenge.dto.VendaDetalhadaResponse;
import com.fiap.challenge.model.Usuario;
import com.fiap.challenge.model.Venda;
import com.fiap.challenge.repository.UsuarioRepository;
import com.fiap.challenge.repository.VendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VendaService {

    private final VendaRepository vendaRepository;
    private final UsuarioRepository usuarioRepository;

    /**
     * Recupera todas as vendas do usuário logado com informações detalhadas
     * @return Lista de VendaDetalhadaResponse
     */
    @Transactional(readOnly = true)
    public List<VendaDetalhadaResponse> getAllVendas() {
        Usuario usuario = getUsuarioLogado();
        List<Venda> vendas = vendaRepository.findByUsuarioOrderByDataVendaDesc(usuario);

        return vendas.stream()
                .map(VendaDetalhadaResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca uma venda específica por ID
     * @param id ID da venda
     * @return VendaDetalhadaResponse com os dados da venda
     */
    @Transactional(readOnly = true)
    public VendaDetalhadaResponse getVendaById(Long id) {
        Usuario usuario = getUsuarioLogado();

        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada"));

        // Verifica se a venda pertence ao usuário logado
        if (!venda.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("Acesso negado: esta venda não pertence ao usuário logado");
        }

        return new VendaDetalhadaResponse(venda);
    }

    /**
     * Busca vendas filtradas por múltiplos IDs
     * @param ids Lista de IDs de vendas
     * @return Lista de VendaDetalhadaResponse
     */
    @Transactional(readOnly = true)
    public List<VendaDetalhadaResponse> getVendasByIds(List<Long> ids) {
        Usuario usuario = getUsuarioLogado();

        List<Venda> vendas = vendaRepository.findAllById(ids);

        // Filtra apenas vendas do usuário logado
        return vendas.stream()
                .filter(venda -> venda.getUsuario().getId().equals(usuario.getId()))
                .map(VendaDetalhadaResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Atualiza uma venda existente
     * @param id ID da venda
     * @param produto Nome do produto
     * @param quantidade Quantidade
     * @param valor Valor unitário
     * @param tipo Tipo da venda
     * @param status Status da venda
     * @param observacoes Observações
     * @return VendaDetalhadaResponse com a venda atualizada
     */
    @Transactional
    public VendaDetalhadaResponse updateVenda(Long id, String produto, Integer quantidade,
                                             java.math.BigDecimal valor, String tipo,
                                             String status, String observacoes) {
        Usuario usuario = getUsuarioLogado();

        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada"));

        // Verifica se a venda pertence ao usuário logado
        if (!venda.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("Acesso negado: esta venda não pertence ao usuário logado");
        }

        // Atualiza os campos
        if (produto != null) venda.setProduto(produto);
        if (quantidade != null) venda.setQuantidade(quantidade);
        if (valor != null) venda.setValor(valor);
        if (tipo != null) venda.setTipo(tipo);
        if (status != null) venda.setStatus(status);
        if (observacoes != null) venda.setObservacoes(observacoes);

        Venda vendaAtualizada = vendaRepository.save(venda);
        return new VendaDetalhadaResponse(vendaAtualizada);
    }

    /**
     * Deleta uma venda
     * @param id ID da venda
     */
    @Transactional
    public void deleteVenda(Long id) {
        Usuario usuario = getUsuarioLogado();

        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada"));

        // Verifica se a venda pertence ao usuário logado
        if (!venda.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("Acesso negado: esta venda não pertence ao usuário logado");
        }

        vendaRepository.delete(venda);
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
