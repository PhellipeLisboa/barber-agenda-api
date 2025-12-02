# barber-agenda-api

**API REST para gerenciar agendamentos de barbearias.**

## 📋 Visão geral

Este projeto foi pensado originalmente para permitir gerenciar múltiplas agendas de diferentes barbearias / profissionais, 
mas durante o desenvolvimento resolvi fazer para apenas uma barbearia.

## 🛠️ Tecnologias

- Java 21

- Spring Boot 

- Spring Data JPA / Hibernate

- Banco de dados relacional (PostgreSQL)
  
- FlyWay para migrations

- Bean Validation para validar requests

- Autenticação com JWT

- Documentação da API com Swagger

- Maven

## 🚀 Como executar localmente

### 1 - Clone o repositório

git clone https://github.com/PhellipeLisboa/barber-agenda-api.git
cd barber-agenda-api

### 2 - Configure o banco de dados (e o application.properties / variáveis de ambiente) conforme sua instância local.

Compile e rode com Maven:

./mvnw spring-boot:run


## 📂 Estrutura de pacotes

**model** —> Entidades JPA: User, Role, Appointment, etc.

**dto** —> Classes DTO para requests/responses.

**infra.security** —> Configuração de segurança, filtros JWT, controle de autorização, etc.

**service** —> Lógica de negócio, validações, regras de negócio de agendamentos.

**repository** — Interfaces Spring Data JPA para persistência.

**mapper** — Mapeamento entre DTOs e entidades.

**exception / exception.handler** — Exceções personalizadas da aplicação e tratamento de exceções.

Outros pacotes conforme necessário para funcionalidades extras.

## 🧪 O que funciona?

- Criação de usuários e autenticação (login / registro) com JWT.

- CRUD de agendamentos.

- Validações via Bean Validation.

- Camada de segurança básica (roles, filtros, autenticação).

- DTOs e mapeamentos.


## 🎯 Próximos passos

A próxima versão do sistema seguirá este planejamento:

- Realizar testes.
- Fazer front-end
- Docker.
- Preparar para deploy real.

## 📄 Conclusão

Esse projeto funciona como um laboratório de aprendizado e registro da minha evolução técnica como desenvolvedor backend.

Aprendi e apliquei pela primeira vez novas tecnologias e conceitos, dando destaque à primeira vez que utilizei o Spring Security 😎

Sinta-se livre para entrar em contato e/ou dar qualquer feedback!
