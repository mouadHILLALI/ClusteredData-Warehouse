package com.warehousedata.warehousedata.controller;


import com.warehousedata.warehousedata.dto.DealRequestDto;
import com.warehousedata.warehousedata.dto.DealResponseDto;
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


    @PostMapping("/import")
    public ResponseEntity<List<DealResponseDto>> importDeals(@RequestBody List<DealRequestDto> deals) {
        return null;
    }
}
