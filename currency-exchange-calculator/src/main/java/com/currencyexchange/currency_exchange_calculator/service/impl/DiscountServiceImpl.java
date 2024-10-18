package com.currencyexchange.currency_exchange_calculator.service.impl;

import com.currencyexchange.currency_exchange_calculator.exception.UserNotFoundException;
import com.currencyexchange.currency_exchange_calculator.model.Item;
import com.currencyexchange.currency_exchange_calculator.model.UserType;
import com.currencyexchange.currency_exchange_calculator.service.DiscountService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {
    public double calculateDiscountedAmount(UserType userType, boolean isGroceries, double billAmount, int tenure, List<Item> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Items list cannot be null or empty");
        }
            double discount = 0;
            double flatDiscount = 0;
            if (!isGroceries) {
                if (userType == UserType.EMPLOYEE) {
                    discount = 0.30;
                } else if (userType == UserType.AFFILIATE) {
                    discount = 0.10;
                } else if (userType == UserType.CUSTOMER && tenure >= 2) {
                    discount = 0.05;
                } else {
                    throw new UserNotFoundException("User is not eligible for any discount");
                }
            }
            double percentageDiscount = billAmount * discount;
            if (billAmount >= 100) {
                flatDiscount = ((int) (billAmount / 100)) * 5;
            }
            return billAmount - percentageDiscount - flatDiscount;
    }
}
