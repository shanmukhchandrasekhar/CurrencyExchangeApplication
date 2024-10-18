package com.currencyexchange.currency_exchange_calculator;

import com.currencyexchange.currency_exchange_calculator.controller.BillController;
import com.currencyexchange.currency_exchange_calculator.model.BillDetails;
import com.currencyexchange.currency_exchange_calculator.model.Item;
import com.currencyexchange.currency_exchange_calculator.model.UserType;
import com.currencyexchange.currency_exchange_calculator.service.CurrencyConversionService;
import com.currencyexchange.currency_exchange_calculator.service.DiscountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(BillController.class)
public class BillControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DiscountService discountService;

    @MockBean
    private CurrencyConversionService currencyConversionService;

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testCalculatePayableAmount() throws Exception {
        BillDetails billDetails = new BillDetails();
        billDetails.setTotalAmount(500.0);
        billDetails.setUserType(UserType.EMPLOYEE);
        billDetails.setTenure(3);
        billDetails.setOriginalCurrency("USD");
        billDetails.setTargetCurrency("INR");
        billDetails.setItems(List.of(new Item("Apple", "groceries", 100.0)));
        when(discountService.calculateDiscountedAmount(any(), anyBoolean(), anyDouble(), anyInt(), anyList()))
                .thenReturn(450.0);
        when(currencyConversionService.converCurrency(anyString(), anyString(), anyDouble()))
                .thenReturn(33000.0);
        mockMvc.perform(post("/api/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(billDetails))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("SUCCESS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(33000.0));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testInvalidRequestExceptionHandling() throws Exception {
        // Create an invalid BillDetails object
        BillDetails invalidBillDetails = new BillDetails();
        invalidBillDetails.setTotalAmount(500.0);
        invalidBillDetails.setUserType(UserType.EMPLOYEE);
        invalidBillDetails.setTenure(3);
        invalidBillDetails.setOriginalCurrency("USD");
        invalidBillDetails.setTargetCurrency("INR");
        invalidBillDetails.setItems(List.of(new Item("Apple", "groceries", 100.0)));

        // Mock the behavior of DiscountService to throw IllegalArgumentException
        when(discountService.calculateDiscountedAmount(any(), anyBoolean(), anyDouble(), anyInt(), anyList()))
                .thenThrow(new IllegalArgumentException("Invalid discount parameters"));

        // Perform the request and verify that the status is 400 Bad Request and the response contains the error message
        mockMvc.perform(post("/api/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidBillDetails))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid request: Invalid discount parameters"));
    }

}
