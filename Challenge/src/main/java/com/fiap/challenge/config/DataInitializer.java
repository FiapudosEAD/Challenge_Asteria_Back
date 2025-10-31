package com.fiap.challenge.config;

import com.fiap.challenge.model.PontoVenda;
import com.fiap.challenge.model.Produto;
import com.fiap.challenge.model.Usuario;
import com.fiap.challenge.model.Venda;
import com.fiap.challenge.repository.PontoVendaRepository;
import com.fiap.challenge.repository.ProdutoRepository;
import com.fiap.challenge.repository.UsuarioRepository;
import com.fiap.challenge.repository.VendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Classe para inicializar dados de exemplo no banco de dados
 * Útil para testes e desenvolvimento
 */
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;
    private final PontoVendaRepository pontoVendaRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Verifica se já existem usuários no banco
        if (usuarioRepository.count() > 0) {
            System.out.println("Banco de dados já contém usuários. Pulando inicialização de dados.");
            return;
        }

        System.out.println("Inicializando dados de exemplo...");

        // Cria usuário de exemplo
        Usuario usuario = new Usuario();
        usuario.setNome("Usuario Teste");
        usuario.setEmail("teste@fiap.com");
        usuario.setSenha(passwordEncoder.encode("senha123"));
        usuario.setAtivo(true);
        usuario = usuarioRepository.save(usuario);

        // Cria vendas de exemplo
        criarVenda(usuario, "Notebook Dell", 2, new BigDecimal("3500.00"), "Eletrônicos", "Concluída", "Venda realizada com sucesso", LocalDateTime.now().minusDays(5));
        criarVenda(usuario, "Mouse Logitech", 5, new BigDecimal("89.90"), "Eletrônicos", "Concluída", null, LocalDateTime.now().minusDays(4));
        criarVenda(usuario, "Teclado Mecânico", 3, new BigDecimal("450.00"), "Eletrônicos", "Concluída", "Cliente solicitou nota fiscal", LocalDateTime.now().minusDays(3));
        criarVenda(usuario, "Livro Java Completo", 10, new BigDecimal("89.90"), "Livros", "Concluída", null, LocalDateTime.now().minusDays(2));
        criarVenda(usuario, "Camisa Polo", 15, new BigDecimal("79.90"), "Roupas", "Pendente", "Aguardando confirmação do pagamento", LocalDateTime.now().minusDays(1));
        criarVenda(usuario, "Monitor LG 27''", 1, new BigDecimal("1200.00"), "Eletrônicos", "Pendente", null, LocalDateTime.now().minusHours(12));
        criarVenda(usuario, "Cadeira Gamer", 2, new BigDecimal("899.00"), "Móveis", "Concluída", "Entrega agendada", LocalDateTime.now().minusHours(6));
        criarVenda(usuario, "HD Externo 1TB", 4, new BigDecimal("350.00"), "Eletrônicos", "Cancelada", "Cliente desistiu da compra", LocalDateTime.now().minusHours(3));
        criarVenda(usuario, "Fone Bluetooth", 8, new BigDecimal("120.00"), "Eletrônicos", "Concluída", null, LocalDateTime.now().minusHours(1));
        criarVenda(usuario, "Mesa de Escritório", 1, new BigDecimal("650.00"), "Móveis", "Concluída", "Produto entregue", LocalDateTime.now().minusMinutes(30));
        criarVenda(usuario, "Kit 3 Livros Técnicos", 5, new BigDecimal("250.00"), "Livros", "Concluída", null, LocalDateTime.now().minusMinutes(15));
        criarVenda(usuario, "Calça Jeans", 20, new BigDecimal("129.90"), "Roupas", "Concluída", "Venda em atacado", LocalDateTime.now().minusMinutes(5));

        // Cria produtos de exemplo
        criarProduto(usuario, "PROD001", "Notebook Dell Inspiron 15", "Notebook 15.6 polegadas, Intel Core i5, 8GB RAM, SSD 256GB", "Eletrônicos", new BigDecimal("3500.00"), 15, "Dell", "unidade", true, LocalDateTime.now().minusDays(10));
        criarProduto(usuario, "PROD002", "Mouse Logitech MX Master", "Mouse sem fio ergonômico com 7 botões programáveis", "Eletrônicos", new BigDecimal("350.00"), 50, "Logitech", "unidade", true, LocalDateTime.now().minusDays(9));
        criarProduto(usuario, "PROD003", "Teclado Mecânico RGB", "Teclado mecânico gamer com iluminação RGB personalizável", "Eletrônicos", new BigDecimal("450.00"), 30, "Redragon", "unidade", true, LocalDateTime.now().minusDays(8));
        criarProduto(usuario, "PROD004", "Monitor LG 27 UltraWide", "Monitor 27 polegadas IPS Full HD 75Hz", "Eletrônicos", new BigDecimal("1200.00"), 8, "LG", "unidade", true, LocalDateTime.now().minusDays(7));
        criarProduto(usuario, "PROD005", "Webcam Logitech C920", "Webcam Full HD 1080p com microfone embutido", "Eletrônicos", new BigDecimal("450.00"), 25, "Logitech", "unidade", true, LocalDateTime.now().minusDays(6));
        criarProduto(usuario, "PROD006", "Cadeira Gamer DT3 Sports", "Cadeira gamer ergonômica com ajuste de altura e reclinação", "Móveis", new BigDecimal("899.00"), 12, "DT3 Sports", "unidade", true, LocalDateTime.now().minusDays(5));
        criarProduto(usuario, "PROD007", "Mesa de Escritório", "Mesa de escritório 120x60cm em MDF", "Móveis", new BigDecimal("650.00"), 5, "Notável", "unidade", true, LocalDateTime.now().minusDays(4));
        criarProduto(usuario, "PROD008", "Livro: Clean Code", "Livro sobre boas práticas de programação", "Livros", new BigDecimal("89.90"), 100, "Pearson", "unidade", true, LocalDateTime.now().minusDays(3));
        criarProduto(usuario, "PROD009", "Livro: Design Patterns", "Livro sobre padrões de projeto", "Livros", new BigDecimal("95.00"), 80, "Addison-Wesley", "unidade", true, LocalDateTime.now().minusDays(2));
        criarProduto(usuario, "PROD010", "HD Externo 1TB", "HD externo portátil USB 3.0", "Eletrônicos", new BigDecimal("350.00"), 20, "Seagate", "unidade", true, LocalDateTime.now().minusDays(1));
        criarProduto(usuario, "PROD011", "SSD 480GB", "SSD SATA III 480GB", "Eletrônicos", new BigDecimal("280.00"), 35, "Kingston", "unidade", true, LocalDateTime.now().minusHours(12));
        criarProduto(usuario, "PROD012", "Fone Bluetooth JBL", "Fone de ouvido sem fio com cancelamento de ruído", "Eletrônicos", new BigDecimal("299.00"), 40, "JBL", "unidade", true, LocalDateTime.now().minusHours(6));
        criarProduto(usuario, "PROD013", "Mousepad Gamer XL", "Mousepad gamer extra grande 90x40cm", "Eletrônicos", new BigDecimal("59.90"), 60, "HyperX", "unidade", true, LocalDateTime.now().minusHours(3));
        criarProduto(usuario, "PROD014", "Hub USB 3.0 4 Portas", "Hub USB 3.0 com 4 portas", "Eletrônicos", new BigDecimal("45.00"), 3, "i2GO", "unidade", true, LocalDateTime.now().minusHours(1));
        criarProduto(usuario, "PROD015", "Cabo HDMI 2.0 2m", "Cabo HDMI 2.0 premium 2 metros", "Eletrônicos", new BigDecimal("35.00"), 150, "Elg", "unidade", true, LocalDateTime.now().minusMinutes(30));

        // Cria pontos de venda de exemplo
        criarPDV(usuario, "Loja Centro", "Av. Paulista, 1000", "Bela Vista", "São Paulo", "SP", "01310-100", "(11) 3000-1000", "centro@loja.com", "João Silva", "Matriz", true, LocalDateTime.now().minusDays(15));
        criarPDV(usuario, "Loja Shopping", "Rua Augusta, 2690 - Shopping Center Norte", "Vila Guilherme", "São Paulo", "SP", "02115-000", "(11) 3000-2000", "shopping@loja.com", "Maria Santos", "Filial", true, LocalDateTime.now().minusDays(14));
        criarPDV(usuario, "Loja Outlet", "Av. das Nações Unidas, 14401", "Vila Gertrudes", "São Paulo", "SP", "04794-000", "(11) 3000-3000", "outlet@loja.com", "Pedro Costa", "Filial", true, LocalDateTime.now().minusDays(13));
        criarPDV(usuario, "Franquia Moema", "Av. Moema, 170", "Moema", "São Paulo", "SP", "04077-020", "(11) 3000-4000", "moema@loja.com", "Ana Paula", "Franquia", true, LocalDateTime.now().minusDays(12));
        criarPDV(usuario, "Quiosque Aeroporto", "Rodovia Hélio Smidt, s/n", "Cumbica", "Guarulhos", "SP", "07190-100", "(11) 3000-5000", "aeroporto@loja.com", "Carlos Mendes", "Quiosque", true, LocalDateTime.now().minusDays(11));
        criarPDV(usuario, "Loja Campinas", "Av. Norte-Sul, 500", "Centro", "Campinas", "SP", "13015-900", "(19) 3000-1000", "campinas@loja.com", "Juliana Lima", "Filial", true, LocalDateTime.now().minusDays(10));
        criarPDV(usuario, "Loja Rio", "Av. Atlântica, 1702", "Copacabana", "Rio de Janeiro", "RJ", "22021-000", "(21) 3000-1000", "rio@loja.com", "Roberto Alves", "Filial", true, LocalDateTime.now().minusDays(9));
        criarPDV(usuario, "Franquia BH", "Av. Afonso Pena, 1270", "Centro", "Belo Horizonte", "MG", "30130-002", "(31) 3000-1000", "bh@loja.com", "Fernanda Souza", "Franquia", true, LocalDateTime.now().minusDays(8));
        criarPDV(usuario, "Loja Curitiba", "Rua XV de Novembro, 999", "Centro", "Curitiba", "PR", "80020-310", "(41) 3000-1000", "curitiba@loja.com", "Lucas Ferreira", "Filial", true, LocalDateTime.now().minusDays(7));
        criarPDV(usuario, "Quiosque Shopping ABC", "Av. Pereira Barreto, 42", "Baeta Neves", "São Bernardo do Campo", "SP", "09751-000", "(11) 3000-6000", "abc@loja.com", "Patricia Rodrigues", "Quiosque", true, LocalDateTime.now().minusDays(6));

        System.out.println("Dados de exemplo criados com sucesso!");
        System.out.println("- 12 vendas");
        System.out.println("- 15 produtos");
        System.out.println("- 10 pontos de venda");
        System.out.println("Login de teste: teste@fiap.com / senha123");
    }

    private void criarVenda(Usuario usuario, String produto, Integer quantidade, BigDecimal valor,
                           String tipo, String status, String observacoes, LocalDateTime dataVenda) {
        Venda venda = new Venda();
        venda.setUsuario(usuario);
        venda.setProduto(produto);
        venda.setQuantidade(quantidade);
        venda.setValor(valor);
        venda.setTipo(tipo);
        venda.setStatus(status);
        venda.setObservacoes(observacoes);
        venda.setDataVenda(dataVenda);
        vendaRepository.save(venda);
    }

    private void criarProduto(Usuario usuario, String codigo, String nome, String descricao,
                             String categoria, BigDecimal preco, Integer estoque,
                             String fabricante, String unidadeMedida, Boolean ativo,
                             LocalDateTime dataCriacao) {
        Produto produto = new Produto();
        produto.setUsuario(usuario);
        produto.setCodigo(codigo);
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setCategoria(categoria);
        produto.setPreco(preco);
        produto.setEstoque(estoque);
        produto.setFabricante(fabricante);
        produto.setUnidadeMedida(unidadeMedida);
        produto.setAtivo(ativo);
        produtoRepository.save(produto);
    }

    private void criarPDV(Usuario usuario, String nome, String endereco, String bairro,
                         String cidade, String estado, String cep, String telefone,
                         String email, String responsavel, String tipo, Boolean ativo,
                         LocalDateTime dataCriacao) {
        PontoVenda pdv = new PontoVenda();
        pdv.setUsuario(usuario);
        pdv.setNome(nome);
        pdv.setEndereco(endereco);
        pdv.setBairro(bairro);
        pdv.setCidade(cidade);
        pdv.setEstado(estado);
        pdv.setCep(cep);
        pdv.setTelefone(telefone);
        pdv.setEmail(email);
        pdv.setResponsavel(responsavel);
        pdv.setTipo(tipo);
        pdv.setAtivo(ativo);
        pontoVendaRepository.save(pdv);
    }
}
