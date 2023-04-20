package com.fintech.loanservice.repository;

import com.fintech.loanservice.model.Order;

import java.util.UUID;

public interface OrderRepository {
    Iterable<Order> findAll();

    Order save(Order order);

    Order update(long id, Order order);

    void delete(long userId, UUID orderId);
}
