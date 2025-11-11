package com.warehousedata.warehousedata.service;

import com.warehousedata.warehousedata.dto.DealRequestDto;
import com.warehousedata.warehousedata.dto.DealResponseDto;

import java.util.List;

public interface DealService {
    List<DealResponseDto> saveDeals(List<DealRequestDto> deals);

    DealResponseDto save(DealRequestDto dealRequestDto);
}
