package com.warehousedata.warehousedata.exception;

import com.warehousedata.warehousedata.dto.ErrorRes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final String INVALID_CURRENCY_MESSAGE = "Invalid currency code please verify the currency";

    @ExceptionHandler(InvalidCurrencyFormat.class)
    public ResponseEntity<ErrorRes> handleInvalidCurrencyFormat(InvalidCurrencyFormat invalidCurrencyFormat) {
        return new ResponseEntity<>(new ErrorRes(INVALID_CURRENCY_MESSAGE,HttpStatus.BAD_REQUEST,LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }
}
