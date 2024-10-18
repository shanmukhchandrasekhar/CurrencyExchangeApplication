package com.currencyexchange.currency_exchange_calculator.service;

import com.currencyexchange.currency_exchange_calculator.exception.UserNotFoundException;
import com.currencyexchange.currency_exchange_calculator.model.BillDetails;
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

    private BillDetails createBillDetails(UserType userType, double totalAmount, int tenure, List<Item> items) {
        BillDetails billDetails = new BillDetails();
        billDetails.setUserType(userType);
        billDetails.setTotalAmount(totalAmount);
        billDetails.setTenure(tenure);
        billDetails.setItems(items);
        billDetails.setOriginalCurrency("USD");
        billDetails.setTargetCurrency("INR");
        return billDetails;
    }

    @Test
    public void testEmployeeDiscount() {
        BillDetails billDetails = createBillDetails(UserType.EMPLOYEE, 200.0, 3, List.of(new Item("product", "electronics")));
        double result = discountService.calculateDiscountedAmount(billDetails);
        assertEquals(130.0, result, 0.0);
    }

    @Test
    public void testEmployeeDiscountOne() {
        BillDetails billDetails = createBillDetails(UserType.EMPLOYEE, 90.0, 3, List.of(new Item("product", "electronics")));
        double result = discountService.calculateDiscountedAmount(billDetails);
        assertEquals(63.0, result, 0.0);
    }

    @Test
    public void testAffiliateDiscount() {
        BillDetails billDetails = createBillDetails(UserType.AFFILIATE, 200.0, 1, List.of(new Item("product", "electronics")));
        double result = discountService.calculateDiscountedAmount(billDetails);
        assertEquals(170.0, result, 0.0);
    }

    @Test
    public void testCustomerDiscount() {
        BillDetails billDetails = createBillDetails(UserType.CUSTOMER, 200.0, 3, List.of(new Item("product", "electronics")));
        double result = discountService.calculateDiscountedAmount(billDetails);
        assertEquals(180.0, result, 0.0);
    }

    @Test
    public void testNoDiscountOnGroceries() {
        BillDetails billDetails = createBillDetails(UserType.EMPLOYEE, 200.0, 3, List.of(new Item("product", "groceries", 100.0)));
        double result = discountService.calculateDiscountedAmount(billDetails);
        assertEquals(190.0, result, 0.0);
    }

    @Test
    public void testUserNotFoundException() {
        List<Item> items = List.of(new Item("Apple", "electronics", 100.0)); // Use a non-grocery item
        BillDetails billDetails = createBillDetails(UserType.CUSTOMER, 200.0, 1, items); // Tenure < 2
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            discountService.calculateDiscountedAmount(billDetails);
        });
        assertEquals("User is not eligible for any discount", exception.getMessage());
    }


    @Test
    public void testInvalidItemsList_Null() {
        BillDetails billDetails = createBillDetails(UserType.EMPLOYEE, 200.0, 3, null); // null items

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            discountService.calculateDiscountedAmount(billDetails);
        });
        assertEquals("Items list cannot be null or empty", exception.getMessage());
    }

    @Test
    public void testInvalidItemsList_Empty() {
        BillDetails billDetails = createBillDetails(UserType.EMPLOYEE, 200.0, 3, List.of()); // empty items list

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            discountService.calculateDiscountedAmount(billDetails);
        });
        assertEquals("Items list cannot be null or empty", exception.getMessage());
    }
}