package com.warehousedata.warehousedata.service.Impl;

import com.warehousedata.warehousedata.dto.DealRequestDto;
import com.warehousedata.warehousedata.dto.DealResponseDto;
import com.warehousedata.warehousedata.mapper.DealMapper;
import com.warehousedata.warehousedata.repository.DealRepository;
import com.warehousedata.warehousedata.service.CurrencyValidator;
import com.warehousedata.warehousedata.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;
    private final DealMapper dealMapper;
    private final CurrencyValidator currencyValidator;
    @Override
    public List<DealResponseDto> saveDeals(List<DealRequestDto> deals) {
        return List.of();
    }
}
