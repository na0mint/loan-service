package com.fintech.loanservice.service.impl;

import com.fintech.loanservice.constants.OrderStatus;
import com.fintech.loanservice.exception.order.*;
import com.fintech.loanservice.exception.tariff.TariffNotFoundException;
import com.fintech.loanservice.model.Order;
import com.fintech.loanservice.repository.OrderRepository;
import com.fintech.loanservice.repository.TariffRepository;
import com.fintech.loanservice.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
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
        newOrder.setCreditRating((double)((int)(Math.random()*80) + 10)/100);
        newOrder.setStatus(OrderStatus.IN_PROGRESS);

        return orderRepository.save(newOrder);
    }

    @Override
    public Order update(Order order) {
        return orderRepository.update(order.getId(), order).orElseThrow(
                OrderNotFoundException::new);
    }

    @Override
    public OrderStatus getStatus(UUID id) {
        return orderRepository.findByOrderId(id).orElseThrow(
                OrderNotFoundException::new).getStatus();
    }

    @Override
    public void delete(Order orderToDelete) {
        Order order = orderRepository.findByOrderId(orderToDelete.getOrderId())
                .orElseThrow(OrderNotFoundException::new);

        if(orderToDelete.getUserId() != order.getUserId()) {
            throw new OrderNotFoundException();
        }

        if(order.getStatus() == OrderStatus.IN_PROGRESS) {
            orderRepository.delete(orderToDelete.getUserId(), orderToDelete.getOrderId());
        } else {
            throw new OrderImpossibleToDeleteException();
        }
    }

    @Scheduled(fixedDelay = 120_000)
    public void orderConsideration() {
        List<Order> orders = (List<Order>) orderRepository.findAllByStatus(OrderStatus.IN_PROGRESS);

        for(Order order : orders) {
            if(Math.random() > 0.5) {
                order.setStatus(OrderStatus.APPROVED);
            } else {
                order.setStatus(OrderStatus.REFUSED);
            }
        }

        orderRepository.saveAll(orders);
    }
}
