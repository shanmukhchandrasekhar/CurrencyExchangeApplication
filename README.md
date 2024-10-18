# CurrencyExchangeApplication

This project is a Spring Boot application that calculates currency conversion based on exchange rates from an external API. The application provides various endpoints for calculating discounts and converting currency.

## Table of Contents
- [Prerequisites](#prerequisites)
- [Setup](#setup)
- [Running Tests](#running-tests)
- [Generating Coverage Reports](#generating-coverage-reports)
- [Endpoints](#endpoints)
- [Exception Handling](#exception-handling)
- [Project Structure](#project-structure)

---

## Prerequisites

Ensure you have the following installed:
- **Java 8 or higher**
- **Maven 3.6 or higher**
- **An IDE (e.g., IntelliJ IDEA, Eclipse)** or command line for running the application

---

## Setup

1. Clone the repository:

    ```bash
    git clone https://github.com/your-username/currency-exchange-calculator.git
    cd currency-exchange-calculator
    ```

2. Set up your local environment by adding the API key, url and port for currency exchange in `src/main/resources/application.properties`:

    ```properties
    currency.exchange.api.key=your_api_key_here
    server.port=9091
    currency.exchange.api.url=https://open.er-api.com
     currency.exchange.api.key=your-api-key
    ```

---

## Running Tests

To run the test cases, use Maven:

```bash
mvn test
```
---

## Generating Coverage Reports
To generate code coverage reports, follow these steps:

Run the following command to execute the tests and generate the coverage report:
```bash
mvn clean test jacoco:report
```
After the tests are complete, you can find the code coverage report at:
```bash
target/site/jacoco/index.html
```

---
