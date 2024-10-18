package com.currencyexchange.currency_exchange_calculator.service.impl;

import com.currencyexchange.currency_exchange_calculator.exception.UserNotFoundException;
import com.currencyexchange.currency_exchange_calculator.model.BillDetails;
import com.currencyexchange.currency_exchange_calculator.model.Item;
import com.currencyexchange.currency_exchange_calculator.model.UserType;
import com.currencyexchange.currency_exchange_calculator.service.DiscountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {

    private static final Logger logger = LoggerFactory.getLogger(DiscountServiceImpl.class);

    public double calculateDiscountedAmount(BillDetails billDetails) {
        if (billDetails.getItems() == null || billDetails.getItems().isEmpty()) {
            throw new IllegalArgumentException("Items list cannot be null or empty");
        }
        double billAmount = billDetails.getTotalAmount();
        UserType userType = billDetails.getUserType();
        List<Item> items = billDetails.getItems();
        boolean isGroceries = items.stream().anyMatch(item -> "groceries".equalsIgnoreCase(item.getCategory()));
        int tenure = billDetails.getTenure();
            double discount = 0;
            double flatDiscount = 0;

        logger.info("Calculating discount for userType: {}, billAmount: {}, isGroceries: {}, tenure: {}", userType, billAmount, isGroceries, tenure);

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
