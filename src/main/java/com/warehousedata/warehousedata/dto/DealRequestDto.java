package com.warehousedata.warehousedata.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DealRequestDto(
        @NotBlank(message = "Deal Id cannot be null") String id,
        @NotBlank(message = "From currency cannot be null") String fromCurrency,
        @NotBlank(message = "To currency cannot be null") String toCurrency,
        @NotNull(message = "Deal time stamp cannot be null") LocalDateTime dealTimeStamp,
        @NotNull(message = "Deal amount cannot be null")
        @DecimalMin(value = "0.01", inclusive = true, message = "Deal amount must be greater than 0")
        BigDecimal dealAmount) {
}
