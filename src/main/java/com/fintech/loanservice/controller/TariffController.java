package com.fintech.loanservice.controller;

import com.fintech.loanservice.service.TariffService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/loan-service")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TariffController {
    TariffService tariffService;

    @GetMapping("/getTariffs")
    public ResponseEntity<?> findAll() {
        log.info("Returning all tariffs");

        return ResponseEntity.ok(tariffService.findAll());
    }
}
