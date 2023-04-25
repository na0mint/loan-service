package com.fintech.loanservice.service.impl;

import com.fintech.loanservice.model.Tariff;
import com.fintech.loanservice.repository.TariffRepository;
import com.fintech.loanservice.service.TariffService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TariffServiceImpl implements TariffService{

    TariffRepository tariffRepository;

    @Override
    public Iterable<Tariff> findAll() {
        return tariffRepository.findAll();
    }

    @Override
    public Tariff save(Tariff tariff) {
        return tariffRepository.save(tariff);
    }

    @Override
    public Tariff update(Tariff tariff) {
        return tariffRepository.update(tariff.getId(), tariff).orElseThrow(()->new RuntimeException("Не удалось обновить тариф"));
    }
}
