package com.warehousedata.warehousedata.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(InvalidCurrencyFormat.class)
    public ResponseEntity<String> handleInvalidCurrencyFormat(InvalidCurrencyFormat invalidCurrencyFormat) {
        return new ResponseEntity<>(invalidCurrencyFormat.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
