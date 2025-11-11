package com.warehousedata.warehousedata.config;

import java.io.IOException;
import java.util.Set;

public interface CsvFileReader {
    Set<String> readCurrencyCodesFile() throws IOException;

    Set<String> getCurrencyCodes() throws IOException;
}
