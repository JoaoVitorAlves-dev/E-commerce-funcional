# E-commerce API

API REST de estudo para operações de clientes, produtos, categorias, pedidos, itens de pedido, pagamentos e endereços. O projeto usa Java 17, Spring Boot, Spring Web MVC, Spring Data JPA, Hibernate, PostgreSQL, Flyway, Thymeleaf e Lombok.

## Modelo de domínio

- `Cliente` pode ter vários `Pedido`s.
- `Pedido` contém vários `ItemPedido`s.
- Cada `ItemPedido` associa um `Pedido` a um `Produto`.
- O projeto também possui entidades `Categoria`, `Endereco` e `Pagamento`.

## Requisitos

- JDK 17
- Maven 3.9 ou posterior
- PostgreSQL acessível

O repositório não inclui Maven Wrapper (`mvnw`) nem configuração versionada em `src/main/resources`. Configure a conexão JDBC e as propriedades exigidas pela aplicação no ambiente local antes de iniciar. Não armazene senhas no Git.

## Executar

```bash
mvn spring-boot:run
```

## Testes

```bash
mvn test
```

O teste atual, `ECommerceFuncionalApplicationTests.contextLoads`, verifica se o contexto Spring inicia. Ele precisa da configuração externa exigida pela aplicação, inclusive datasource PostgreSQL e migrações Flyway.

## Autenticação e acesso

A configuração de segurança é stateless e usa BCrypt para codificar e comparar senhas. O cadastro de cliente (`POST /clientes`) e o login (`POST /clientes/login`) são públicos. Consultas `GET` em `/produtos/**` e `/categorias/**` também são públicas; as demais requisições exigem autenticação Spring Security.

O endpoint de login valida e-mail e senha e devolve um `ClienteResponse`. **O código atual não emite JWT nem implementa autorização por roles**: embora a descrição do repositório mencione JWT e roles, `SecurityConfig` não configura filtro/token e não há serviço JWT no código. O acesso stateless às demais rotas requer que a autenticação seja fornecida por mecanismo configurado fora dos arquivos revisados.

## Endpoints

As listagens são paginadas com os parâmetros Spring Data `page`, `size` e `sort`.

| Recurso | Rotas |
|---|---|
| Clientes | `GET /clientes`, `GET /clientes/{id}`, `POST /clientes`, `POST /clientes/login`, `PATCH /clientes/{id}`, `DELETE /clientes/{id}` |
| Produtos | `GET /produtos`, `GET /produtos/{id}`, `POST /produtos`, `PATCH /produtos/{id}`, `DELETE /produtos/{id}` |
| Categorias | `GET /categorias`, `GET /categorias/{id}`, `POST /categorias`, `PATCH /categorias/{id}`, `DELETE /categorias/{id}` |
| Pedidos | `GET /pedidos`, `GET /pedidos/{id}`, `POST /pedidos`, `PATCH /pedidos/{id}`, `DELETE /pedidos/{id}` |
| Itens | `GET /itempedidos`, `GET /itempedidos/{id}`, `POST /itempedidos`, `PATCH /itempedidos/{id}`, `DELETE /itempedidos/{id}` |
| Pagamentos | `GET /pagamentos`, `GET /pagamentos/{id}`, `POST /pagamentos`, `PATCH /pagamentos/{id}`, `DELETE /pagamentos/{id}` |
| Endereços | `GET /enderecos`, `GET /enderecos/{id}`, `POST /enderecos`, `PATCH /enderecos/{id}`, `DELETE /enderecos/{id}` |

Os corpos das requisições e respostas são definidos pelos DTOs em `src/main/java/ecom/merce/ecommerce/dto`.

## Estrutura

```text
src/main/java/ecom/merce/ecommerce/
├── config/             # Configuração do Spring Security e BCrypt
├── domain/             # Regras de negócio, enums e exceções
├── dto/                # DTOs de request e response
├── entity/             # Entidades JPA
├── infra/controller/   # Endpoints REST
└── mapper/              # Conversão entre entidades e DTOs
```

## Autor

**João Vitor Alves**  
[GitHub](https://github.com/JoaoVitorAlves-dev) · [Repositório do projeto](https://github.com/JoaoVitorAlves-dev/Marketplace-Delivery)
