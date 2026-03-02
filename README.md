# Game Catalogs API

API REST em Java/Spring Boot para gerenciar um catalogo de jogos.

## Status
Projeto em desenvolvimento.

## Stack
- Java 21
- Spring Boot
- Spring Data JPA
- Spring Validation
- H2 Database
- Lombok
- Maven

## Objetivo
Permitir o cadastro e consulta de jogos com dados como:
- nome
- plataforma
- genero
- status do jogo (enum)
- nota (`0.0` a `10.0`)
- ano jogado (opcional)
- observacao (opcional)
- data de criacao automatica

## Estrutura atual
- Entidade `Game`
- Enum `GameStatus`
- Repositorio `GameRepository`

## Como executar
1. Clone o repositorio:
```bash
git clone https://github.com/Hornbalonga/game-catalogs-api.git
cd game-catalogs-api
```

2. Rode a aplicacao:
```bash
./mvnw spring-boot:run
```
No Windows (PowerShell):
```powershell
.\mvnw.cmd spring-boot:run
```

## Banco H2
- Console: `http://localhost:8080/h2-console`
- URL JDBC: `jdbc:h2:mem:testdb` (pode variar conforme `application.properties`)

## Proximos passos
- Criar `GameController` com endpoints `POST /games` e `GET /games`
- Adicionar DTOs de request/response
- Implementar tratamento de erros de validacao
- Adicionar testes

