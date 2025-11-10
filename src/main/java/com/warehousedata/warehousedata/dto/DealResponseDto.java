package com.warehousedata.warehousedata.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DealResponseDto(
        String id,
        String fromCurrency,
        String toCurrency,
        LocalDateTime dealTimeStamp,
        BigDecimal dealAmount
) {
}
