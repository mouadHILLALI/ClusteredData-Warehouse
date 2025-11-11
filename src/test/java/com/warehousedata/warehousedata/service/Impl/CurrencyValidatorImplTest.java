package com.warehousedata.warehousedata.service.Impl;

import com.warehousedata.warehousedata.config.CsvFileReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyValidatorImplTest {

    @Mock
    private CsvFileReader csvFileReader;

    private CurrencyValidatorImpl currencyValidator;

    @BeforeEach
    void setUp() {
        currencyValidator = new CurrencyValidatorImpl(csvFileReader);
    }

    @Test
    void validateCurrency_shouldReturnTrueForValidCurrencies() throws IOException {
        when(csvFileReader.getCurrencyCodes()).thenReturn(Set.of("USD", "EUR", "GBP"));

        boolean result = currencyValidator.validateCurrency("USD", "EUR");

        assertTrue(result);
    }

    @Test
    void validateCurrency_shouldReturnFalseIfFromCurrencyIsInvalid() throws IOException {
        when(csvFileReader.getCurrencyCodes()).thenReturn(Set.of("USD", "EUR", "GBP"));

        boolean result = currencyValidator.validateCurrency("ABC", "EUR");

        assertFalse(result);
    }

    @Test
    void validateCurrency_shouldReturnFalseIfToCurrencyIsInvalid() throws IOException {
        when(csvFileReader.getCurrencyCodes()).thenReturn(Set.of("USD", "EUR", "GBP"));

        boolean result = currencyValidator.validateCurrency("USD", "XYZ");

        assertFalse(result);
    }

    @Test
    void validateCurrency_shouldReturnFalseIfCurrenciesAreIdentical() throws IOException {
        when(csvFileReader.getCurrencyCodes()).thenReturn(Set.of("USD", "EUR", "GBP"));

        boolean result = currencyValidator.validateCurrency("USD", "USD");

        assertFalse(result);
    }

    @Test
    void validateCurrency_shouldReturnFalseIfCsvReadingFails() throws IOException {
        when(csvFileReader.getCurrencyCodes()).thenThrow(new IOException("File error"));

        boolean result = currencyValidator.validateCurrency("USD", "EUR");

        assertFalse(result);
    }

    @Test
    void validateCurrency_shouldReturnFalseIfCurrencyIsLowercaseOrInvalidPattern() throws IOException {
        when(csvFileReader.getCurrencyCodes()).thenReturn(Set.of("USD", "EUR", "GBP"));

        assertFalse(currencyValidator.validateCurrency("usd", "EUR"));
        assertFalse(currencyValidator.validateCurrency("US1", "EUR"));
    }
}
