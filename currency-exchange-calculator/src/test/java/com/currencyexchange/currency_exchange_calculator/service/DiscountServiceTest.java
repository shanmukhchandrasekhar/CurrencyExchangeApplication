package com.currencyexchange.currency_exchange_calculator.service;

import com.currencyexchange.currency_exchange_calculator.exception.UserNotFoundException;
import com.currencyexchange.currency_exchange_calculator.model.Item;
import com.currencyexchange.currency_exchange_calculator.model.UserType;
import com.currencyexchange.currency_exchange_calculator.service.DiscountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DiscountServiceTest {

    @Autowired
    private DiscountService discountService;

    @Test
    public void testEmployeeDiscount() {
        List<Item> items = List.of(new Item("product", "electronics"));
        double result = discountService.calculateDiscountedAmount(UserType.EMPLOYEE, false, 200.0, 3, items);
        assertEquals(130.0, result, 0.0);
    }

    @Test
    public void testEmployeeDiscountOne() {
        List<Item> items = List.of(new Item("product", "electronics"));
        double result = discountService.calculateDiscountedAmount(UserType.EMPLOYEE, false, 90.0, 3, items);
        assertEquals(63.0, result, 0.0);
    }

    @Test
    public void testAffiliateDiscount() {
        List<Item> items = List.of(new Item("product", "electronics"));
        double result = discountService.calculateDiscountedAmount(UserType.AFFILIATE, false, 200.0, 1, items);
        assertEquals(170.0, result, 0.0);
    }

    @Test
    public void testCustomerDiscount() {
        List<Item> items = List.of(new Item("product", "electronics"));
        double result = discountService.calculateDiscountedAmount(UserType.CUSTOMER, false, 200.0, 3, items);
        assertEquals(180.0, result, 0.0);
    }

    @Test
    public void testNoDiscountOnGroceries() {
        List<Item> items = List.of(new Item("product", "groceries"));
        double result = discountService.calculateDiscountedAmount(UserType.EMPLOYEE, true, 200.0, 3, items);
        assertEquals(190.0, result, 0.0);
    }

    @Test
    public void testUserNotFoundException() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Apple", "groceries", 100.0));
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            discountService.calculateDiscountedAmount(UserType.CUSTOMER, false, 200.0, 1, items);
        });
        assertEquals("User is not eligible for any discount", exception.getMessage());
    }

    @Test
    public void testInvalidItemsList_Null() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            discountService.calculateDiscountedAmount(UserType.EMPLOYEE, false, 200.0, 3, null);
        });
        assertEquals("Items list cannot be null or empty", exception.getMessage());
    }

    @Test
    public void testInvalidItemsList_Empty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            discountService.calculateDiscountedAmount(UserType.EMPLOYEE, false, 200.0, 3, new ArrayList<>());
        });
        assertEquals("Items list cannot be null or empty", exception.getMessage());
    }
}