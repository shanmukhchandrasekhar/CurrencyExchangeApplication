package com.currencyexchange.currency_exchange_calculator.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class BillDetails {

//    @NotNull(message = "Bill amount cannot be null")
    private Double totalAmount;
   // @NotNull(message = "Items cannot be null")
    private List<Item> items = new ArrayList<>();
    private UserType userType;
    private int tenure;
    private String originalCurrency;
    private String targetCurrency;


}
