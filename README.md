# Game Catalogs API

API REST desenvolvida com Java e Spring Boot para gerenciar um catálogo de jogos.

## Status
Projeto finalizado e publicado no Railway.

## Stack
- Java 21
- Spring Boot
- Spring Data JPA
- Spring Validation
- PostgreSQL
- Lombok
- Maven
- Swagger / OpenAPI
- Railway

## Funcionalidades
- Cadastrar um jogo
- Listar todos os jogos
- Buscar um jogo por ID
- Atualizar um jogo
- Deletar um jogo
- Validar dados de entrada
- Tratar erros globais de validação e recurso não encontrado
- Documentar a API com Swagger

## Domínio
Um jogo contém os seguintes dados:
- nome
- plataforma
- gênero
- status do jogo (`enum`)
- nota (`0.0` a `10.0`)
- ano jogado (opcional)
- observação (opcional)
- data de criação automática

## Endpoints da API
URL base local:
```text
http://localhost:8080/api/games
```

URL base em produção:
```text
https://game-catalogs-api-production.up.railway.app/api/games
```

Swagger em produção:
```text
https://game-catalogs-api-production.up.railway.app/swagger-ui/index.html
```

Endpoints disponíveis:
- `POST /api/games` — cadastrar um novo jogo
- `GET /api/games` — listar todos os jogos
- `GET /api/games/{id}` — buscar um jogo por ID
- `PUT /api/games/{id}` — atualizar um jogo
- `DELETE /api/games/{id}` — remover um jogo

## Regras de validação
- `name` é obrigatório, com mínimo de 3 e máximo de 100 caracteres
- `platform` é obrigatório
- `genre` é obrigatório
- `status` é obrigatório
- `rating` deve estar entre `0.0` e `10.0`
- `yearPlayed` deve estar entre `1970` e `2026`
- `observation` deve ter no máximo 500 caracteres

## Tratamento de erros
Atualmente a API trata:
- `400 Bad Request` para dados inválidos no corpo da requisição
- `404 Not Found` quando o jogo não é encontrado

Exemplo de resposta para erro de validação:
```json
{
  "yearPlayed": "deve ser maior que ou igual à 1970",
  "rating": "deve ser menor que ou igual a 10.0",
  "name": "Nome é obrigatório",
  "genre": "Gênero é obrigatório",
  "platform": "Plataforma é obrigatória",
  "status": "Status é obrigatório"
}
```

Exemplo de resposta para recurso não encontrado:
```json
{
  "error": "Jogo não encontrado com id: 999"
}
```

## Como executar o projeto
1. Clone o repositório:
```bash
git clone https://github.com/Hornbalonga/game-catalogs-api.git
cd game-catalogs-api
```

2. Configure o PostgreSQL no `application.properties` ou por variáveis de ambiente.

Exemplo:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/gamecatalogs
spring.datasource.username=postgres
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
server.port=8080
```

3. Execute a aplicação:
```bash
./mvnw spring-boot:run
```

No Windows (PowerShell):
```powershell
.\mvnw.cmd spring-boot:run
```

## Banco de dados
O projeto utiliza PostgreSQL como banco de dados principal, tanto no ambiente local quanto no deploy em produção no Railway.

## Deploy
Aplicação publicada no Railway com PostgreSQL.

- API: https://game-catalogs-api-production.up.railway.app/
- Swagger: https://game-catalogs-api-production.up.railway.app/swagger-ui/index.html
