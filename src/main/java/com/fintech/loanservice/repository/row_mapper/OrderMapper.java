package com.fintech.loanservice.repository.row_mapper;

import com.fintech.loanservice.constants.OrderStatus;
import com.fintech.loanservice.model.Order;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class OrderMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order();

        order.setId(rs.getLong("id"));
        order.setOrderId(UUID.fromString(rs.getString("order_id")));
        order.setUserId(rs.getLong("user_id"));
        order.setTariffId(rs.getLong("tariff_id"));
        order.setCreditRating(rs.getDouble("credit_rating"));
        order.setStatus(OrderStatus.valueOf(rs.getString("status")));
        order.setTimeInsert(rs.getDate("time_insert"));
        order.setTimeUpdate(rs.getDate("time_update"));

        return order;
    }
}
