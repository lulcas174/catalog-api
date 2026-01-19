# Catalog API

API REST para gerenciamento de controle financeiro com categorias, subcategorias e lançamentos.

## 🚀 Tecnologias

- Java 21
- Spring Boot 3.4.1
- PostgreSQL 16
- Docker & Docker Compose
- Maven

## 📋 Pré-requisitos

- Docker
- Docker Compose

## ⚙️ Configuração

1. **Configure as variáveis de ambiente**
   
   Copie o arquivo de exemplo:
   ```bash
   cp .env.example .env
   ```
   
   Edite o arquivo `.env` com suas configurações:
   ```env
   POSTGRES_DB=catalog
   POSTGRES_USER=postgres
   POSTGRES_PASSWORD=postgres
   API_KEY=your-secret-api-key
   ```

## 🐳 Executando com Docker

**Iniciar a aplicação:**
```bash
docker-compose up --build
```

A aplicação irá:
1. ✅ Aguardar o PostgreSQL estar disponível
2. ✅ Executar todos os testes unitários
3. ✅ Iniciar a aplicação
4. ✅ Validar que o health check está respondendo

**Parar a aplicação:**
```bash
docker-compose down
```

**Ver logs:**
```bash
docker-compose logs -f app
```

## 📡 Endpoints

### Base URL
```
http://localhost:8080/v1
```

### Autenticação
A API requer uma API Key no header:
```
X-API-KEY: your-secret-api-key
```

### Principais Endpoints

#### Categorias
- `GET /categorias` - Listar todas as categorias
- `POST /categorias` - Criar categoria
- `GET /categorias/{id}` - Obter categoria por ID
- `PUT /categorias/{id}` - Atualizar categoria
- `DELETE /categorias/{id}` - Deletar categoria
- `GET /categorias/resumo` - Resumo com subcategorias

#### Subcategorias
- `GET /subcategorias` - Listar todas as subcategorias
- `POST /subcategorias` - Criar subcategoria
- `GET /subcategorias/{id}` - Obter subcategoria por ID
- `PUT /subcategorias/{id}` - Atualizar subcategoria
- `DELETE /subcategorias/{id}` - Deletar subcategoria

#### Lançamentos
- `GET /lancamentos` - Listar todos os lançamentos
- `POST /lancamentos` - Criar lançamento
- `GET /lancamentos/{id}` - Obter lançamento por ID
- `PUT /lancamentos/{id}` - Atualizar lançamento
- `DELETE /lancamentos/{id}` - Deletar lançamento

#### Balanço
- `GET /balanco` - Obter balanço geral (receitas, despesas, saldo)

### Documentação Interativa (Swagger)
```
http://localhost:8080/v1/swagger-ui/index.html
```

## 💡 Exemplos de Uso

### Criar uma categoria
```bash
curl -X POST http://localhost:8080/v1/categorias \
  -H "Content-Type: application/json" \
  -H "X-API-KEY: your-secret-api-key" \
  -d '{
    "nome": "Alimentação"
  }'
```

### Criar um lançamento
```bash
curl -X POST http://localhost:8080/v1/lancamentos \
  -H "Content-Type: application/json" \
  -H "X-API-KEY: your-secret-api-key" \
  -d '{
    "valor": 150.50,
    "data": "19/01/2026",
    "id_subcategoria": 1,
    "comentario": "Compras do mercado"
  }'
```

**Formato de data:** `dd/MM/yyyy` (ex: `19/01/2026`)

## 📊 Observabilidade

### Health Checks

**Status geral:**
```bash
curl http://localhost:8080/v1/actuator/health
```

**Readiness (pronto para tráfego):**
```bash
curl http://localhost:8080/v1/actuator/health/readiness
```

**Liveness (aplicação está viva):**
```bash
curl http://localhost:8080/v1/actuator/health/liveness
```


### Métricas
```bash
curl http://localhost:8080/v1/actuator/metrics
```

Ver métrica específica:
```bash
curl http://localhost:8080/v1/actuator/metrics/jvm.memory.used
```

## 🧪 Testes

Os testes são executados automaticamente durante o build do Docker.

**Executar testes manualmente:**
```bash
./mvnw test
```

## 🔒 Segurança

- API Key obrigatória em todas as requisições (exceto health checks)
- Endpoints sensíveis do Actuator não estão expostos (`/env`, `/beans`, `/shutdown`)
- Validação de entrada em todos os DTOs

## 📁 Estrutura do Projeto

```
catalog-api/
├── src/
│   ├── main/
│   │   ├── java/com/catalog/api/
│   │   │   ├── config/          # Configurações (OpenAPI, CORS, etc)
│   │   │   ├── controller/      # Controllers REST
│   │   │   ├── domain/          # Entidades JPA
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── exception/       # Tratamento de exceções
│   │   │   ├── repository/      # Repositórios JPA
│   │   │   ├── service/         # Lógica de negócio
│   │   │   └── validation/      # Validações customizadas
│   │   └── resources/
│   │       └── application.properties
│   └── test/                    # Testes unitários
├── .env.example                 # Exemplo de variáveis de ambiente
├── docker-compose.yml           # Orquestração Docker
├── Dockerfile                   # Imagem Docker da aplicação
├── entrypoint.sh               # Script de inicialização
└── pom.xml                     # Dependências Maven
```

## 🐛 Troubleshooting

### Erro de conexão com banco
Verifique se as credenciais no `.env` estão corretas e se o container do PostgreSQL está rodando:
```bash
docker-compose ps
```