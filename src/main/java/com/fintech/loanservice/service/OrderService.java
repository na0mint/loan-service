package com.fintech.loanservice.service;

import com.fintech.loanservice.constants.OrderStatus;
import com.fintech.loanservice.model.Order;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface OrderService {
    Iterable<Order> findAll();
    Order save(Order order);
    Order update(Order order);
    OrderStatus getStatus(UUID id);
    void delete(Order order);
}
