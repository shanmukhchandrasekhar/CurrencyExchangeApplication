package com.currencyexchange.currency_exchange_calculator;

import com.currencyexchange.currency_exchange_calculator.client.ExchangeRateClient;
import com.currencyexchange.currency_exchange_calculator.exception.CurrencyConversionException;
import com.currencyexchange.currency_exchange_calculator.model.ExchangeRateResponse;
import com.currencyexchange.currency_exchange_calculator.service.CurrencyConversionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CurrencyConversionServiceTest {

    @Autowired
    private CurrencyConversionService currencyConversionService;

    @MockBean
    private ExchangeRateClient exchangeRateClient;

    @Test
    public void testSuccessfulConversion() {
        ExchangeRateResponse mockResponse = new ExchangeRateResponse();
        Map<String, Double> rates = new HashMap<>();
        rates.put("INR", 80.0);
        mockResponse.setRates(rates);
        Mockito.when(exchangeRateClient.getExchangeRates(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(mockResponse);
        double result = currencyConversionService.converCurrency("USD", "INR", 100.0);
        assertEquals(8000.0, result, 0.0);
    }

    @Test
    public void testFailedConversion() {
        Mockito.when(exchangeRateClient.getExchangeRates(Mockito.anyString(), Mockito.anyString()))
                .thenThrow(new CurrencyConversionException("Unable to retrieve rate"));
        assertThrows(CurrencyConversionException.class, () -> {
            currencyConversionService.converCurrency("USD", "INR", 100.0);
        });
    }

    @Test
    public void testCurrencyConversion_ExceptionForMissingTargetCurrency() {
        ExchangeRateResponse mockResponse = new ExchangeRateResponse();
        Map<String, Double> rates = new HashMap<>();
        rates.put("USD", 1.0);
        mockResponse.setRates(rates);
        Mockito.when(exchangeRateClient.getExchangeRates(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(mockResponse);
        CurrencyConversionException exception = assertThrows(CurrencyConversionException.class, () -> {
            currencyConversionService.converCurrency("USD", "INR", 100.0);
        });
        assertEquals("Failed to convert currency from USD to INR", exception.getMessage());
    }
    @Test
    public void testCurrencyConversion_RatesAreNull() {
        ExchangeRateResponse mockResponse = new ExchangeRateResponse();
        mockResponse.setRates(null);
        Mockito.when(exchangeRateClient.getExchangeRates(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(mockResponse);
        CurrencyConversionException exception = assertThrows(CurrencyConversionException.class, () -> {
            currencyConversionService.converCurrency("USD", "INR", 100.0);
        });
        assertEquals("Failed to convert currency from USD to INR", exception.getMessage());
    }
    @Test
    public void testCurrencyConversion_ResponseNull() {
        ExchangeRateResponse mockResponse = null;

        Mockito.when(exchangeRateClient.getExchangeRates(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(null);
        CurrencyConversionException exception = assertThrows(CurrencyConversionException.class, () -> {
            currencyConversionService.converCurrency("USD", "INR", 100.0);
        });
        assertEquals("Failed to convert currency from USD to INR", exception.getMessage());
    }
}

