package com.fiap.challenge.service;

import com.fiap.challenge.dto.CreatePDVRequest;
import com.fiap.challenge.dto.PDVResponse;
import com.fiap.challenge.model.PontoVenda;
import com.fiap.challenge.model.Usuario;
import com.fiap.challenge.repository.PontoVendaRepository;
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
public class PontoVendaService {

    private final PontoVendaRepository pontoVendaRepository;
    private final UsuarioRepository usuarioRepository;

    /**
     * Recupera todos os pontos de venda do usuário logado
     * @return Lista de PDVResponse
     */
    @Transactional(readOnly = true)
    public List<PDVResponse> getAllPDVs() {
        Usuario usuario = getUsuarioLogado();
        List<PontoVenda> pdvs = pontoVendaRepository.findByUsuarioOrderByDataCriacaoDesc(usuario);

        return pdvs.stream()
                .map(PDVResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca um PDV específico por ID
     * @param id ID do PDV
     * @return PDVResponse com os dados do PDV
     */
    @Transactional(readOnly = true)
    public PDVResponse getPDVById(Long id) {
        Usuario usuario = getUsuarioLogado();

        PontoVenda pdv = pontoVendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ponto de venda não encontrado"));

        // Verifica se o PDV pertence ao usuário logado
        if (!pdv.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("Acesso negado: este ponto de venda não pertence ao usuário logado");
        }

        return new PDVResponse(pdv);
    }

    /**
     * Busca PDVs filtrados por endereço
     * @param endereco Endereço ou parte do endereço
     * @return Lista de PDVResponse filtradas por endereço
     */
    @Transactional(readOnly = true)
    public List<PDVResponse> getPDVsByEndereco(String endereco) {
        Usuario usuario = getUsuarioLogado();
        List<PontoVenda> pdvs = pontoVendaRepository.findByUsuarioAndEnderecoContainingIgnoreCase(usuario, endereco);

        return pdvs.stream()
                .map(PDVResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca PDVs filtrados por nome
     * @param nome Nome ou parte do nome do PDV
     * @return Lista de PDVResponse filtradas por nome
     */
    @Transactional(readOnly = true)
    public List<PDVResponse> getPDVsByNome(String nome) {
        Usuario usuario = getUsuarioLogado();
        List<PontoVenda> pdvs = pontoVendaRepository.findByUsuarioAndNomeContainingIgnoreCase(usuario, nome);

        return pdvs.stream()
                .map(PDVResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca PDVs filtrados por cidade
     * @param cidade Cidade
     * @return Lista de PDVResponse filtradas por cidade
     */
    @Transactional(readOnly = true)
    public List<PDVResponse> getPDVsByCidade(String cidade) {
        Usuario usuario = getUsuarioLogado();
        List<PontoVenda> pdvs = pontoVendaRepository.findByUsuarioAndCidadeOrderByDataCriacaoDesc(usuario, cidade);

        return pdvs.stream()
                .map(PDVResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca PDVs filtrados por estado
     * @param estado Sigla do estado (UF)
     * @return Lista de PDVResponse filtradas por estado
     */
    @Transactional(readOnly = true)
    public List<PDVResponse> getPDVsByEstado(String estado) {
        Usuario usuario = getUsuarioLogado();
        List<PontoVenda> pdvs = pontoVendaRepository.findByUsuarioAndEstadoOrderByDataCriacaoDesc(usuario, estado);

        return pdvs.stream()
                .map(PDVResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca PDVs filtrados por bairro
     * @param bairro Bairro
     * @return Lista de PDVResponse filtradas por bairro
     */
    @Transactional(readOnly = true)
    public List<PDVResponse> getPDVsByBairro(String bairro) {
        Usuario usuario = getUsuarioLogado();
        List<PontoVenda> pdvs = pontoVendaRepository.findByUsuarioAndBairroContainingIgnoreCase(usuario, bairro);

        return pdvs.stream()
                .map(PDVResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca PDVs filtrados por tipo
     * @param tipo Tipo do PDV (Matriz, Filial, Franquia, Quiosque)
     * @return Lista de PDVResponse filtradas por tipo
     */
    @Transactional(readOnly = true)
    public List<PDVResponse> getPDVsByTipo(String tipo) {
        Usuario usuario = getUsuarioLogado();
        List<PontoVenda> pdvs = pontoVendaRepository.findByUsuarioAndTipoOrderByDataCriacaoDesc(usuario, tipo);

        return pdvs.stream()
                .map(PDVResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca PDVs filtrados por status ativo/inativo
     * @param ativo Status do PDV
     * @return Lista de PDVResponse filtradas por status
     */
    @Transactional(readOnly = true)
    public List<PDVResponse> getPDVsByAtivo(Boolean ativo) {
        Usuario usuario = getUsuarioLogado();
        List<PontoVenda> pdvs = pontoVendaRepository.findByUsuarioAndAtivoOrderByDataCriacaoDesc(usuario, ativo);

        return pdvs.stream()
                .map(PDVResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Lista tipos disponíveis de PDVs
     * @return Lista de tipos únicos
     */
    @Transactional(readOnly = true)
    public List<String> getTiposDisponiveis() {
        Usuario usuario = getUsuarioLogado();
        return pontoVendaRepository.findDistinctTiposByUsuario(usuario);
    }

    /**
     * Lista cidades onde o usuário possui PDVs
     * @return Lista de cidades únicas
     */
    @Transactional(readOnly = true)
    public List<String> getCidadesDisponiveis() {
        Usuario usuario = getUsuarioLogado();
        return pontoVendaRepository.findDistinctCidadesByUsuario(usuario);
    }

    /**
     * Cria um novo PDV
     * @param request Dados do PDV
     * @return PDVResponse com o PDV criado
     */
    @Transactional
    public PDVResponse createPDV(CreatePDVRequest request) {
        Usuario usuario = getUsuarioLogado();

        PontoVenda pdv = new PontoVenda();
        pdv.setNome(request.getNome());
        pdv.setEndereco(request.getEndereco());
        pdv.setBairro(request.getBairro());
        pdv.setCidade(request.getCidade());
        pdv.setEstado(request.getEstado());
        pdv.setCep(request.getCep());
        pdv.setTelefone(request.getTelefone());
        pdv.setEmail(request.getEmail());
        pdv.setResponsavel(request.getResponsavel());
        pdv.setTipo(request.getTipo());
        pdv.setObservacoes(request.getObservacoes());
        pdv.setAtivo(request.getAtivo() != null ? request.getAtivo() : true);
        pdv.setUsuario(usuario);

        PontoVenda pdvSalvo = pontoVendaRepository.save(pdv);
        return new PDVResponse(pdvSalvo);
    }

    /**
     * Atualiza um PDV existente
     * @param id ID do PDV
     * @param request Dados para atualização
     * @return PDVResponse com o PDV atualizado
     */
    @Transactional
    public PDVResponse updatePDV(Long id, CreatePDVRequest request) {
        Usuario usuario = getUsuarioLogado();

        PontoVenda pdv = pontoVendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ponto de venda não encontrado"));

        // Verifica se o PDV pertence ao usuário logado
        if (!pdv.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("Acesso negado: este ponto de venda não pertence ao usuário logado");
        }

        // Atualiza os campos
        pdv.setNome(request.getNome());
        pdv.setEndereco(request.getEndereco());
        pdv.setBairro(request.getBairro());
        pdv.setCidade(request.getCidade());
        pdv.setEstado(request.getEstado());
        pdv.setCep(request.getCep());
        pdv.setTelefone(request.getTelefone());
        pdv.setEmail(request.getEmail());
        pdv.setResponsavel(request.getResponsavel());
        pdv.setTipo(request.getTipo());
        pdv.setObservacoes(request.getObservacoes());
        if (request.getAtivo() != null) {
            pdv.setAtivo(request.getAtivo());
        }

        PontoVenda pdvAtualizado = pontoVendaRepository.save(pdv);
        return new PDVResponse(pdvAtualizado);
    }

    /**
     * Deleta um PDV
     * @param id ID do PDV
     */
    @Transactional
    public void deletePDV(Long id) {
        Usuario usuario = getUsuarioLogado();

        PontoVenda pdv = pontoVendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ponto de venda não encontrado"));

        // Verifica se o PDV pertence ao usuário logado
        if (!pdv.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("Acesso negado: este ponto de venda não pertence ao usuário logado");
        }

        pontoVendaRepository.delete(pdv);
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
