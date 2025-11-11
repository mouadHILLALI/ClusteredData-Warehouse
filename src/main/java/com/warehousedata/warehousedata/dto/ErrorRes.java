package com.warehousedata.warehousedata.dto;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorRes(
        String message,
        HttpStatus httpStatus,
        LocalDateTime timeStamp
) {
}
