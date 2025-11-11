package com.warehousedata.warehousedata.service.Impl;

import com.warehousedata.warehousedata.config.CsvFileReader;
import com.warehousedata.warehousedata.service.CurrencyValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class CurrencyValidatorImpl implements CurrencyValidator {

    private final CsvFileReader csvFileReader;
    private final String CURRENCY_PATTERN = "^[A-Z]{3}$";

    @Override
    public boolean validateCurrency(String fromCurrency, String toCurrency) {
        try {
            Set<String> currencyCodes = csvFileReader.getCurrencyCodes();
            if (!isValid(fromCurrency, currencyCodes) || !isValid(toCurrency, currencyCodes)) {
                log.warn("Invalid currency code(s): {} -> {}", fromCurrency, toCurrency);
                return false;
            }
            if (fromCurrency.equals(toCurrency)) {
                log.info("Invalid deal: currencies are identical");
                return false;
            }
            return true;
        } catch (IOException e) {
            log.error("Error reading currency codes from CSV file");
            return false;
        }

    }

    private boolean isValid(String currency, Set<String> currencyCodes) {
        return currency != null && currencyCodes.contains(currency) && currency.matches(CURRENCY_PATTERN);
    }


}
