package com.currencyexchange.currency_exchange_calculator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private String name;
    private String category;
    private Double price;

    public Item(String name, String category) {
    }
}
