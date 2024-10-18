package com.currencyexchange.currency_exchange_calculator.controller;

import com.currencyexchange.currency_exchange_calculator.model.BillDetails;
import com.currencyexchange.currency_exchange_calculator.model.StatusResponse;
import com.currencyexchange.currency_exchange_calculator.service.CurrencyConversionService;
import com.currencyexchange.currency_exchange_calculator.service.DiscountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BillController {
    private static final Logger logger = LoggerFactory.getLogger(BillController.class);

    @Autowired
    private DiscountService discountService;
    @Autowired
    private CurrencyConversionService currencyConversionService;

    @PostMapping("/calculate")
    public ResponseEntity<StatusResponse> calculatePaybleAmount(@RequestBody BillDetails billDetails) {
        logger.info("Received request to calculate payable amount for billDetails: {}", billDetails);
        try {
            double discountedAmount = discountService.calculateDiscountedAmount(billDetails);
            double payableAmount = currencyConversionService.converCurrency(billDetails.getOriginalCurrency(), billDetails.getTargetCurrency(), discountedAmount);
            StatusResponse response = new StatusResponse("SUCCESS", payableAmount, null);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            logger.error("Error occurred while calculating payable amount", ex);
            return ResponseEntity.badRequest().body( new StatusResponse("ERROR", null, "Invalid request: " + ex.getMessage()));
        }
    }
}
