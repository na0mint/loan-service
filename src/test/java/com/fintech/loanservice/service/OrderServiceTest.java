package com.fintech.loanservice.service;

import com.fintech.loanservice.constants.Code;
import com.fintech.loanservice.constants.OrderStatus;
import com.fintech.loanservice.constants.TariffType;
import com.fintech.loanservice.exception.order.LoanConsiderationException;
import com.fintech.loanservice.exception.order.LoanIsApprovedException;
import com.fintech.loanservice.exception.order.OrderImpossibleToDeleteException;
import com.fintech.loanservice.exception.order.OrderNotFoundException;
import com.fintech.loanservice.exception.tariff.TariffNotFoundException;
import com.fintech.loanservice.model.Order;
import com.fintech.loanservice.model.Tariff;
import com.fintech.loanservice.repository.OrderRepository;
import com.fintech.loanservice.repository.TariffRepository;
import com.fintech.loanservice.service.impl.OrderServiceImpl;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderServiceTest {
    @Mock
    OrderRepository orderRepository;
    @Mock
    TariffRepository tariffRepository;
    @InjectMocks
    OrderServiceImpl orderService;
    Tariff tariff;
    List<Tariff> tariffs;
    Order order;

    @BeforeEach
    void init() {
        tariff = new Tariff();
        tariff.setType(TariffType.BUSINESS);
        tariff.setInterestRate("7%");
        tariffRepository.save(tariff);

        tariffs = new ArrayList<>();
        tariffs.add(tariff);

        order = new Order();
        order.setId(1L);
        order.setTariffId(0);
        order.setUserId(2134);
    }

    @AfterEach
    void tearDown() {
        tariffRepository.delete(5L);
        tariffs.clear();
    }

    @Test
    public void saveOrderTest() {

        when(orderRepository.save(order)).thenReturn(order);
        when(tariffRepository.findAll()).thenReturn(tariffs);

        Order result = orderService.save(order);

        assertEquals(result, order);
    }

    @Test
    public void tariffNotFoundTest() {
        order.setTariffId(8L);

        when(tariffRepository.findAll()).thenReturn(tariffs);

        TariffNotFoundException exception = assertThrows(
                TariffNotFoundException.class,
                () -> orderService.save(order)
        );

        assertEquals(exception.getError().getCode(), Code.TARIFF_NOT_FOUND);
    }

    @Test
    public void loanIsApprovedTest() {
        order.setStatus(OrderStatus.APPROVED);

        when(orderRepository.findAllByUserId(order.getUserId()))
                .thenReturn(Collections.singletonList(order));
        when(tariffRepository.findAll()).thenReturn(tariffs);

        LoanIsApprovedException exception = assertThrows(
                LoanIsApprovedException.class,
                () -> orderService.save(order)
        );

        assertEquals(exception.getError().getCode(), Code.LOAN_ALREADY_APPROVED);
    }

    @Test
    public void loanConsiderationTest() {
        order.setStatus(OrderStatus.IN_PROGRESS);

        when(orderRepository.findAllByUserId(order.getUserId()))
                .thenReturn(Collections.singletonList(order));
        when(tariffRepository.findAll()).thenReturn(tariffs);

        LoanConsiderationException exception = assertThrows(
                LoanConsiderationException.class,
                () -> orderService.save(order)
        );

        assertEquals(exception.getError().getCode(), Code.LOAN_CONSIDERATION);
    }

    @Test
    public void orderImpossibleToDeleteTest() {
        order.setStatus(OrderStatus.APPROVED);

        when(orderRepository.findByOrderId(order.getOrderId())).thenReturn(Optional.of(order));

        OrderImpossibleToDeleteException exception = assertThrows(
                OrderImpossibleToDeleteException.class,
                () -> orderService.delete(order)
        );

        assertEquals(exception.getError().getCode(), Code.ORDER_IMPOSSIBLE_TO_DELETE);
    }

    @Test
    public void orderNotfoundTest() {
        Order orderToDelete = new Order();
        orderToDelete.setUserId(1);

        when(orderRepository.findByOrderId(order.getOrderId())).thenReturn(Optional.of(order));

        OrderNotFoundException exception = assertThrows(
                OrderNotFoundException.class,
                () -> orderService.delete(orderToDelete)
        );

        assertEquals(exception.getError().getCode(), Code.ORDER_NOT_FOUND);
    }
}
