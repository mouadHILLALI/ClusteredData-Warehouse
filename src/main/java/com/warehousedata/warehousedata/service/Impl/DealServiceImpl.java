package com.warehousedata.warehousedata.service.Impl;

import com.warehousedata.warehousedata.dto.DealRequestDto;
import com.warehousedata.warehousedata.dto.DealResponseDto;
import com.warehousedata.warehousedata.entity.Deal;
import com.warehousedata.warehousedata.mapper.DealMapper;
import com.warehousedata.warehousedata.repository.DealRepository;
import com.warehousedata.warehousedata.service.CurrencyValidator;
import com.warehousedata.warehousedata.service.DealService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;
    private final DealMapper dealMapper;
    private final CurrencyValidator currencyValidator;

    @Override
    @Transactional
    public List<DealResponseDto> saveDeals(List<DealRequestDto> deals) {
        if (deals == null || deals.isEmpty()) {
            log.warn("No deals to process.");
            return List.of();
        }

        Set<Deal> validDeals = filterInvalidDealRows(deals);

        log.info("Total deals received: {}", deals.size());
        log.info("Valid deals: {}", validDeals.size());
        log.info("Invalid deals: {}", deals.size() - validDeals.size());

        return validDeals.stream()
                .peek(deal -> log.info("Saving deal {}", deal))
                .map(dealRepository::save)
                .map(dealMapper::toResDto)
                .toList();
    }


    private Set<Deal> filterInvalidDealRows(List<DealRequestDto> deals) {
        if (deals == null) return new HashSet<>();
        log.debug("Filtering invalid rows from {} deals", deals.size());

        return deals.stream()
                .peek(deal -> {
                    if (!isValidDeal(deal)) log.warn("Invalid deal found and skipped: {}", deal);
                })
                .filter(this::isValidDeal).map(dealMapper::toEntity)
                .collect(Collectors.toSet());
    }


    private boolean isValidDeal(DealRequestDto deal) {
        if (deal == null) {
            log.warn("Encountered null deal entry");
            return false;
        }
        return deal.id() != null && !deal.id().isBlank() &&
                deal.fromCurrency() != null && !deal.fromCurrency().isBlank() &&
                deal.toCurrency() != null && !deal.toCurrency().isBlank() &&
                deal.dealAmount() != null && deal.dealAmount().compareTo(BigDecimal.ZERO) > 0 &&
                deal.dealTimeStamp() != null &&
                currencyValidator.validateCurrency(deal.fromCurrency(), deal.toCurrency());
    }

}
