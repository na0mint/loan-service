package com.fintech.loanservice.repository;

import com.fintech.loanservice.constants.TariffType;
import com.fintech.loanservice.model.Tariff;

public interface TariffRepository {
    Iterable<Tariff> findAll();

    Iterable<Tariff> findAllByType(TariffType type);

    Tariff save(Tariff tariff);

    Tariff update(long id, Tariff tariff);

    void delete(long id);
}
