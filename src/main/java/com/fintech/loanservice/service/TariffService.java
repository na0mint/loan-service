package com.fintech.loanservice.service;

import com.fintech.loanservice.model.Tariff;

import java.util.Optional;

public interface TariffService {
    Iterable<Tariff> findAll();
    Optional<Tariff> save(Tariff tariff);
    Optional<Tariff> update(Tariff tariff);
}
