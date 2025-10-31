package com.fiap.challenge.service;

import com.fiap.challenge.dto.CardInfoResponse;
import com.fiap.challenge.dto.CreateVendaRequest;
import com.fiap.challenge.dto.VendaPorTipoResponse;
import com.fiap.challenge.dto.VendaResponse;
import com.fiap.challenge.model.Usuario;
import com.fiap.challenge.model.Venda;
import com.fiap.challenge.repository.UsuarioRepository;
import com.fiap.challenge.repository.VendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final VendaRepository vendaRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public CardInfoResponse getCardInfo() {
        Usuario usuario = getUsuarioLogado();

        BigDecimal totalVendas = vendaRepository.calcularTotalVendas(usuario);
        BigDecimal totalVendasConcluidas = vendaRepository.calcularTotalVendasConcluidas(usuario);
        Long quantidadeVendas = vendaRepository.countByUsuario(usuario);
        Long vendasConcluidas = vendaRepository.countByUsuarioAndStatus(usuario, "Concluída");
        Long vendasPendentes = vendaRepository.countByUsuarioAndStatus(usuario, "Pendente");
        Long vendasCanceladas = vendaRepository.countByUsuarioAndStatus(usuario, "Cancelada");

        BigDecimal ticketMedio = BigDecimal.ZERO;
        if (quantidadeVendas > 0) {
            ticketMedio = totalVendas.divide(
                    BigDecimal.valueOf(quantidadeVendas),
                    2,
                    RoundingMode.HALF_UP
            );
        }

        return new CardInfoResponse(
                totalVendas,
                totalVendasConcluidas,
                quantidadeVendas,
                vendasConcluidas,
                vendasPendentes,
                vendasCanceladas,
                ticketMedio
        );
    }

    @Transactional(readOnly = true)
    public List<VendaResponse> getVendasUsuario() {
        Usuario usuario = getUsuarioLogado();
        List<Venda> vendas = vendaRepository.findByUsuarioOrderByDataVendaDesc(usuario);

        return vendas.stream()
                .map(VendaResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<VendaResponse> getVendasPorTipo(String tipo) {
        Usuario usuario = getUsuarioLogado();
        List<Venda> vendas = vendaRepository.findByUsuarioAndTipoOrderByDataVendaDesc(usuario, tipo);

        return vendas.stream()
                .map(VendaResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<VendaResponse> getVendasPorStatus(String status) {
        Usuario usuario = getUsuarioLogado();
        List<Venda> vendas = vendaRepository.findByUsuarioAndStatusOrderByDataVendaDesc(usuario, status);

        return vendas.stream()
                .map(VendaResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<VendaPorTipoResponse> getVendasAgrupadasPorTipo() {
        Usuario usuario = getUsuarioLogado();
        List<String> tipos = vendaRepository.findDistinctTiposByUsuario(usuario);

        return tipos.stream()
                .map(tipo -> {
                    BigDecimal valorTotal = vendaRepository.calcularTotalVendasPorTipo(usuario, tipo);
                    int quantidade = vendaRepository.findByUsuarioAndTipoOrderByDataVendaDesc(usuario, tipo).size();
                    return new VendaPorTipoResponse(tipo, valorTotal, (long) quantidade);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public VendaResponse criarVenda(CreateVendaRequest request) {
        Usuario usuario = getUsuarioLogado();

        Venda venda = new Venda();
        venda.setProduto(request.getProduto());
        venda.setQuantidade(request.getQuantidade());
        venda.setValor(request.getValor());
        venda.setTipo(request.getTipo());
        venda.setStatus(request.getStatus());
        venda.setObservacoes(request.getObservacoes());
        venda.setUsuario(usuario);

        Venda vendaSalva = vendaRepository.save(venda);
        return new VendaResponse(vendaSalva);
    }

    @Transactional(readOnly = true)
    public List<String> getTiposDisponiveis() {
        Usuario usuario = getUsuarioLogado();
        return vendaRepository.findDistinctTiposByUsuario(usuario);
    }

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
