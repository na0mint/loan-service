package com.fintech.loanservice.service.impl;

import com.fintech.loanservice.constants.OrderStatus;
import com.fintech.loanservice.exception.order.LoanConsiderationException;
import com.fintech.loanservice.exception.order.LoanIsApprovedException;
import com.fintech.loanservice.exception.order.OrderNotFoundException;
import com.fintech.loanservice.exception.order.TryLaterException;
import com.fintech.loanservice.exception.tariff.TariffNotFoundException;
import com.fintech.loanservice.model.Order;
import com.fintech.loanservice.repository.OrderRepository;
import com.fintech.loanservice.repository.TariffRepository;
import com.fintech.loanservice.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;
    TariffRepository tariffRepository;

    @Override
    public Iterable<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order save(Order newOrder) {
        Iterable<Order> orders = orderRepository.findAllByUserId(newOrder.getUserId());

        Set<Long> tariffIds = new HashSet<>();
        tariffRepository.findAll().forEach(tariff -> tariffIds.add(tariff.getId()));

        if(!tariffIds.contains(newOrder.getTariffId())) {
            throw new TariffNotFoundException();
        }

        for(Order order : orders) {
            if(order.getTariffId() == newOrder.getTariffId()
                    && order.getStatus() == OrderStatus.IN_PROGRESS) {

                throw new LoanConsiderationException();
            }

            if(order.getTariffId() == newOrder.getTariffId()
                    && order.getStatus() == OrderStatus.APPROVED) {

                throw new LoanIsApprovedException();
            }

            if((order.getTariffId() == newOrder.getTariffId())
                    && (order.getStatus() == OrderStatus.REFUSED)
                    && TimeUnit.MILLISECONDS.toMinutes
                    (new Date().getTime() - order.getTimeUpdate().getTime()) < 2) {

                throw new TryLaterException();
            }
        }

        newOrder.setOrderId(UUID.randomUUID());
        newOrder.setCreditRating(new Random().nextDouble(0.10, 0.90));
        newOrder.setStatus(OrderStatus.IN_PROGRESS);

        return orderRepository.save(newOrder);
    }

    @Override
    public Order update(Order order) {
        return orderRepository.update(order.getId(), order).orElseThrow(
                ()->new RuntimeException("Не удалось обновить заказ"));
    }

    @Override
    public OrderStatus getStatus(UUID id) {
        return orderRepository.findByOrderId(id).orElseThrow(
                OrderNotFoundException::new).getStatus();
    }
}
