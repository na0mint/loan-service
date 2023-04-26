package com.fintech.loanservice.repository.impl;

import com.fintech.loanservice.constants.TariffType;
import com.fintech.loanservice.model.Tariff;
import com.fintech.loanservice.repository.TariffRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TariffRepositoryImpl implements TariffRepository {

    static String FIND_ALL = "SELECT * FROM tariff";
    static String INSERT = "INSERT INTO tariff(type, interest_rate) VALUES(?, ?)";
    static String UPDATE = "UPDATE tariff SET type=?, interest_rate=? WHERE id=?";
    static String FINDBYTYPE = "SELECT * FROM tariff WHERE type=?";
    static String DELETE = "DELETE FROM tariff WHERE id=?";

    JdbcTemplate jdbcTemplate;
    RowMapper<Tariff> tariffRowMapper;

    @Override
    public Iterable<Tariff> findAll() {
        return jdbcTemplate.query(FIND_ALL, tariffRowMapper);
    }

    @Override
    public Iterable<Tariff> findAllByType(TariffType type) {

        return jdbcTemplate.query(FINDBYTYPE, tariffRowMapper,
                type.toString());
    }

    @Override
    public Tariff save(Tariff tariff) {

        jdbcTemplate.update(INSERT,
                tariff.getType().toString(), tariff.getInterestRate());

        return tariff;
    }

    @Override
    public Optional<Tariff> update(long id, Tariff tariff) {
        try {
            jdbcTemplate.update(UPDATE,
                    tariff.getType().toString(), tariff.getInterestRate(), id);
        } catch (Exception e) {
            return Optional.empty();
        }

        return Optional.of(tariff);
    }

    @Override
    public void delete(long id) {

        jdbcTemplate.update(DELETE, id);
    }
}
