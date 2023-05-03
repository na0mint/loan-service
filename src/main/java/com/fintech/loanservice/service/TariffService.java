package com.fintech.loanservice.service;

import com.fintech.loanservice.model.Tariff;
import org.springframework.stereotype.Component;

@Component
public interface TariffService {
    Iterable<Tariff> findAll();

    Tariff save(Tariff tariff);

    Tariff update(Tariff tariff);
}
