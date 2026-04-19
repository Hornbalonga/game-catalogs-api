# Game Catalogs API

API REST desenvolvida com Java e Spring Boot para gerenciar um catálogo de jogos.

## Status
Projeto em desenvolvimento.

## Stack
- Java 21
- Spring Boot
- Spring Data JPA
- Spring Validation
- PostgreSQL
- Lombok
- Maven

## Funcionalidades
- Cadastrar um jogo
- Listar todos os jogos
- Buscar um jogo por ID
- Atualizar um jogo
- Deletar um jogo
- Validar dados de entrada
- Tratar erros globais de validação e recurso não encontrado

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
URL base:
```text
http://localhost:8080/api/games
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

2. Configure o PostgreSQL no `application.properties` com sua URL, usuário e senha.

Exemplo:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/gamecatalogs
spring.datasource.username=postgres
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
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
O projeto utiliza PostgreSQL como banco de dados principal.

## Próximos passos
- Adicionar documentação com Swagger / OpenAPI
- Melhorar mensagens personalizadas para enum inválido
- Adicionar testes automatizados
