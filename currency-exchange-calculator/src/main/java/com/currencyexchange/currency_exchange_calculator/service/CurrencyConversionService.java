package com.currencyexchange.currency_exchange_calculator.service;

import org.springframework.stereotype.Service;

@Service
public interface CurrencyConversionService {
    public double converCurrency(String originalCurrency, String targetCurrency, double discountedAmount);
}
