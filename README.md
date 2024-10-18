# CurrencyExchangeApplication

This project is a Spring Boot application that calculates currency conversion based on exchange rates from an external API. The application provides various endpoints for calculating discounts and converting currency.

## Table of Contents
- [Prerequisites](#prerequisites)
- [Setup](#setup)
- [Running Tests](#running-tests)
- [Generating Coverage Reports](#generating-coverage-reports)
- [Endpoints](#endpoints)
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

## Endpoints

### 1. Calculate Payable Amount
- **URL**: `/api/calculate`
- **Method**: `POST`
- **Request Body**:
  ```json
  {
    "userType": "CUSTOMER",
    "totalAmount": 200.0,
    "tenure": 3,
    "originalCurrency": "USD",
    "targetCurrency": "INR",
    "items": [
      {"name": "product1", "category": "electronics"},
      {"name": "product2", "category": "furniture"}
    ]
  }
  ```
  
  #### Authentication:
- **Username**: `user`
- **Password**: `password`
- **Role**: `USER`
  
- **Response**:
```json
{
    "status": "Success",
    "data": 10931.99835
}

```

### Currency Conversion (Internal API for Exchange Rates)
- **URL**: `/v6/latest/{base_currency}`
- **Method**: `GET`
- **Response**:
```json
{
  "rates": {
    "INR": 80.0,
    "EUR": 0.85,
    ...
  },
  "base": "USD",
  "date": "2023-10-16"
}


```

### Project Structure
- **src/main/java**: Contains the main code including controllers, services, and exception handling.
Controllers: Handles API requests.
Services: Contains business logic for discount calculations and currency conversion.
Exception Handling: Custom exceptions like CurrencyConversionException and UserNotFoundException.
-**src/test/java**: Contains the unit test cases for the application logic.
Tests for Services: Includes tests for discount calculations and currency conversions.
Mocking and Test Utilities: Uses Mockito for mocking dependencies like external APIs.
-**src/main/resources**: Contains application configuration files like application.properties.
application.properties: Includes configuration settings such as API keys, port configurations, etc.
