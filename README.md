# 🎬 ScreenMatch

Projeto Full Stack desenvolvido para praticar Java com Spring Boot no backend e HTML, CSS e JavaScript no frontend.

O ScreenMatch é uma aplicação estilo catálogo de streaming que permite importar séries da API OMDb, armazenar no banco de dados e exibir as informações no frontend.

---

## 🚀 Tecnologias Utilizadas

### 🔙 Backend
- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven

### 🌐 Frontend
- HTML5
- CSS3
- JavaScript (ES Modules)
- Fetch API

### 🔗 API Externa
- OMDb API (http://www.omdbapi.com)

---

## 🏗️ Estrutura do Projeto

```
screenmatchSpring/
│
├── src/                → Backend Spring Boot
├── frontend/           → Frontend (HTML, CSS, JS)
├── pom.xml
└── README.md
```

---

## ⚙️ Funcionalidades

### 📥 Importar Série
Importa uma série pelo nome, busca dados na OMDb e salva no banco junto com todos os episódios automaticamente.

Endpoint:
```
POST /series/importar?titulo=NomeDaSerie
```

---

### 🔎 Buscar Série por Nome

```
GET /series/nome/{nome}
```

---

### 🏆 Top 5 Séries

```
GET /series/top5
```

---

### 🆕 Lançamentos

```
GET /series/lancamentos
```

---

### 📚 Listar Todas as Séries

```
GET /series
```

---

### ❌ Deletar Série

```
DELETE /series/{id}
```

---

## 🗄️ Banco de Dados

O projeto utiliza PostgreSQL.

Exemplo de configuração no `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/screenmatch
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

spring.jpa.hibernate.ddl-auto=update
```

---

## ▶️ Como Executar o Projeto

### 1️⃣ Executar Backend

Na pasta raiz do projeto:

```bash
mvn spring-boot:run
```

O backend ficará disponível em:

```
http://localhost:8080
```

---

### 2️⃣ Executar Frontend

Abra o arquivo:

```
frontend/index.html
```

Ou utilize uma extensão como Live Server no VS Code.

---

## 📌 Objetivos do Projeto

Este projeto foi desenvolvido com foco em:

- Aplicar arquitetura em camadas (Controller, Service, Repository)
- Trabalhar com relacionamentos JPA (OneToMany)
- Consumir API externa
- Construir uma API REST estruturada
- Integrar frontend com backend real
- Praticar boas práticas de organização de projeto

---

## 🌍 Próximas Melhorias

- Deploy do backend na nuvem
- Deploy do frontend no GitHub Pages
- Dockerização
- Paginação
- Tratamento global de exceções
- Autenticação de usuários
  
---
## 👨‍💻 Autor

Patrick Rebecchi  
Estudante de Engenharia de Software  
Foco em Desenvolvimento Backend Java
