package com.warehousedata.warehousedata.controller;


import com.warehousedata.warehousedata.dto.DealRequestDto;
import com.warehousedata.warehousedata.dto.DealResponseDto;
import com.warehousedata.warehousedata.service.DealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/deals")
@RequiredArgsConstructor
public class DealController {

    private final DealService dealService;

    @PostMapping("/import/batch")
    public ResponseEntity<List<DealResponseDto>> importDeals(
            @RequestBody @Valid List<DealRequestDto> deals) {
        return ResponseEntity.ok(dealService.saveDeals(deals));
    }

    @PostMapping("/import")
    public ResponseEntity<DealResponseDto> importDeal(
            @RequestBody @Valid DealRequestDto deal) {
        return ResponseEntity.ok(dealService.save(deal));
    }

}
