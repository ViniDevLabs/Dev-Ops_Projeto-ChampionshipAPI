# Championship API

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
Também será possível acessar a documentalçao gerada pelo Swagger em [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Opcional. Executar versão que deve ser utilizada em produção

Também é possível executar a versão completa da aplicação em modo produção utilizando o Docker Compose. Esse comando vai iniciar tanto o container do banco PostgreSQL quanto o da API:

```bash
docker compose up -d
```

Assim como antes, a aplicação estará disponível em [http://localhost:8080](http://localhost:8080).