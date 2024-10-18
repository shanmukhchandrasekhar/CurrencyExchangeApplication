package com.currencyexchange.currency_exchange_calculator.client;

import com.currencyexchange.currency_exchange_calculator.model.ExchangeRateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="exchangeRateClient", url="${currency.exchange.api.url}")
public interface ExchangeRateClient {
    @GetMapping("/v6/latest/{base_currency}")
    ExchangeRateResponse getExchangeRates(
            @PathVariable("base_currency") String baseCurrency,
            @RequestParam("apikey") String apiKey
    );
}