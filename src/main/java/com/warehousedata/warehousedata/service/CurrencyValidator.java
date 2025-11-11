package com.warehousedata.warehousedata.service;

import com.warehousedata.warehousedata.exception.InvalidCurrencyFormat;

public interface CurrencyValidator {
    boolean validateCurrency(String fromCurrency, String toCurrency) throws InvalidCurrencyFormat;
}
