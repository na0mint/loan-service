package com.fintech.loanservice.exception.handler;

import com.fintech.loanservice.exception.tariff.TariffNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class TariffExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TariffNotFoundException.class)
    public ResponseEntity<?> notFound(TariffNotFoundException e) {

        log.error("Tariff not found error: {}", e.toString());
        return new ResponseEntity<>(e.getError(), HttpStatus.NOT_FOUND);
    }
}
