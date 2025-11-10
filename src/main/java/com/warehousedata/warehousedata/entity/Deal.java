package com.warehousedata.warehousedata.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "deals")
public class Deal {

    @Id
    private String id;

    @Pattern(regexp = "^[A-Z]{3}$", message = "Invalid currency code format")
    @Column(name = "from_currency", nullable = false, length = 3)
    @Size(min = 3, max = 3)
    @NotBlank
    private String fromCurrency;

    @Pattern(regexp = "^[A-Z]{3}$", message = "Invalid currency code format")
    @Column(name = "to_currency", nullable = false, length = 3)
    @Size(min = 3, max = 3)
    @NotBlank
    private String toCurrency;

    @Column(name = "deal_timestamp", nullable = false)
    @NotNull
    private LocalDateTime dealTimestamp;

    @Positive
    @Column(name = "deal_amount", nullable = false)
    @NotNull
    private BigDecimal dealAmount;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        Deal deal = (Deal) o;
        return Objects.equals(deal.id, this.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dealAmount, dealTimestamp, toCurrency);
    }

    @Override
    public String toString() {
        return String.format(
                "Deal{id='%s', from='%s', to='%s', amount=%s, timestamp=%s}",
                id, fromCurrency, toCurrency, dealAmount, dealTimestamp
        );
    }
}
