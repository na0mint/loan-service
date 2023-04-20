package com.fintech.loanservice.repository.row_mapper;

import com.fintech.loanservice.constants.TariffType;
import com.fintech.loanservice.model.Tariff;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TariffMapper implements RowMapper<Tariff> {
    @Override
    public Tariff mapRow(ResultSet rs, int rowNum) throws SQLException {
        Tariff tariff = new Tariff();

        tariff.setId(rs.getLong("id"));
        tariff.setType(TariffType.valueOf(rs.getString("type")));
        tariff.setInterestRate(rs.getString("interest_rate"));

        return tariff;
    }
}
