package com.warehousedata.warehousedata.service.Impl;

import com.warehousedata.warehousedata.service.CurrencyValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CurrencyValidatorImpl implements CurrencyValidator {


    @Override
    public boolean validateCurrency(String fromCurrency, String toCurrency) {
        return false;
    }
}
