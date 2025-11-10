package com.warehousedata.warehousedata.mapper;

import com.warehousedata.warehousedata.dto.DealRequestDto;
import com.warehousedata.warehousedata.dto.DealResponseDto;
import com.warehousedata.warehousedata.entity.Deal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DealMapper {
    DealResponseDto toResDto(Deal deal);

    Deal toEntity(DealRequestDto dealRequestDto);
}
