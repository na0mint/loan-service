package com.fintech.loanservice.repository.impl;

import com.fintech.loanservice.model.Order;
import com.fintech.loanservice.repository.OrderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderRepositoryImpl implements OrderRepository {

    static String FINDALL = "SELECT * FROM loan_order";
    static String INSERT = "INSERT INTO loan_order(order_id, user_id, tariff_id," +
            " credit_rating, status, time_update)" +
            " VALUES (?, ?, ?, ?, ?, current_timestamp)";
    static String UPDATE = "UPDATE loan_order SET order_id=?, user_id=?," +
            " tariff_id=?, credit_rating=?, status=?, time_update=current_timestamp" +
            " WHERE id=?";
    static String DELETE = "DELETE FROM loan_order WHERE user_id=? AND order_id=?";

    JdbcTemplate jdbcTemplate;
    RowMapper<Order> orderRowMapper;

    @Override
    public Iterable<Order> findAll() {

        return jdbcTemplate.query(FINDALL, orderRowMapper);
    }

    @Override
    public Order save(Order order) {

        jdbcTemplate.update(INSERT, order.getOrderId(),
                order.getUserId(), order.getTariffId(),
                order.getCreditRating(), order.getStatus().toString());

        return order;
    }

    @Override
    public Order update(long id, Order order) {

        jdbcTemplate.update(UPDATE, order.getOrderId(),
                order.getUserId(), order.getTariffId(),
                order.getCreditRating(), order.getStatus().toString(), id);

        return order;
    }

    @Override
    public void delete(long userId, UUID orderId) {

        jdbcTemplate.update(DELETE, userId, orderId.toString());
    }
}
