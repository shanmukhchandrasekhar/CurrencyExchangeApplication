package com.currencyexchange.currency_exchange_calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CurrencyExchangeCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyExchangeCalculatorApplication.class, args);
	}

}
