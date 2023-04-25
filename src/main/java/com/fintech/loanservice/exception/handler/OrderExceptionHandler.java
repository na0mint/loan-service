package com.fintech.loanservice.exception.handler;

import com.fintech.loanservice.exception.order.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class OrderExceptionHandler {

    @ExceptionHandler(LoanConsiderationException.class)
    public ResponseEntity<?> loanConsideration(LoanConsiderationException e) {

        log.error("Loan consideration exception: {}", e.toString());
        return new ResponseEntity<>(e.getError(), HttpStatus.I_AM_A_TEAPOT);
    }

    @ExceptionHandler(LoanIsApprovedException.class)
    public ResponseEntity<?> loanIsApproved(LoanIsApprovedException e) {

        log.error("Loan id approved exception: {}", e.toString());
        return new ResponseEntity<>(e.getError(), HttpStatus.I_AM_A_TEAPOT);
    }

    @ExceptionHandler(OrderImpossibleToDeleteException.class)
    public ResponseEntity<?> orderDeletion(OrderImpossibleToDeleteException e) {

        log.error("Order deletion exception: {}", e.toString());
        return new ResponseEntity<>(e.getError(), HttpStatus.I_AM_A_TEAPOT);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<?> notFound(OrderNotFoundException e) {

        log.error("Order not found exception: {}", e.toString());
        return new ResponseEntity<>(e.getError(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TryLaterException.class)
    public ResponseEntity<?> tryLater(TryLaterException e) {

        log.error("Try later exception: {}", e.toString());
        return new ResponseEntity<>(e.getError(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
