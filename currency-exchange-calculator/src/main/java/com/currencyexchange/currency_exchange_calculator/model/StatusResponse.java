package com.currencyexchange.currency_exchange_calculator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusResponse {

    private String status;
    private Double data;
    private String message;

    public StatusResponse(String status, String message) {
    }

    public StatusResponse(String status, Double data) {
    }

}
