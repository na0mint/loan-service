package com.fintech.loanservice.repository.impl;

import com.fintech.loanservice.constants.OrderStatus;
import com.fintech.loanservice.model.Order;
import com.fintech.loanservice.repository.OrderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderRepositoryImpl implements OrderRepository {

    static String FIND_ALL = "SELECT * FROM loan_order";
    static String FIND_ALL_BY_STATUS = "SELECT * FROM loan_order WHERE status=?";
    static String FIND_ALL_BY_USERID = "SELECT * FROM loan_order WHERE user_id=?";
    static String FIND_BY_ORDERID = "SELECT * FROM loan_order WHERE order_id=?";
    static String INSERT = "INSERT INTO loan_order(order_id, user_id, tariff_id," +
            " credit_rating, status,time_insert, time_update)" +
            " VALUES (?, ?, ?, ?, ?, current_timestamp, current_timestamp)";
    static String UPDATE = "UPDATE loan_order SET order_id=?, user_id=?," +
            " tariff_id=?, credit_rating=?, status=?, time_update=current_timestamp" +
            " WHERE id=?";
    static String DELETE = "DELETE FROM loan_order WHERE user_id=? AND order_id=?";

    JdbcTemplate jdbcTemplate;
    RowMapper<Order> orderRowMapper;

    @Override
    public Iterable<Order> findAll() {

        return jdbcTemplate.query(FIND_ALL, orderRowMapper);
    }

    @Override
    public Iterable<Order> findAllByUserId(long id) {
        return jdbcTemplate.query(FIND_ALL_BY_USERID, orderRowMapper, id);
    }

    @Override
    public Order save(Order order) {

        jdbcTemplate.update(INSERT, order.getOrderId(),
                order.getUserId(), order.getTariffId(),
                order.getCreditRating(), order.getStatus().toString());

        return order;
    }

    @Override
    public Optional<Order> update(long id, Order order) {

        try {
            jdbcTemplate.update(UPDATE, order.getOrderId(),
                    order.getUserId(), order.getTariffId(),
                    order.getCreditRating(), order.getStatus().toString(), id);
        } catch (Exception e) {
            return Optional.empty();
        }

        return Optional.of(order);
    }

    @Override
    public void delete(long userId, UUID orderId) {

        jdbcTemplate.update(DELETE, userId, orderId.toString());
    }

    @Override
    public Optional<Order> findByOrderId(UUID orderId) {
        Order order;

        try {
            order = (Order) jdbcTemplate.query(FIND_BY_ORDERID, orderRowMapper, orderId);
        } catch (Exception e) {
            return Optional.empty();
        }

        return Optional.of(order);
    }

    @Override
    public Iterable<Order> findAllByStatus(OrderStatus status) {
        return jdbcTemplate.query(FIND_ALL_BY_STATUS, orderRowMapper, status.toString());
    }

    public int saveAll(List<Order> orders) {
        jdbcTemplate.batchUpdate(UPDATE, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, orders.get(i).getOrderId().toString());
                ps.setLong(2, orders.get(i).getUserId());
                ps.setLong(3, orders.get(i).getTariffId());
                ps.setDouble(4, orders.get(i).getCreditRating());
                ps.setString(5, orders.get(i).getStatus().toString());
                ps.setLong(6, orders.get(i).getId());
            }

            @Override
            public int getBatchSize() {
                return orders.size();
            }
        });

        return orders.size();
    }
}
