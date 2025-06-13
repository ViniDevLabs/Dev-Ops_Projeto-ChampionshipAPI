# Championship API

## Sumário

- [Sobre o Projeto](#sobre-o-projeto)
- [Modelo de Dados](#modelo-de-dados)
- [Pipeline de CI/CD](#pipeline-de-cicd)
  - [1. Etapa de Build e Teste](#1-etapa-de-build-e-teste-build-and-test)
  - [2. Etapa de Empacotamento](#2-etapa-de-empacotamento-package)
- [Executando o projeto localmente](#executando-o-projeto-localmente)
  - [1. Iniciando o Banco de Dados](#1-iniciando-o-banco-de-dados-postgresql-com-docker-compose)
  - [2. Executando a aplicação](#2-executando-a-aplicação-spring-boot)
  - [Opcional. Execução em Modo Produção](#opcional-executar-versão-que-deve-ser-utilizada-em-produção)

## Sobre o projeto

A **Championship API** é um sistema de backend construído com Spring Boot para gerenciar campeonatos esportivos. A API permite a criação de campeonatos, equipes e jogadores, formando a base para qualquer competição.

As funcionalidades principais incluem:

- **Gerenciamento de Campeonatos**: Criar novos campeonatos, adicionar equipes a eles e consultar a tabela de classificação completa com a pontuação.
- **Gerenciamento de Equipes**: Criar equipes, adicionar ou remover jogadores e consultar informações detalhadas.
- **Gerenciamento de Jogadores**: Manter um registro de jogadores com seus atributos, como idade e nível de habilidade.
- **Gerenciamento de Partidas**: Agendar partidas entre duas equipes, registrar os resultados e buscar o histórico de jogos de um time específico.

## Modelo de Dados

O diagrama de classes abaixo representa as principais entidades e seus relacionamentos no sistema.

![Diagrama de classes](./docs/diagrama-classes-championship-api.png)

## Pipeline de CI/CD

Este projeto utiliza um pipeline de Integração e Entrega Contínua (CI/CD) para automatizar os processos de verificação, teste e empacotamento da aplicação. A configuração do pipeline está no arquivo `.gitlab-ci.yml` e visa garantir a qualidade do código, a segurança e a padronização das entregas.

O pipeline é dividido em duas etapas principais:

### 1. Etapa de Build e Teste (`build-and-test`)

Esta é a primeira barreira de qualidade do pipeline. Ela é executada em todas as alterações enviadas ao repositório (em qualquer branch).

- **Propósito**: Compilar o código-fonte, executar todos os testes automatizados e verificar se o projeto está em um estado funcional e estável.
- **Comando Executado**: `mvn clean verify`
  - Executa o ciclo de vida completo do Maven, que inclui compilação, teste e validação do pacote gerado.

### 2. Etapa de Empacotamento (`package`)

Esta etapa só é executada quando as alterações são mescladas ou enviadas diretamente para a branch **`main`**.

- **Propósito**: Empacotar a aplicação em uma imagem Docker e enviá-la para o registro de contêineres do GitLab (`Container Registry`), criando um artefato pronto para ser implantado em qualquer ambiente.
- **O que acontece?**:
  1.  **Autenticação Segura**: O pipeline realiza o login no `Container Registry` do GitLab utilizando variáveis de ambiente pré-definidas (`$CI_REGISTRY_USER`, `$CI_JOB_TOKEN`) no Runner do próprio Gitlab.
  2.  **Push da Imagem**: A imagem gerada é enviada (`push`) para o registro, ficando disponível para a etapa de deploy.

## Executando o projeto localmente

**O banco de dados PostgreSQL deve estar em execução antes de iniciar a aplicação.**

### 1. Iniciando o Banco de Dados PostgreSQL com Docker Compose

O PostgreSQL é gerenciado via Docker Compose. Para iniciá-lo:

```bash
docker compose up -d --build 'championship-api-pg'
```

### 2. Executando a aplicação Spring Boot

Em application.yml, mude o spring.profiles.active para "dev". Em seguida, para executar a aplicação:

```bash
mvn spring-boot:run
```

Após inicializada, ela estará disponível em [http://localhost:8080](http://localhost:8080).</br>
Também será possível acessar a documentalçao gerada pelo Swagger em [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).

### Opcional. Executar versão que deve ser utilizada em produção

Também é possível executar a versão completa da aplicação em modo produção utilizando o Docker Compose. Esse comando vai iniciar tanto o container do banco PostgreSQL quanto o da API:

```bash
docker compose up -d
```

Assim como antes, a aplicação estará disponível em [http://localhost:8080](http://localhost:8080).
