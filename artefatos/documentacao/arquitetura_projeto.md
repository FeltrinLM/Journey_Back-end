# 🧠 Documento de Arquitetura do Projeto

## 1. Visão Geral

Este projeto backend foi desenvolvido com Java + Spring Boot, seguindo a Arquitetura em Camadas. Ele expõe uma API REST que será consumida por um front-end Angular.

---

## 2. Como Funciona a Arquitetura em Camadas

A arquitetura do projeto está dividida em **camadas hierárquicas**, onde **cada camada depende apenas da imediatamente inferior**. Essa separação permite **organização clara**, **testes unitários mais fáceis** e **baixo acoplamento**.

### 📊 Hierarquia de Camadas
```
┌────────────┐
│ Controller │
└────────────┘
      ↓
┌──────────┐
│ Service  │
└──────────┘
      ↓
┌────────────┐
│ Repository │
└────────────┘
      ↓
┌────────┐
│ Model  │
└────────┘

```
### 🔐 Camada de Segurança (transversal)

A camada `security` **intercepta requisições na borda da aplicação**, antes de chegar à camada Controller, para aplicar autenticação e autorização via JWT.

---

## 3. Camadas do Projeto

### 3.1 Controller
Responsável por receber as requisições HTTP, validar entradas básicas e delegar para os serviços.

### 3.2 Service
Contém a lógica de negócio, regras e validações. Atua como intermediário entre controller e repository.

### 3.3 Repository (DAO)
Camada de persistência. Usa Spring Data JPA para interagir com o banco de dados.

### 3.4 Model
Entidades que representam tabelas do banco de dados, anotadas com `@Entity`.

### 3.5 DTO
Objetos de transferência usados para comunicação segura entre camadas, evitando exposição de entidades.

### 3.6 Security
Configurações de segurança, autenticação JWT, filtros, validações e interceptadores.

---

## 4. Estrutura de Pastas



```
src/
└── main/
    └── java/
        └── com/
            └── example/
                └── journey_backend/
                    ├── controller/
                    │   └── PecaController.java
                    ├── service/
                    │   └── PecaService.java
                    ├── repository/
                    │   └── PecaRepository.java
                    ├── model/
                    │   └── Peca.java
                    ├── dto/
                    │   └── PecaDTO.java
                    ├── security/
                    │   ├── config/
                    │   │   └── SecurityConfig.java
                    │   └── jwt/                                          
                    └── JourneyBackEndApplication.java # Classe principal
```