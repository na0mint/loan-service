package com.fintech.loanservice.repository;

import com.fintech.loanservice.constants.TariffType;
import com.fintech.loanservice.model.Tariff;

import java.util.Optional;

public interface TariffRepository {
    Iterable<Tariff> findAll();

    Iterable<Tariff> findAllByType(TariffType type);

    Tariff save(Tariff tariff);

    Optional<Tariff> update(long id, Tariff tariff);

    void delete(long id);
}
