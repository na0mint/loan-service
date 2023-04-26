package com.fintech.loanservice.repository;

import com.fintech.loanservice.constants.OrderStatus;
import com.fintech.loanservice.model.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    Iterable<Order> findAll();

    Iterable<Order> findAllByUserId(long id);

    Order save(Order order);

    Optional<Order> update(long id, Order order);

    void delete(long userId, UUID orderId);

    Optional<Order> findByOrderId(UUID orderId);
    Iterable<Order> findAllByStatus(OrderStatus status);
    int saveAll(List<Order> orders);
}
