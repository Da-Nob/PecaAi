# 🍽️ PecaAi API - Documentação e Guia de Uso

Bem-vindo à documentação oficial da API do **PecaAi**, um sistema de backend para gestão de delivery e atendimento em tempo real desenvolvido em **Java com Spring Boot**, utilizando **Spring Data JPA/Hibernate**, banco de dados **PostgreSQL** e segurança baseada em **JWT (JSON Web Token)**.

O sistema está atualmente hospedado e rodando em produção na plataforma **Railway**:

🔗 **URL Base:** `https://pecaai-production.up.railway.app`

---

## 🚀 Como Funciona o Fluxo da Aplicação

Para utilizar a API corretamente, siga esta ordem lógica de operações:

1. **Autenticação:** Registrar um usuário (`ADMIN`, `GARCOM` ou `CLIENTE`) e fazer login para obter o **Token JWT**.
2. **Gestão de Lojas:** Cadastrar o estabelecimento (ex: *Tapiocaria Lamparina*).
3. **Cardápio:** Cadastrar os produtos vinculados à loja (exige papel `ADMIN`).
4. **Pedidos:** Criar os pedidos informando o cliente, a loja e os itens desejados.
5. **Atendimento (Garçom):** Abrir chamadas de mesa e atualizar o status de atendimento.

---

## ⚠️ Importante: quase tudo exige autenticação

Com exceção de `POST /auth/login` e `POST /auth/register`, **todas as demais rotas exigem um token válido** — inclusive as de leitura (`GET`). Não existe endpoint público de consulta hoje; qualquer requisição sem o header abaixo retorna `401/403`.

Envie o token gerado no login através do header:

| Header | Valor |
|---|---|
| `Authorization` | `Bearer <seu_token_jwt>` |

O token expira em **2 horas** após ser gerado.

---

## 🔐 1. Autenticação (JWT)

### 📌 Registrar Usuário

- **Método:** `POST`
- **Rota:** `https://pecaai-production.up.railway.app/auth/register`
- **Autenticação:** não exige (rota pública)
- **Body (JSON):**

```json
{
  "nome": "Daniel Admin",
  "email": "admin@email.com",
  "senha": "senha_segura",
  "tipo": "ADMIN"
}
```

`tipo` aceita exatamente três valores: `ADMIN`, `GARCOM`, `CLIENTE`.

> ⚠️ **Use sempre esta rota para criar usuários, nunca `POST /usuarios`.** O endpoint `/usuarios` existe apenas para manutenção administrativa (editar/listar/remover) e **não criptografa a senha** — um usuário criado por lá fica com a senha em texto puro e não consegue fazer login depois. Isso é uma limitação conhecida do código atual, não um comportamento esperado.

### 📌 Fazer Login (Obter Token)

- **Método:** `POST`
- **Rota:** `https://pecaai-production.up.railway.app/auth/login`
- **Autenticação:** não exige (rota pública)
- **Body (JSON):**

```json
{
  "email": "admin@email.com",
  "senha": "senha_segura"
}
```

- **Resposta esperada (`200 OK`):**

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

Copie o valor de `token` para usar no header `Authorization` das próximas requisições.

---

## 🏪 2. Gestão de Lojas

### 📌 Cadastrar Loja

- **Método:** `POST`
- **Rota:** `https://pecaai-production.up.railway.app/lojas`
- **Autenticação:** exige token de **qualquer** usuário logado (não precisa ser `ADMIN` — qualquer papel serve)
- **Body (JSON):**

```json
{
  "nome": "Tapiocaria Lamparina",
  "cnpj": "12345678000100"
}
```

Guarde o `id` retornado (ex: `2`) para usar nos produtos e pedidos. O campo `cnpj` não tem validação de formato no código — tanto com pontuação quanto sem funcionam, mas vale manter um padrão único pra evitar confusão.

### 📌 Listar / Buscar Loja

- **Método:** `GET` — **Rota:** `https://pecaai-production.up.railway.app/lojas` (lista todas)
- **Método:** `GET` — **Rota:** `https://pecaai-production.up.railway.app/lojas/{id}` (busca uma específica)
- **Autenticação:** exige token de qualquer usuário logado

---

## 🍔 3. Gestão de Produtos (Cardápio)

### 📌 Cadastrar Produto

- **Método:** `POST`
- **Rota:** `https://pecaai-production.up.railway.app/produtos`
- **Autenticação:** exige token com papel **`ADMIN`** — esta é a única rota de escrita com restrição de papel específica hoje.
- **Body (JSON):**

```json
{
  "nome": "Tapioca Carocuda com Carne de Sol",
  "descricao": "Massa com coco recheada com carne de sol e queijo",
  "preco": 9.00,
  "lojaId": 2
}
```

Guarde o `id` gerado para o produto (ex: `2`).

### 📌 Listar Produtos

- **Método:** `GET` — **Rota:** `https://pecaai-production.up.railway.app/produtos` (lista todos)
- **Método:** `GET` — **Rota:** `https://pecaai-production.up.railway.app/produtos/loja/{lojaId}` (lista os de uma loja específica)
- **Método:** `GET` — **Rota:** `https://pecaai-production.up.railway.app/produtos/{id}` (busca um específico)
- **Autenticação:** exige token de qualquer usuário logado

---

## 🛒 4. Gestão de Pedidos

### 📌 Realizar Pedido

- **Método:** `POST`
- **Rota:** `https://pecaai-production.up.railway.app/pedidos`
- **Autenticação:** exige token de qualquer usuário logado
- **Body (JSON):**

```json
{
  "clienteId": 1,
  "lojaId": 2,
  "itens": [
    {
      "produtoId": 2,
      "quantidade": 2
    }
  ]
}
```

- **Resposta esperada (`201 Created`):** retorna o resumo completo do pedido gerado, com data/hora e os itens (incluindo o nome do produto).

### 📌 Listar Pedidos

- **Método:** `GET` — **Rota:** `https://pecaai-production.up.railway.app/pedidos` (lista todos)
- **Método:** `GET` — **Rota:** `https://pecaai-production.up.railway.app/pedidos/loja/{lojaId}` (lista os de uma loja específica)
- **Método:** `GET` — **Rota:** `https://pecaai-production.up.railway.app/pedidos/{id}` (busca um específico)

---

## 🛎️ 5. Chamada e Atendimento do Garçom

### 📌 Chamar o Garçom (Criar Chamada)

- **Método:** `POST`
- **Rota:** `https://pecaai-production.up.railway.app/chamadas`
- **Autenticação:** exige token de qualquer usuário logado
- **Body (JSON):**

```json
{
  "lojaId": 2,
  "numeroMesa": 5
}
```

### 📌 Atender Chamada

- **Método:** `PUT`
- **Rota:** `https://pecaai-production.up.railway.app/chamadas/{id}/atender` (substitua `{id}` pelo ID da chamada criada, ex: `.../chamadas/4/atender`)
- **Resposta esperada (`200 OK`):** atualiza o status da mesa para atendida (`statusAtendido: true`).

### 📌 Listar Chamadas

- **Método:** `GET` — **Rota:** `https://pecaai-production.up.railway.app/chamadas` (lista todas)
- **Método:** `GET` — **Rota:** `https://pecaai-production.up.railway.app/chamadas/pendentes/loja/{lojaId}` (lista só as pendentes de uma loja)

---

## 🛠️ Tecnologias Utilizadas

- Java 21 / Spring Boot
- Spring Security & JWT (biblioteca `com.auth0:java-jwt`)
- Spring Data JPA / Hibernate
- PostgreSQL
- Railway (Deploy e Hospedagem em Nuvem)

---

## 💻 Como Rodar o Projeto Localmente

### Pré-requisitos
- **Java 21** ou superior instalado
- **Maven** instalado
- Banco de dados **PostgreSQL** rodando localmente

### Passo a Passo

1. **Clone o repositório:**
   ```bash
   git clone [https://github.com/Da-Nob/PecaAi.git](https://github.com/Da-Nob/PecaAi.git)
   cd PecaAi

2. **Configure o Banco de Dados:**
Crie um banco de dados no PostgreSQL chamado pecaai:

SQL

CREATE DATABASE pecaai;

3. **Configure as Variáveis de Ambiente:**

Ajuste as credenciais no arquivo src/main/resources/application.properties ou defina no seu ambiente:

SPRING_DATASOURCE_URL: jdbc:postgresql://localhost:5432/pecaai

SPRING_DATASOURCE_USERNAME: seu_usuario

SPRING_DATASOURCE_PASSWORD: sua_senha

JWT_SECRET: sua_chave_secreta


4. **Instale as dependências e rode a aplicação:**

Bash
./mvnw spring-boot:run
A API estará acessível em http://localhost:8080

---

## 🗺️ Tabela Rápida de Endpoints

| Método | Rota | Descrição | Rota Pública? | Permissão Mínima |
| :--- | :--- | :--- | :---: | :---: |
| `POST` | `/auth/register` | Cadastra novo usuário | Sim | Qualquer |
| `POST` | `/auth/login` | Autentica e gera Token | Sim | Qualquer |
| `POST` | `/lojas` | Cadastra nova loja | Não | Logado |
| `GET` | `/lojas` | Lista todas as lojas | Não | Logado |
| `POST` | `/produtos` | Cadastra produto no cardápio | Não | **ADMIN** |
| `GET` | `/produtos` | Lista todos os produtos | Não | Logado |
| `POST` | `/pedidos` | Cria um novo pedido | Não | Logado |
| `POST` | `/chamadas` | Solicita atendimento de mesa | Não | Logado |
| `PUT` | `/chamadas/{id}/atender` | Garçom atende a mesa | Não | Logado |

---

## 📋 Limitações conhecidas (para o relatório/próximos passos)

- `POST /usuarios` não criptografa a senha — prefira sempre `/auth/register` para criar usuários.
- Só `POST /produtos` tem restrição por papel (`ADMIN`); as demais rotas de escrita aceitam qualquer usuário autenticado, mesmo que logicamente devessem ser restritas (ex: só `ADMIN` deveria poder criar loja).
- A assinatura do token JWT tem um valor padrão fixo no código (`api.security.token.secret`) caso a variável de ambiente não seja configurada — em produção, é importante garantir que essa variável esteja definida no Railway com um valor próprio, não o padrão do código-fonte.