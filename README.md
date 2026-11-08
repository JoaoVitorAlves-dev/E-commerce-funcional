# E-commerce API

API REST para gerenciamento de clientes, produtos, categorias, pedidos, itens de pedido, pagamentos e endereços.

## Tecnologias

- Java 17 e Spring Boot
- Spring Web, Spring Data JPA e Spring Security
- JWT e BCrypt
- PostgreSQL e Flyway
- Maven

## Requisitos

- JDK 17
- Maven
- PostgreSQL

## Configuração e execução

Crie o banco `ecommerce` no PostgreSQL e configure as variáveis de ambiente:

```bash
export DB_USER=postgres
export DB_PASSWORD=sua_senha
export JWT_SECRET=uma_chave_com_pelo_menos_32_caracteres
```

Inicie a aplicação:

```bash
mvn spring-boot:run
```

A API fica disponível em `http://localhost:8080`.

Para executar os testes:

```bash
mvn test
```

## Autenticação

Registre um cliente em `POST /clientes/registrar` e faça login em `POST /clientes/logar`.

O login retorna `accessToken` e `refreshToken`. Envie o access token nas rotas protegidas:

```http
Authorization: Bearer <accessToken>
```

O access token tem validade padrão de uma hora. O refresh token tem validade configurada em dias e é rotacionado quando usado. Guarde os tokens com segurança e nunca os publique no repositório.

A rota de renovação implementada é `POST /clientes/refresh?token=<refreshToken>`. **Na configuração atual, essa rota também exige um access token válido**, então não é possível usá-la depois que o access token expira. Essa regra precisa ser ajustada para o fluxo de renovação funcionar após a expiração.

As senhas são armazenadas com BCrypt.

## Principais rotas

| Recurso | Rotas |
|---|---|
| Clientes | `/clientes/buscar`, `/clientes/buscar/{id}`, `/clientes/registrar`, `/clientes/logar`, `/clientes/refresh`, `/clientes/atualizar`, `/clientes/deletar` |
| Produtos | `/produtos/listar`, `/produtos/buscar/{id}`, `/produtos/adicionar`, `/produtos/atualizar/{id}`, `/produtos/deletar/{id}` |
| Categorias | `/categorias/listar`, `/categorias/buscar/{id}`, `/categorias/adicionar`, `/categorias/atualizar/{id}`, `/categorias/deletar/{id}` |
| Pedidos | `/pedidos/listar`, `/pedidos/buscar/{id}`, `/pedidos/adicionar`, `/pedidos/atualizar/{id}`, `/pedidos/deletar/{id}` |
| Itens de pedido | `/itempedidos/listar`, `/itempedidos/buscar/{id}`, `/itempedidos/adicionar`, `/itempedidos/atualizar/{id}`, `/itempedidos/deletar/{id}` |
| Pagamentos | `/pagamentos/listar`, `/pagamentos/buscar/{id}`, `/pagamentos/adicionar`, `/pagamentos/atualizar/{id}`, `/pagamentos/deletar/{id}` |
| Endereços | `/enderecos/listar`, `/enderecos/buscar/{id}`, `/enderecos/adicionar`, `/enderecos/atualizar/{id}`, `/enderecos/deletar/{id}` |

As listagens aceitam paginação com `page`, `size` e `sort`. As permissões por perfil estão definidas em `SecurityConfig`.

## Estrutura

```text
src/main/java/ecom/merce/ecommerce/
├── config/              # Segurança e JWT
├── domain/              # Serviços, repositórios, enums e exceções
├── dto/                 # Objetos de requisição e resposta
├── entity/              # Entidades JPA
├── infra/controller/    # Endpoints REST
└── mapper/              # Conversão entre entidades e DTOs
```

## Autor

**João Vitor de Jesus Alves** · [GitHub](https://github.com/JoaoVitorAlves-dev)