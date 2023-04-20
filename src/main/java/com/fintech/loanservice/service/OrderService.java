package com.fintech.loanservice.service;

import com.fintech.loanservice.model.Order;

import java.util.Optional;

public interface OrderService {
    Iterable<Order> findAll();
    Optional<Order> save(Order order);
    Optional<Order> update(Order order);
}
