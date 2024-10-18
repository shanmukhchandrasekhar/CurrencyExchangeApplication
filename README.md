# CurrencyExchangeApplication

This project is a Spring Boot application that calculates currency conversion based on exchange rates from an external API. The application provides various endpoints for calculating discounts and converting currency.

## Table of Contents
- [Prerequisites](#prerequisites)
- [Setup](#setup)
- [Running the Application](#running-the-application)
- [Running Tests](#running-tests)
- [Generating Coverage Reports](#generating-coverage-reports)
- [Endpoints](#endpoints)
- [Exception Handling](#exception-handling)
- [Project Structure](#project-structure)

---

## Prerequisites

Ensure you have the following installed:
- **Java 11 or higher**
- **Maven 3.6 or higher**
- **An IDE (e.g., IntelliJ IDEA, Eclipse)** or command line for running the application
- **API Key for currency exchange** (You can set it in `src/main/resources/application.properties`)

---

## Setup

1. Clone the repository:

    ```bash
    git clone https://github.com/your-username/currency-exchange-calculator.git
    cd currency-exchange-calculator
    ```

2. Set up your local environment by adding the API key for currency exchange in `src/main/resources/application.properties`:

    ```properties
    currency.exchange.api.key=your_api_key_here
    ```

    Replace `your_api_key_here` with your actual API key.

---

## Running the Application

To start the Spring Boot application, use the following Maven command:

```bash
mvn spring-boot:run
