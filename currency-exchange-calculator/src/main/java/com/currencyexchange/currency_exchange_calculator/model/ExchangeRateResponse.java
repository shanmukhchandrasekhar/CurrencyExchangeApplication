package com.currencyexchange.currency_exchange_calculator.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class ExchangeRateResponse {

    private String base;
    private Map<String, Double> rates;

}
