package com.currencyexchange.currency_exchange_calculator.service;

import com.currencyexchange.currency_exchange_calculator.model.Item;
import com.currencyexchange.currency_exchange_calculator.model.UserType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DiscountService {
    double calculateDiscountedAmount(UserType userType, boolean isGroceries, double billAmount, int tenure, List<Item> items);
}
