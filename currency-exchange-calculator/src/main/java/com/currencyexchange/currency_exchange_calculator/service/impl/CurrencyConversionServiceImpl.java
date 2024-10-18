package com.currencyexchange.currency_exchange_calculator.service.impl;

import com.currencyexchange.currency_exchange_calculator.client.ExchangeRateClient;
import com.currencyexchange.currency_exchange_calculator.exception.CurrencyConversionException;
import com.currencyexchange.currency_exchange_calculator.model.ExchangeRateResponse;
import com.currencyexchange.currency_exchange_calculator.service.CurrencyConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CurrencyConversionServiceImpl implements CurrencyConversionService {

    @Value("${currency.exchange.api.key}")
    private String apiKey;

    @Autowired
    private ExchangeRateClient exchangeRateClient;

    public double converCurrency(String originalCurrency, String targetCurrency, double discountedAmount) {
        try{
            ExchangeRateResponse response = exchangeRateClient.getExchangeRates(originalCurrency,apiKey);
            if(response==null ||
                    response.getRates()==null ||
                    !response.getRates().containsKey(targetCurrency)){
                throw new CurrencyConversionException("Unable to retrieve rate for"+ originalCurrency);
            }
            double exchangeRate = response.getRates().get(targetCurrency);
            return discountedAmount * exchangeRate;
        } catch (Exception e){
            e.getMessage();
            throw new CurrencyConversionException("Failed to convert currency from " + originalCurrency + " to " + targetCurrency);
        }
    }
}
