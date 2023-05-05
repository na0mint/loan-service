package com.fintech.loanservice.repository;

import com.fintech.loanservice.constants.Code;
import com.fintech.loanservice.constants.TariffType;
import com.fintech.loanservice.exception.tariff.TariffNotFoundException;
import com.fintech.loanservice.model.Tariff;
import com.fintech.loanservice.repository.impl.TariffRepositoryImpl;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase
@TestPropertySource(properties = "spring.liquibase.change-log=classpath:db/changelog/0.0.1/init-changelog.yaml")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TariffRepositoryTest {

    final JdbcTemplate jdbcTemplate;
    final TariffRepository tariffRepository;
    final RowMapper<Tariff> tariffRowMapper;
    Tariff tariff;

    @Autowired
    public TariffRepositoryTest(JdbcTemplate jdbcTemplate, RowMapper<Tariff> tariffRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.tariffRowMapper = tariffRowMapper;

        tariffRepository = new TariffRepositoryImpl(jdbcTemplate, tariffRowMapper);
    }

    @BeforeEach
    void init() {
        tariff = new Tariff();
        tariff.setId(5L);
        tariff.setType(TariffType.BUSINESS);
        tariff.setInterestRate("19%");
    }

    @AfterEach
    void tearDown() {
        tariffRepository.delete(tariff.getId());
    }

    @Test
    public void findAllTest() {
        List<Tariff> tariffs = (List<Tariff>) tariffRepository.findAll();

        assertEquals(4, tariffs.size());
    }

    @Test
    public void findAllByTypeTest() {
        List<Tariff> tariffs = (List<Tariff>) tariffRepository.findAllByType(TariffType.MORTGAGE);

        assertEquals(1, tariffs.size());
    }

    @Test
    public void saveTest() {
        tariffRepository.save(tariff);

        List<Tariff> tariffs = (List<Tariff>) tariffRepository.findAll();

        assertEquals(5, tariffs.size());
        assertEquals(tariff.getType(), tariffs.get(4).getType());
        assertEquals(tariff.getInterestRate(), tariffs.get(4).getInterestRate());
    }

    @Test
    public void updateTest() {
        Optional<Tariff> tariffToBeUpdated = tariffRepository.update(1, tariff);

        Tariff updatedTariff = tariffRepository.findById(1).orElseThrow(
                TariffNotFoundException::new);

        assertEquals(updatedTariff.getType(), tariffToBeUpdated.get().getType());
        assertEquals(updatedTariff.getInterestRate(), tariffToBeUpdated.get().getInterestRate());
    }

    @Test
    public void deleteTest() {
        tariffRepository.delete(2);

        TariffNotFoundException exception = assertThrows(
                TariffNotFoundException.class,
                () -> tariffRepository.findById(2).orElseThrow(TariffNotFoundException::new)
        );

        assertEquals(exception.getError().getCode(), Code.TARIFF_NOT_FOUND);
    }
}
