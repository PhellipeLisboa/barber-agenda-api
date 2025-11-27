# barber-agenda-api

API REST para gerenciar agendas e agendamentos de barbearias — TENTATIVA de construir uma plataforma de múltiplas agendas/barbearias.

⚠️ Status: protótipo / versão experimental —> Ultima versão antes de uma refatoração para uma API de um único estabelecimento, cujo objetivo é apenas registrar o que foi feito.

## 📋 Visão geral

Este projeto foi pensado originalmente para permitir gerenciar múltiplas agendas de diferentes barbearias / profissionais. As funcionalidades contempladas incluem:

- Usuário com roles (admin, owner, profissional, usuário comum).

- Autenticação e autorização via JWT.

- Entidades de Agenda, Appointment, User, Role, etc.

- Controle de permissões para ações como criação, edição, deleção de agendas e agendamentos.

- Validações (tamanhos de strings, horário de funcionamento, conflito de agendamentos, etc).

- DTOs, mapeamentos e persistência usando Spring Data JPA / Hibernate.

Esta versão se resume a uma tentativa que acabou sendo um passo grande demais para o que consigo fazer no momento, por isso será usado apenas como backup/histórico, antes de refatorar o sistema para atender uma única barbearia, com modelo simplificado.

## 🛠️ Tecnologias

- Java 21

- Spring Boot 

- Spring Data JPA / Hibernate

- Banco de dados relacional (PostgreSQL)
  
- FlyWay para migrations

- Bean Validation (jakarta.validation) para validar requests

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


A aplicação irá subir (normalmente na porta 8080), com configurações de segurança, JWT, endpoints e APIs conforme implementado até agora.

## 📂 Estrutura de pacotes

**model** —> Entidades JPA: User, Role, Agenda, Appointment, etc.

**dto** —> Classes DTO para requests/responses (AgendaRequestDto, AppointmentPatchDto, etc).

**infra.security** —> Configuração de segurança, filtros JWT, controle de autorização, roles, etc.

**service** —> Lógica de negócio, validações, regras de serviço para agendas e agendamentos.

**repository** — Interfaces Spring Data JPA para persistência.

**mapper** — Mapeamento entre DTOs e entidades.

**exception / exception.handler** — Exceções personalizadas da aplicação e handler global (para erros de negócio) + (tentativas de) handler de segurança.

Outros pacotes conforme necessário para funcionalidades extras.

## 🧪 O que funciona?

- Criação de usuários e autenticação (login / registro) com JWT.

- Criação de agendas e agendamentos.

- Validações via Bean Validation.

- Camada de segurança básica (roles, filtros, autenticação).

- DTOs e mapeamentos.


## 🎯 Próximos passos

Este repositório ficará como marco histórico / backup antes da refatoração. 

A próxima versão do sistema seguirá este planejamento:

- Refatorar o domínio para trabalhar com apenas uma barbearia.
- Manter apenas as partes essenciais (User, Role, Appointment, Service/Agenda, etc).
- Melhorar segurança e permissões.
- Limpar endpoints, DTOs e camadas desnecessárias.
- Fazer front-end
- Docker.
- Realizar testes.
- Preparar para deploy real.

## 📄 Conclusão

Eu usei esse projeto como laboratório de aprendizado e registro das minhas tentativas.
Aprendi e apliquei pela primeira vez algumas tecnologias e conceitos, dando destaque à primeira vez que consegui utilizar o Spring Security 😎
Não aceito contribuições externas — o objetivo atual é limpar e refatorar para uma nova versão.

Sinta-se livre para entrar em contato e/ou dar qualquer feedback!
