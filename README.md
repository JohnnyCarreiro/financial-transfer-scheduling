
# Projeto de Transferências Financeiras

## Sobre o Projeto

Este projeto foi desenvolvido como um exemplo de sistema para agendamento de transferências financeiras, utilizando uma arquitetura moderna e tecnologias robustas. Ele segue práticas recomendadas de desenvolvimento, incluindo **Clean Architecture**, **Domain-Driven Design (DDD)** e padrões de design.

Se desejar ver o planejamento e a organização do projeto, acesse: [Notion](https://consolidados.notion.site/Planejamento-requisitos-e-cronogramas-1524351040f18014a4b7deef5da59cde).

---

## Escolhas Tecnológicas

### Back-end

- **Java 17**: Versão LTS, moderna e eficiente.
- **Spring Boot**: Framework consolidado para criação de APIs REST.
- **Banco de Dados H2**: Banco em memória para facilitar setup e testes.
- **MapStruct**: Para mapeamento de DTOs e entidades.
- **Spring Validation**: Para validação de entrada de dados.

### Front-end

- **Vue.js 3**: Framework ágil e reativo para construção de interfaces.
- **Vite**: Ferramenta moderna de build para Vue.js, com hot-reload.
- **Axios**: Biblioteca para chamadas HTTP ao back-end.
- **Shadcn-vue**: Componentes estilizados e personalizáveis, sem acoplamento.

### Testes

- **JUnit 5** e **Mockito** para testes unitários e de integração no back-end.
- **Vue Test Utils** para testes no front-end.

---

## Arquitetura

### Back-end

#### Domain-Driven Design (DDD)

- **Entities**:
  - `Transfer`: Representa uma transferência financeira.
  - `TransferFee`: Representa a tarifa sobre a transferência.
- **Value Objects**:
  - `Account`: Representa uma conta bancária com número padrão `XXXXXXXXXX`.
- **Aggregates**:
  - `Transfer`: Gerencia a lógica de negócio de transferências.
- **Services**:
  - `TransferFeeCalculatorService`: Implementa a lógica de cálculo de taxas.
- **Repositories**:
  - Interfaces para abstrair persistência.

#### Clean Architecture

- **Camadas**:
  - **Domain**: Regras de negócio puras.
  - **Application**: Casos de uso (`CreateTransferUseCase`, `UpdateTransferUseCase`).
  - **Infrastructure**: Persistência e detalhes técnicos.
  - **API**: Exposição de endpoints REST.

### Front-end

#### Organização Modular

- **Pages**:
  - `TransferForm`: Página para agendar transferências.
  - `TransferList`: Página para exibir o extrato.
- **Components**:
  - `TransferTable`: Tabela reutilizável para exibir transferências.
  - `TransferAlert`: Para exibir mensagens de erro.
- **Services**:
  - `TransferService`: Comunicação com o back-end via Axios.
- **Rotas**:
  - `/`: Redireciona para `/transfers`.
  - `/transfers`: Lista transferências.
  - `/transfers/new`: Cadastro de transferências.

---

## Estrutura do Domínio

1. **Value Object `Account`**:
   - `accountNumber`: Número da conta no formato `XXXXXXXXXX`.

2. **Entidade `Transfer`**:
   - **Atributos**:
     - `id`, `sourceAccount`, `destinationAccount`, `amount`, `transferDate`, `scheduledDate`, `fee`, `status`.

3. **Entidade `TransferFee`**:
   - **Atributos**:
     - `name`, `minDays`, `maxDays`, `fixedFee`, `percentageFee`.

---

## Instruções para Subir o Projeto

1. Clone o repositório.
2. Navegue até a pasta do back-end e execute:

   ```bash
   cd backend
   ./gradlew bootRun --args='--seed' -Dspring.profiles.active=dev
   ```

2. Navegue até a pasta do front-end e execute:

   ```bash
   cd front-end
   npm install
   npm run dev
   ```

# Financial Transfer Project

## About the Project

This project is a sample system for scheduling financial transfers using modern architecture and robust technologies. It follows development best practices, including **Clean Architecture**, **Domain-Driven Design (DDD)**, and design patterns.

For planning and project organization, visit: [Notion](https://consolidados.notion.site/Planejamento-requisitos-e-cronogramas-1524351040f18014a4b7deef5da59cde).

---

## Technological Choices

### Back-end

- **Java 17**: LTS version, modern and efficient.
- **Spring Boot**: Framework for creating REST APIs.
- **H2 Database**: In-memory database for easy setup and testing.
- **MapStruct**: For mapping DTOs and entities.
- **Spring Validation**: For data input validation.

### Front-end

- **Vue.js 3**: Agile and reactive framework for building interfaces.
- **Vite**: Modern build tool for Vue.js with hot-reload.
- **Axios**: HTTP library for back-end communication.
- **Shadcn-vue**: Pre-styled and customizable components.

### Testing

- **JUnit 5** and **Mockito** for unit and integration testing on the back-end.
- **Vue Test Utils** for front-end testing.

---

## Architecture

### Back-end

#### Domain-Driven Design (DDD)

- **Entities**:
  - `Transfer`: Represents a financial transfer.
  - `TransferFee`: Represents the transfer fee logic.
- **Value Objects**:
  - `Account`: Represents a bank account with format `XXXXXXXXXX`.
- **Aggregates**:
  - `Transfer`: Manages business logic for transfers.
- **Services**:
  - `TransferFeeCalculatorService`: Implements fee calculation logic.
- **Repositories**:
  - Interfaces for persistence abstraction.

#### Clean Architecture

- **Layers**:
  - **Domain**: Pure business rules.
  - **Application**: Use cases (`CreateTransferUseCase`, `UpdateTransferUseCase`).
  - **Infrastructure**: Persistence and technical details.
  - **API**: REST endpoints.

### Front-end

#### Modular Organization

- **Pages**:
  - `TransferForm`: Page to schedule transfers.
  - `TransferList`: Page to display the statement.
- **Components**:
  - `TransferTable`: Reusable table to display transfers.
  - `TransferAlert`: For error messages.
- **Services**:
  - `TransferService`: Back-end communication via Axios.
- **Routes**:
  - `/`: Redirects to `/transfers`.
  - `/transfers`: Lists transfers.
  - `/transfers/new`: Transfer registration.

---

## Domain Structure

1. **Value Object `Account`**:
   - `accountNumber`: Account number in the format `XXXXXXXXXX`.

2. **Entity `Transfer`**:
   - **Attributes**:
     - `id`, `sourceAccount`, `destinationAccount`, `amount`, `transferDate`, `scheduledDate`, `fee`, `status`.

3. **Entity `TransferFee`**:
   - **Attributes**:
     - `name`, `minDays`, `maxDays`, `fixedFee`, `percentageFee`.

---

## Instructions to Run the Project

1. Clone the repository.
2. Navigate to the back-end folder and execute:

   ```bash
   cd backend
   ./gradlew bootRun --args='--seed' -Dspring.profiles.active=dev
   ```

3. Navigate to the front-end folder and execute:

   ```bash
   cd front-end
   npm install
   npm run dev
   ```
