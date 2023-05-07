package com.fintech.loanservice.service.impl;

import com.fintech.loanservice.model.Tariff;
import com.fintech.loanservice.repository.TariffRepository;
import com.fintech.loanservice.service.TariffService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TariffServiceImpl implements TariffService{

    TariffRepository tariffRepository;

    @Override
    public Iterable<Tariff> findAll() {
        log.info("Fetching all tariffs");

        return tariffRepository.findAll();
    }

    @Override
    public Tariff save(Tariff tariff) {
        log.info("Saving tariff into db: {}", tariff);

        return tariffRepository.save(tariff);
    }

    @Override
    public Tariff update(Tariff tariff) {
        log.info("Updating tariff with id={}", tariff.getId());

        return tariffRepository.update(tariff.getId(), tariff).orElseThrow(()->new RuntimeException("Не удалось обновить тариф"));
    }
}
