package com.warehousedata.warehousedata.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CsvFileReaderImpl implements CsvFileReader, ApplicationRunner {

    private static final String CURRENCIES_FILE_PATH = "currency_codes.csv";
    private final Set<String> currencyCodes = new HashSet<>();

    @Override
    public Set<String> readCurrencyCodesFile() throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(getClass().getClassLoader()
                                .getResourceAsStream(CURRENCIES_FILE_PATH)),
                        StandardCharsets.UTF_8))) {
            return reader.lines()
                    .map(String::trim)
                    .filter(line -> !line.isBlank())
                    .map(String::toUpperCase)
                    .collect(Collectors.toSet());
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            log.info("Reading currency codes from file...");
            currencyCodes.addAll(readCurrencyCodesFile());
        } catch (IOException e) {
            log.error("Error reading currency codes from file", e);
            throw e;
        }
    }

    public Set<String> getCurrencyCodes() {
        return currencyCodes;
    }
}
