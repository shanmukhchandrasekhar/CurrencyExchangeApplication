package com.currencyexchange.currency_exchange_calculator.controller;

import com.currencyexchange.currency_exchange_calculator.model.BillDetails;
import com.currencyexchange.currency_exchange_calculator.model.StatusResponse;
import com.currencyexchange.currency_exchange_calculator.service.CurrencyConversionService;
import com.currencyexchange.currency_exchange_calculator.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BillController {

    @Autowired
    private DiscountService discountService;
    @Autowired
    private CurrencyConversionService currencyConversionService;

    @PostMapping("/calculate")
    public ResponseEntity<?> calculatePaybleAmount(@RequestBody BillDetails billDetails) {
        try {
            double totalBill = billDetails.getTotalAmount();
            boolean isGroceries = billDetails.getItems().stream()
                    .anyMatch(item -> "groceries".equalsIgnoreCase(item.getCategory()));
            double discountedAmount = discountService.calculateDiscountedAmount(billDetails.getUserType(), isGroceries, totalBill, billDetails.getTenure(), billDetails.getItems());
            double payableAmount = currencyConversionService.converCurrency(billDetails.getOriginalCurrency(), billDetails.getTargetCurrency(), discountedAmount);

            StatusResponse response = new StatusResponse("SUCCESS", payableAmount);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body("Invalid request: " + ex.getMessage());
        }
    }
}
