# Challenge FIAP - Backend API

API REST desenvolvida em Java com Spring Boot para sistema de autenticação de usuários e gerenciamento de vendas.

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.2.0
- Spring Security
- Spring Data JPA
- JWT (JSON Web Tokens)
- H2 Database (desenvolvimento)
- Maven
- Lombok

## Funcionalidades

### Autenticação
- Registro de novos usuários
- Login com autenticação JWT
- Recuperação de dados do usuário logado
- Validação de dados de entrada
- Tratamento de erros

### Dashboard de Vendas
- Controle completo de vendas
- Estatísticas e métricas (cards informativos)
- Filtros por tipo e status de venda
- Agrupamento de vendas por categoria
- Dados de exemplo pré-carregados para testes

## Endpoints

### Autenticação

#### 1. Registrar Novo Usuário
```
POST /api/auth/register
Content-Type: application/json

{
  "nome": "João Silva",
  "email": "joao@example.com",
  "senha": "senha123"
}
```

**Resposta de Sucesso (201 Created):**
```json
{
  "id": 1,
  "nome": "João Silva",
  "email": "joao@example.com",
  "dataCriacao": "2025-10-29T10:00:00",
  "ativo": true
}
```

#### 2. Login
```
POST /api/auth/login
Content-Type: application/json

{
  "email": "joao@example.com",
  "senha": "senha123"
}
```

**Resposta de Sucesso (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tipo": "Bearer",
  "usuario": {
    "id": 1,
    "nome": "João Silva",
    "email": "joao@example.com",
    "dataCriacao": "2025-10-29T10:00:00",
    "ativo": true
  }
}
```

#### 3. Recuperar Usuário Logado
```
GET /api/auth/me
Authorization: Bearer {token}
```

**Resposta de Sucesso (200 OK):**
```json
{
  "id": 1,
  "nome": "João Silva",
  "email": "joao@example.com",
  "dataCriacao": "2025-10-29T10:00:00",
  "ativo": true
}
```

#### 4. Health Check
```
GET /api/auth/health
```

**Resposta:**
```
API está funcionando!
```

### Dashboard

**IMPORTANTE:** Todos os endpoints do Dashboard requerem autenticação. Adicione o token JWT no header:
```
Authorization: Bearer {seu-token-aqui}
```

#### 1. Recuperar Informações dos Cards
```
GET /api/dashboard/cards
Authorization: Bearer {token}
```

**Resposta de Sucesso (200 OK):**
```json
{
  "totalVendas": 25000.00,
  "totalVendasConcluidas": 22000.00,
  "quantidadeVendas": 12,
  "vendasConcluidas": 9,
  "vendasPendentes": 2,
  "vendasCanceladas": 1,
  "ticketMedio": 2083.33
}
```

#### 2. Recuperar Todas as Vendas do Usuário
```
GET /api/dashboard/vendas
Authorization: Bearer {token}
```

**Resposta de Sucesso (200 OK):**
```json
[
  {
    "id": 1,
    "produto": "Notebook Dell",
    "quantidade": 2,
    "valor": 3500.00,
    "valorTotal": 7000.00,
    "tipo": "Eletrônicos",
    "status": "Concluída",
    "dataVenda": "2025-10-24T10:00:00",
    "observacoes": "Venda realizada com sucesso"
  },
  {
    "id": 2,
    "produto": "Mouse Logitech",
    "quantidade": 5,
    "valor": 89.90,
    "valorTotal": 449.50,
    "tipo": "Eletrônicos",
    "status": "Concluída",
    "dataVenda": "2025-10-25T14:30:00",
    "observacoes": null
  }
]
```

#### 3. Filtrar Vendas por Tipo
```
GET /api/dashboard/vendas/tipo/{tipo}
Authorization: Bearer {token}
```

**Exemplos de tipos:** Eletrônicos, Roupas, Livros, Móveis, Alimentos

**Exemplo de uso:**
```
GET /api/dashboard/vendas/tipo/Eletrônicos
```

**Resposta:** Array de vendas filtradas por tipo

#### 4. Filtrar Vendas por Status
```
GET /api/dashboard/vendas/status/{status}
Authorization: Bearer {token}
```

**Status disponíveis:** Concluída, Pendente, Cancelada

**Exemplo de uso:**
```
GET /api/dashboard/vendas/status/Concluída
```

**Resposta:** Array de vendas filtradas por status

#### 5. Obter Estatísticas por Tipo
```
GET /api/dashboard/vendas/por-tipo
Authorization: Bearer {token}
```

**Resposta de Sucesso (200 OK):**
```json
[
  {
    "tipo": "Eletrônicos",
    "valorTotal": 15000.00,
    "quantidade": 8
  },
  {
    "tipo": "Livros",
    "valorTotal": 2500.00,
    "quantidade": 3
  },
  {
    "tipo": "Roupas",
    "valorTotal": 3500.00,
    "quantidade": 2
  }
]
```

#### 6. Criar Nova Venda
```
POST /api/dashboard/vendas
Authorization: Bearer {token}
Content-Type: application/json

{
  "produto": "Teclado Mecânico",
  "quantidade": 3,
  "valor": 450.00,
  "tipo": "Eletrônicos",
  "status": "Concluída",
  "observacoes": "Cliente solicitou nota fiscal"
}
```

**Resposta de Sucesso (201 Created):**
```json
{
  "id": 13,
  "produto": "Teclado Mecânico",
  "quantidade": 3,
  "valor": 450.00,
  "valorTotal": 1350.00,
  "tipo": "Eletrônicos",
  "status": "Concluída",
  "dataVenda": "2025-10-29T15:30:00",
  "observacoes": "Cliente solicitou nota fiscal"
}
```

#### 7. Listar Tipos Disponíveis
```
GET /api/dashboard/tipos
Authorization: Bearer {token}
```

**Resposta de Sucesso (200 OK):**
```json
["Eletrônicos", "Livros", "Móveis", "Roupas"]
```

### Vendas (Aba de Vendas)

**IMPORTANTE:** Todos os endpoints de Vendas requerem autenticação. Adicione o token JWT no header:
```
Authorization: Bearer {seu-token-aqui}
```

#### 1. Recuperar Colunas da Tabela
```
GET /api/vendas/colunas
Authorization: Bearer {token}
```

**Resposta de Sucesso (200 OK):**
```json
[
  {
    "campo": "id",
    "label": "ID",
    "tipo": "number",
    "ordenavel": true
  },
  {
    "campo": "produto",
    "label": "Produto",
    "tipo": "string",
    "ordenavel": true
  },
  {
    "campo": "quantidade",
    "label": "Quantidade",
    "tipo": "number",
    "ordenavel": true
  },
  {
    "campo": "valor",
    "label": "Valor Unitário",
    "tipo": "currency",
    "ordenavel": true
  },
  {
    "campo": "valorTotal",
    "label": "Valor Total",
    "tipo": "currency",
    "ordenavel": true
  },
  {
    "campo": "tipo",
    "label": "Tipo",
    "tipo": "string",
    "ordenavel": true
  },
  {
    "campo": "status",
    "label": "Status",
    "tipo": "string",
    "ordenavel": true
  },
  {
    "campo": "dataVenda",
    "label": "Data da Venda",
    "tipo": "datetime",
    "ordenavel": true
  },
  {
    "campo": "observacoes",
    "label": "Observações",
    "tipo": "string",
    "ordenavel": false
  }
]
```

#### 2. Listar Todas as Vendas
```
GET /api/vendas
Authorization: Bearer {token}
```

**Resposta de Sucesso (200 OK):**
```json
[
  {
    "id": 1,
    "produto": "Notebook Dell",
    "quantidade": 2,
    "valor": 3500.00,
    "valorTotal": 7000.00,
    "tipo": "Eletrônicos",
    "status": "Concluída",
    "dataVenda": "2025-10-24T10:00:00",
    "dataCriacao": "2025-10-24T09:00:00",
    "dataAtualizacao": "2025-10-24T09:00:00",
    "observacoes": "Venda realizada com sucesso",
    "nomeUsuario": "Usuario Teste",
    "emailUsuario": "teste@fiap.com"
  }
]
```

#### 3. Buscar Venda por ID
```
GET /api/vendas/{id}
Authorization: Bearer {token}
```

**Exemplo de uso:**
```
GET /api/vendas/1
```

**Resposta de Sucesso (200 OK):**
```json
{
  "id": 1,
  "produto": "Notebook Dell",
  "quantidade": 2,
  "valor": 3500.00,
  "valorTotal": 7000.00,
  "tipo": "Eletrônicos",
  "status": "Concluída",
  "dataVenda": "2025-10-24T10:00:00",
  "dataCriacao": "2025-10-24T09:00:00",
  "dataAtualizacao": "2025-10-24T09:00:00",
  "observacoes": "Venda realizada com sucesso",
  "nomeUsuario": "Usuario Teste",
  "emailUsuario": "teste@fiap.com"
}
```

**Respostas de Erro:**
- `404 Not Found` - Venda não encontrada
- `403 Forbidden` - Venda não pertence ao usuário logado

#### 4. Filtrar Vendas por IDs
```
GET /api/vendas/filtrar?ids=1,2,3
Authorization: Bearer {token}
```

**Parâmetros de Query:**
- `ids` (obrigatório): Lista de IDs separados por vírgula

**Exemplo de uso:**
```
GET /api/vendas/filtrar?ids=1,3,5,7
```

**Resposta:** Array de vendas filtradas pelos IDs fornecidos (apenas as que pertencem ao usuário)

#### 5. Atualizar Venda
```
PUT /api/vendas/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "produto": "Notebook Dell Inspiron",
  "quantidade": 3,
  "valor": 3200.00,
  "tipo": "Eletrônicos",
  "status": "Concluída",
  "observacoes": "Venda atualizada"
}
```

**Nota:** Todos os campos são opcionais. Envie apenas os campos que deseja atualizar.

**Resposta de Sucesso (200 OK):** VendaDetalhadaResponse com dados atualizados

**Respostas de Erro:**
- `404 Not Found` - Venda não encontrada
- `403 Forbidden` - Venda não pertence ao usuário logado

#### 6. Deletar Venda
```
DELETE /api/vendas/{id}
Authorization: Bearer {token}
```

**Exemplo de uso:**
```
DELETE /api/vendas/1
```

**Resposta de Sucesso:** `204 No Content`

**Respostas de Erro:**
- `404 Not Found` - Venda não encontrada
- `403 Forbidden` - Venda não pertence ao usuário logado

### Produtos (Aba de Produtos)

**IMPORTANTE:** Todos os endpoints de Produtos requerem autenticação. Adicione o token JWT no header:
```
Authorization: Bearer {seu-token-aqui}
```

#### 1. Recuperar Colunas da Tabela
```
GET /api/produtos/colunas
Authorization: Bearer {token}
```

**Resposta de Sucesso (200 OK):**
```json
[
  {
    "campo": "id",
    "label": "ID",
    "tipo": "number",
    "ordenavel": true
  },
  {
    "campo": "codigo",
    "label": "Código",
    "tipo": "string",
    "ordenavel": true
  },
  {
    "campo": "nome",
    "label": "Nome do Produto",
    "tipo": "string",
    "ordenavel": true
  },
  {
    "campo": "categoria",
    "label": "Categoria",
    "tipo": "string",
    "ordenavel": true
  },
  {
    "campo": "preco",
    "label": "Preço",
    "tipo": "currency",
    "ordenavel": true
  },
  {
    "campo": "estoque",
    "label": "Estoque",
    "tipo": "number",
    "ordenavel": true
  },
  {
    "campo": "dataCriacao",
    "label": "Data de Criação",
    "tipo": "datetime",
    "ordenavel": true
  },
  {
    "campo": "dataAtualizacao",
    "label": "Data de Atualização",
    "tipo": "datetime",
    "ordenavel": true
  }
]
```

#### 2. Listar Todos os Produtos
```
GET /api/produtos
Authorization: Bearer {token}
```

**Resposta de Sucesso (200 OK):**
```json
[
  {
    "id": 1,
    "codigo": "PROD001",
    "nome": "Notebook Dell Inspiron 15",
    "descricao": "Notebook 15.6 polegadas, Intel Core i5, 8GB RAM, SSD 256GB",
    "categoria": "Eletrônicos",
    "preco": 3500.00,
    "estoque": 15,
    "ativo": true,
    "fabricante": "Dell",
    "unidadeMedida": "unidade",
    "dataCriacao": "2025-10-19T10:00:00",
    "dataAtualizacao": "2025-10-19T10:00:00"
  }
]
```

#### 3. Filtrar Produtos por Nome
```
GET /api/produtos/filtrar/nome?nome={nome}
Authorization: Bearer {token}
```

**Parâmetros de Query:**
- `nome` (obrigatório): Nome ou parte do nome do produto (case-insensitive)

**Exemplo de uso:**
```
GET /api/produtos/filtrar/nome?nome=notebook
```

**Resposta:** Array de produtos cujo nome contenha o texto informado

#### 4. Buscar Produto por ID
```
GET /api/produtos/{id}
Authorization: Bearer {token}
```

**Exemplo de uso:**
```
GET /api/produtos/1
```

**Resposta de Sucesso (200 OK):** ProdutoResponse com dados completos do produto

**Respostas de Erro:**
- `404 Not Found` - Produto não encontrado
- `403 Forbidden` - Produto não pertence ao usuário logado

#### 5. Buscar Produto por Código
```
GET /api/produtos/codigo/{codigo}
Authorization: Bearer {token}
```

**Exemplo de uso:**
```
GET /api/produtos/codigo/PROD001
```

#### 6. Filtrar Produtos por Categoria
```
GET /api/produtos/filtrar/categoria?categoria={categoria}
Authorization: Bearer {token}
```

**Exemplo de uso:**
```
GET /api/produtos/filtrar/categoria?categoria=Eletrônicos
```

#### 7. Filtrar Produtos por Status
```
GET /api/produtos/filtrar/ativo?ativo={true/false}
Authorization: Bearer {token}
```

**Exemplo de uso:**
```
GET /api/produtos/filtrar/ativo?ativo=true
```

#### 8. Listar Categorias Disponíveis
```
GET /api/produtos/categorias
Authorization: Bearer {token}
```

**Resposta de Sucesso (200 OK):**
```json
["Eletrônicos", "Livros", "Móveis"]
```

#### 9. Produtos com Estoque Baixo
```
GET /api/produtos/estoque-baixo?limite={numero}
Authorization: Bearer {token}
```

**Parâmetros de Query:**
- `limite` (opcional, padrão: 10): Quantidade mínima de estoque

**Exemplo de uso:**
```
GET /api/produtos/estoque-baixo?limite=5
```

**Resposta:** Array de produtos com estoque menor ou igual ao limite especificado

#### 10. Criar Novo Produto
```
POST /api/produtos
Authorization: Bearer {token}
Content-Type: application/json

{
  "codigo": "PROD016",
  "nome": "Tablet Samsung Galaxy",
  "descricao": "Tablet 10.1 polegadas, 64GB",
  "categoria": "Eletrônicos",
  "preco": 1299.00,
  "estoque": 20,
  "fabricante": "Samsung",
  "unidadeMedida": "unidade",
  "ativo": true
}
```

**Resposta de Sucesso (201 Created):** ProdutoResponse com o produto criado

#### 11. Atualizar Produto
```
PUT /api/produtos/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "codigo": "PROD001",
  "nome": "Notebook Dell Inspiron 15 - Atualizado",
  "descricao": "Nova descrição",
  "categoria": "Eletrônicos",
  "preco": 3200.00,
  "estoque": 10,
  "fabricante": "Dell",
  "unidadeMedida": "unidade",
  "ativo": true
}
```

**Resposta de Sucesso (200 OK):** ProdutoResponse com dados atualizados

#### 12. Deletar Produto
```
DELETE /api/produtos/{id}
Authorization: Bearer {token}
```

**Resposta de Sucesso:** `204 No Content`

**Respostas de Erro:**
- `404 Not Found` - Produto não encontrado
- `403 Forbidden` - Produto não pertence ao usuário logado

### Pontos de Venda (PDV)

**IMPORTANTE:** Todos os endpoints de PDV requerem autenticação. Adicione o token JWT no header:
```
Authorization: Bearer {seu-token-aqui}
```

#### 1. Recuperar Colunas da Tabela
```
GET /api/pdv/colunas
Authorization: Bearer {token}
```

#### 2. Listar Todos os PDVs
```
GET /api/pdv
Authorization: Bearer {token}
```

#### 3. Filtrar PDVs por Endereço
```
GET /api/pdv/filtrar/endereco?endereco={endereco}
Authorization: Bearer {token}
```

**Parâmetros de Query:**
- `endereco` (obrigatório): Endereço ou parte do endereço (case-insensitive)

**Exemplo de uso:**
```
GET /api/pdv/filtrar/endereco?endereco=Paulista
```

**Resposta:** Array de PDVs cujo endereço contenha o texto informado

#### 4. Buscar PDV por ID
```
GET /api/pdv/{id}
```

#### 5. Outros Filtros Disponíveis
- `GET /api/pdv/filtrar/nome?nome={nome}` - Filtrar por nome
- `GET /api/pdv/filtrar/cidade?cidade={cidade}` - Filtrar por cidade
- `GET /api/pdv/filtrar/estado?estado={uf}` - Filtrar por estado (ex: SP)
- `GET /api/pdv/filtrar/bairro?bairro={bairro}` - Filtrar por bairro
- `GET /api/pdv/filtrar/tipo?tipo={tipo}` - Filtrar por tipo (Matriz, Filial, Franquia, Quiosque)
- `GET /api/pdv/filtrar/ativo?ativo={true/false}` - Filtrar por status

#### 6. Listar Tipos e Cidades
- `GET /api/pdv/tipos` - Listar tipos disponíveis
- `GET /api/pdv/cidades` - Listar cidades onde existem PDVs

#### 7. Criar Novo PDV
```
POST /api/pdv
Content-Type: application/json

{
  "nome": "Loja Nova",
  "endereco": "Rua das Flores, 123",
  "bairro": "Jardim Botânico",
  "cidade": "São Paulo",
  "estado": "SP",
  "cep": "04050-000",
  "telefone": "(11) 9999-9999",
  "email": "nova@loja.com",
  "responsavel": "Maria Silva",
  "tipo": "Filial",
  "ativo": true
}
```

#### 8. Atualizar e Deletar PDV
- `PUT /api/pdv/{id}` - Atualizar PDV
- `DELETE /api/pdv/{id}` - Deletar PDV

## Como Executar

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6 ou superior

### Passos

1. Clone o repositório ou navegue até a pasta do projeto:
```bash
cd "C:\Users\matheus.amaral\Documents\Challenge FIAP"
```

2. Compile o projeto:
```bash
mvn clean install
```

3. Execute a aplicação:
```bash
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

### Dados de Teste

Ao iniciar a aplicação pela primeira vez, dados de exemplo serão criados automaticamente:

**Credenciais de teste:**
- Email: `teste@fiap.com`
- Senha: `senha123`

O banco será populado automaticamente com:
- **12 vendas** de exemplo de diferentes tipos (Eletrônicos, Livros, Roupas, Móveis) e status variados
- **15 produtos** de exemplo com diferentes categorias, preços e níveis de estoque
- **10 pontos de venda (PDV)** em diferentes cidades e tipos (Matriz, Filial, Franquia, Quiosque)

### Console H2

Para acessar o console do banco de dados H2:
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:fiapdb`
- Username: `sa`
- Password: (deixe em branco)

## Configuração

As configurações da aplicação estão em `src/main/resources/application.properties`:

- **Porta do servidor**: 8080
- **Banco de dados**: H2 (em memória)
- **JWT Secret**: Configurado no arquivo (ALTERE EM PRODUÇÃO!)
- **JWT Expiration**: 24 horas (86400000 ms)

### Para Produção

Para usar PostgreSQL em produção, edite o `application.properties`:

1. Comente as configurações do H2
2. Descomente as configurações do PostgreSQL
3. Ajuste as credenciais do banco
4. No `pom.xml`, comente a dependência do H2 e descomente a do PostgreSQL
5. **IMPORTANTE**: Altere o `jwt.secret` para uma chave segura

## Estrutura do Projeto

```
src/main/java/com/fiap/challenge/
├── config/
│   └── DataInitializer.java         # Inicializador de dados de exemplo
├── controller/
│   ├── AuthController.java          # Endpoints de autenticação
│   └── DashboardController.java     # Endpoints do dashboard
├── dto/
│   ├── CardInfoResponse.java        # DTO de informações dos cards
│   ├── CreateVendaRequest.java      # DTO de criação de venda
│   ├── LoginRequest.java            # DTO de requisição de login
│   ├── LoginResponse.java           # DTO de resposta de login
│   ├── RegisterRequest.java         # DTO de registro
│   ├── UserResponse.java            # DTO de resposta de usuário
│   ├── VendaPorTipoResponse.java    # DTO de vendas agrupadas por tipo
│   └── VendaResponse.java           # DTO de resposta de venda
├── exception/
│   └── GlobalExceptionHandler.java  # Tratamento global de exceções
├── model/
│   ├── Usuario.java                 # Entidade JPA de usuário
│   └── Venda.java                   # Entidade JPA de venda
├── repository/
│   ├── UsuarioRepository.java       # Repositório de usuários
│   └── VendaRepository.java         # Repositório de vendas
├── security/
│   ├── JwtUtil.java                 # Utilitário JWT
│   ├── JwtAuthenticationFilter.java # Filtro de autenticação
│   └── SecurityConfig.java          # Configuração de segurança
├── service/
│   ├── AuthService.java             # Lógica de autenticação
│   ├── CustomUserDetailsService.java # UserDetailsService customizado
│   └── DashboardService.java        # Lógica de negócio do dashboard
└── ChallengeApplication.java        # Classe principal
```

## Testando com cURL

### Autenticação

#### Registrar usuário:
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d "{\"nome\":\"João Silva\",\"email\":\"joao@example.com\",\"senha\":\"senha123\"}"
```

#### Login:
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d "{\"email\":\"teste@fiap.com\",\"senha\":\"senha123\"}"
```

#### Recuperar usuário logado:
```bash
curl -X GET http://localhost:8080/api/auth/me \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

### Dashboard

#### Recuperar informações dos cards:
```bash
curl -X GET http://localhost:8080/api/dashboard/cards \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

#### Listar todas as vendas:
```bash
curl -X GET http://localhost:8080/api/dashboard/vendas \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

#### Filtrar vendas por tipo:
```bash
curl -X GET "http://localhost:8080/api/dashboard/vendas/tipo/Eletrônicos" \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

#### Filtrar vendas por status:
```bash
curl -X GET "http://localhost:8080/api/dashboard/vendas/status/Concluída" \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

#### Criar nova venda:
```bash
curl -X POST http://localhost:8080/api/dashboard/vendas \
  -H "Authorization: Bearer SEU_TOKEN_AQUI" \
  -H "Content-Type: application/json" \
  -d "{\"produto\":\"Webcam HD\",\"quantidade\":2,\"valor\":250.00,\"tipo\":\"Eletrônicos\",\"status\":\"Concluída\",\"observacoes\":\"Entrega expressa\"}"
```

### Vendas

#### Recuperar colunas da tabela:
```bash
curl -X GET http://localhost:8080/api/vendas/colunas \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

#### Listar todas as vendas:
```bash
curl -X GET http://localhost:8080/api/vendas \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

#### Buscar venda por ID:
```bash
curl -X GET http://localhost:8080/api/vendas/1 \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

#### Filtrar vendas por IDs:
```bash
curl -X GET "http://localhost:8080/api/vendas/filtrar?ids=1,2,3" \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

#### Atualizar venda:
```bash
curl -X PUT http://localhost:8080/api/vendas/1 \
  -H "Authorization: Bearer SEU_TOKEN_AQUI" \
  -H "Content-Type: application/json" \
  -d "{\"produto\":\"Notebook Dell Inspiron\",\"quantidade\":3,\"valor\":3200.00}"
```

#### Deletar venda:
```bash
curl -X DELETE http://localhost:8080/api/vendas/1 \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

### Produtos

#### Recuperar colunas da tabela:
```bash
curl -X GET http://localhost:8080/api/produtos/colunas \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

#### Listar todos os produtos:
```bash
curl -X GET http://localhost:8080/api/produtos \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

#### Filtrar produtos por nome:
```bash
curl -X GET "http://localhost:8080/api/produtos/filtrar/nome?nome=notebook" \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

#### Buscar produto por ID:
```bash
curl -X GET http://localhost:8080/api/produtos/1 \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

#### Buscar produto por código:
```bash
curl -X GET http://localhost:8080/api/produtos/codigo/PROD001 \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

#### Produtos com estoque baixo:
```bash
curl -X GET "http://localhost:8080/api/produtos/estoque-baixo?limite=5" \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

#### Criar novo produto:
```bash
curl -X POST http://localhost:8080/api/produtos \
  -H "Authorization: Bearer SEU_TOKEN_AQUI" \
  -H "Content-Type: application/json" \
  -d "{\"codigo\":\"PROD016\",\"nome\":\"Tablet Samsung\",\"categoria\":\"Eletrônicos\",\"preco\":1299.00,\"estoque\":20,\"fabricante\":\"Samsung\",\"unidadeMedida\":\"unidade\"}"
```

#### Atualizar produto:
```bash
curl -X PUT http://localhost:8080/api/produtos/1 \
  -H "Authorization: Bearer SEU_TOKEN_AQUI" \
  -H "Content-Type: application/json" \
  -d "{\"codigo\":\"PROD001\",\"nome\":\"Notebook Atualizado\",\"categoria\":\"Eletrônicos\",\"preco\":3200.00,\"estoque\":10,\"fabricante\":\"Dell\",\"unidadeMedida\":\"unidade\",\"ativo\":true}"
```

#### Deletar produto:
```bash
curl -X DELETE http://localhost:8080/api/produtos/1 \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

### Pontos de Venda (PDV)

#### Recuperar colunas da tabela:
```bash
curl -X GET http://localhost:8080/api/pdv/colunas \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

#### Listar todos os PDVs:
```bash
curl -X GET http://localhost:8080/api/pdv \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

#### Filtrar PDVs por endereço:
```bash
curl -X GET "http://localhost:8080/api/pdv/filtrar/endereco?endereco=Paulista" \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

#### Filtrar por cidade:
```bash
curl -X GET "http://localhost:8080/api/pdv/filtrar/cidade?cidade=São Paulo" \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

#### Criar novo PDV:
```bash
curl -X POST http://localhost:8080/api/pdv \
  -H "Authorization: Bearer SEU_TOKEN_AQUI" \
  -H "Content-Type: application/json" \
  -d "{\"nome\":\"Loja Nova\",\"endereco\":\"Rua das Flores, 123\",\"bairro\":\"Jardim\",\"cidade\":\"São Paulo\",\"estado\":\"SP\",\"cep\":\"04050-000\",\"telefone\":\"(11)9999-9999\",\"email\":\"nova@loja.com\",\"responsavel\":\"Maria Silva\",\"tipo\":\"Filial\",\"ativo\":true}"
```

#### Atualizar PDV:
```bash
curl -X PUT http://localhost:8080/api/pdv/1 \
  -H "Authorization: Bearer SEU_TOKEN_AQUI" \
  -H "Content-Type: application/json" \
  -d "{\"nome\":\"Loja Atualizada\",\"endereco\":\"Av. Paulista, 1000\",\"cidade\":\"São Paulo\",\"estado\":\"SP\",\"tipo\":\"Matriz\",\"ativo\":true}"
```

#### Deletar PDV:
```bash
curl -X DELETE http://localhost:8080/api/pdv/1 \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

## Segurança

- Senhas são criptografadas usando BCrypt
- Autenticação baseada em JWT
- Tokens expiram em 24 horas
- CORS configurado para aceitar requisições de qualquer origem (ajuste para produção)
- Proteção contra CSRF desabilitada (API REST stateless)

## Notas Importantes

1. **JWT Secret**: Altere a chave secreta em produção para uma chave segura e única
2. **CORS**: Ajuste as origens permitidas para produção
3. **Banco de Dados**: O H2 é apenas para desenvolvimento. Use PostgreSQL ou outro banco em produção
4. **Validações**: Todas as entradas são validadas antes do processamento

## Resumo dos Endpoints

### Autenticação (Public)
- `POST /api/auth/register` - Registrar novo usuário
- `POST /api/auth/login` - Fazer login
- `GET /api/auth/me` - Recuperar usuário logado (requer autenticação)
- `GET /api/auth/health` - Health check

### Dashboard (Requer Autenticação)
- `GET /api/dashboard/cards` - Informações para cards do dashboard
- `GET /api/dashboard/vendas` - Listar todas as vendas do usuário
- `GET /api/dashboard/vendas/tipo/{tipo}` - Filtrar vendas por tipo
- `GET /api/dashboard/vendas/status/{status}` - Filtrar vendas por status
- `GET /api/dashboard/vendas/por-tipo` - Estatísticas agrupadas por tipo
- `POST /api/dashboard/vendas` - Criar nova venda
- `GET /api/dashboard/tipos` - Listar tipos disponíveis

### Vendas - Aba de Vendas (Requer Autenticação)
- `GET /api/vendas/colunas` - Recuperar estrutura das colunas da tabela
- `GET /api/vendas` - Listar todas as vendas (detalhadas)
- `GET /api/vendas/{id}` - Buscar venda específica por ID
- `GET /api/vendas/filtrar?ids=1,2,3` - Filtrar vendas por lista de IDs
- `PUT /api/vendas/{id}` - Atualizar uma venda
- `DELETE /api/vendas/{id}` - Deletar uma venda

### Produtos - Aba de Produtos (Requer Autenticação)
- `GET /api/produtos/colunas` - Recuperar estrutura das colunas da tabela
- `GET /api/produtos` - Listar todos os produtos
- `GET /api/produtos/{id}` - Buscar produto específico por ID
- `GET /api/produtos/codigo/{codigo}` - Buscar produto por código
- `GET /api/produtos/filtrar/nome?nome={nome}` - Filtrar produtos por nome
- `GET /api/produtos/filtrar/categoria?categoria={categoria}` - Filtrar por categoria
- `GET /api/produtos/filtrar/ativo?ativo={true/false}` - Filtrar por status
- `GET /api/produtos/categorias` - Listar categorias disponíveis
- `GET /api/produtos/estoque-baixo?limite={numero}` - Produtos com estoque baixo
- `POST /api/produtos` - Criar novo produto
- `PUT /api/produtos/{id}` - Atualizar um produto
- `DELETE /api/produtos/{id}` - Deletar um produto

### Pontos de Venda (PDV) - Aba PDV (Requer Autenticação)
- `GET /api/pdv/colunas` - Recuperar estrutura das colunas da tabela
- `GET /api/pdv` - Listar todos os pontos de venda
- `GET /api/pdv/{id}` - Buscar PDV específico por ID
- `GET /api/pdv/filtrar/endereco?endereco={endereco}` - Filtrar por endereço
- `GET /api/pdv/filtrar/nome?nome={nome}` - Filtrar por nome
- `GET /api/pdv/filtrar/cidade?cidade={cidade}` - Filtrar por cidade
- `GET /api/pdv/filtrar/estado?estado={uf}` - Filtrar por estado
- `GET /api/pdv/filtrar/bairro?bairro={bairro}` - Filtrar por bairro
- `GET /api/pdv/filtrar/tipo?tipo={tipo}` - Filtrar por tipo
- `GET /api/pdv/filtrar/ativo?ativo={true/false}` - Filtrar por status
- `GET /api/pdv/tipos` - Listar tipos disponíveis
- `GET /api/pdv/cidades` - Listar cidades disponíveis
- `POST /api/pdv` - Criar novo PDV
- `PUT /api/pdv/{id}` - Atualizar um PDV
- `DELETE /api/pdv/{id}` - Deletar um PDV

## Próximos Passos Sugeridos

- [ ] Implementar refresh token
- [ ] Adicionar roles/permissões de usuários
- [ ] Implementar recuperação de senha por email
- [ ] Adicionar edição e exclusão de vendas
- [ ] Implementar paginação nas listagens
- [ ] Adicionar filtros por período de data
- [ ] Adicionar testes unitários e de integração
- [ ] Documentação com Swagger/OpenAPI
- [ ] Implementar rate limiting
- [ ] Adicionar logs estruturados
- [ ] Gerar relatórios em PDF/Excel
