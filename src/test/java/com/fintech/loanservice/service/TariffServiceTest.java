package com.fintech.loanservice.service;

import com.fintech.loanservice.constants.TariffType;
import com.fintech.loanservice.model.Tariff;
import com.fintech.loanservice.repository.TariffRepository;
import com.fintech.loanservice.service.impl.TariffServiceImpl;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TariffServiceTest {

    @Mock
    TariffRepository tariffRepository;

    @InjectMocks
    TariffServiceImpl tariffService;
    Tariff tariff;

    @BeforeEach
    public void init() {
        tariff = new Tariff();
        tariff.setType(TariffType.BUSINESS);
        tariff.setInterestRate("7%");
        tariffRepository.save(tariff);
    }

    @Test
    public void saveTariffTest() {
        when(tariffRepository.save(tariff)).thenReturn(tariff);

        Tariff result = tariffService.save(tariff);

        assertEquals(result, tariff);
    }
}
