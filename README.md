# Inventory Management API

API REST para gerenciamento de estoque, com funcionalidades para cadastro de produtos, movimentações (entrada e saída), controle de fornecedores, categorias e usuários. Desenvolvido com Java, Spring Boot, JPA, Flyway, PostgreSQL e Docker.

## Tecnologias Utilizadas

- Java 22+
- Spring Boot
- Spring Data JPA
- Spring Validation
- Flyway
- PostgreSQL
- Docker / Docker Compose
- MapStruct
- Swagger UI

## Funcionalidades

- Cadastro, atualização, listagem e inativação de produtos
- Controle de categorias e fornecedores
- Registro de movimentações de estoque (entrada e saída)
- Consulta por intervalo de datas, tipo e usuário responsável
- Cadastro e atualização de usuários
- Filtro por nome, categoria, fornecedor e quantidade mínima

## Pré-requisitos

- Docker e Docker Compose instalados

## Como rodar o projeto

1. Clone o repositório:

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/seu-repo.git

# Entre no diretório
cd seu-repo

# Configure o application.yml com os dados do banco e email

# Execute a aplicação
./mvnw spring-boot:run
```

## Acesse a documentação da API:
- http://localhost:8080/swagger-ui.html

---

## 📌 Considerações Finais
Contribuições são sempre bem-vindas! Fique à vontade para contribuir ou relatar problemas.</br>
Desenvolvido por [Bruno Reis 👨‍💻](https://www.linkedin.com/in/bruno-reis-oliveira/)
