package com.fintech.loanservice.service;

import com.fintech.loanservice.constants.OrderStatus;
import com.fintech.loanservice.model.Order;

import java.util.UUID;

public interface OrderService {
    Iterable<Order> findAll();
    Order save(Order order);
    Order update(Order order);
    OrderStatus getStatus(UUID id);
    void delete(Order order);
}
