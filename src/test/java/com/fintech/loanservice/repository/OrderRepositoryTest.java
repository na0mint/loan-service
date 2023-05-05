package com.fintech.loanservice.repository;

import com.fintech.loanservice.constants.Code;
import com.fintech.loanservice.constants.OrderStatus;
import com.fintech.loanservice.exception.order.OrderNotFoundException;
import com.fintech.loanservice.exception.tariff.TariffNotFoundException;
import com.fintech.loanservice.model.Order;
import com.fintech.loanservice.repository.impl.OrderRepositoryImpl;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase
@TestPropertySource(properties = "spring.liquibase.change-log=classpath:db/changelog/0.0.1/init-changelog.yaml")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRepositoryTest {

    final JdbcTemplate jdbcTemplate;
    final OrderRepository orderRepository;
    final RowMapper<Order> orderRowMapper;
    Order order;

    @Autowired
    public OrderRepositoryTest(JdbcTemplate jdbcTemplate, RowMapper<Order> orderRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.orderRowMapper = orderRowMapper;

        orderRepository = new OrderRepositoryImpl(jdbcTemplate, orderRowMapper);
    }

    @BeforeEach
    void init() {
        order = new Order(0L, UUID.randomUUID(), 23,
                1, 0.45, OrderStatus.IN_PROGRESS);

        orderRepository.save(order);
    }

    @AfterEach
    void tearDown() {
        orderRepository.delete(order.getUserId(), order.getOrderId());
    }

    @Test
    public void findAllTest() {
        List<Order> orders = (List<Order>) orderRepository.findAll();

        assertEquals(1, orders.size());
    }

    @Test
    public void saveTest() {
        Order createdOrder = orderRepository.findByOrderId(order.getOrderId())
                .orElseThrow(OrderNotFoundException::new);

        assertEquals(order.getOrderId(), createdOrder.getOrderId());
        assertEquals(order.getUserId(), createdOrder.getUserId());
        assertEquals(order.getTariffId(), createdOrder.getTariffId());
        assertEquals(order.getCreditRating(), createdOrder.getCreditRating());
        assertEquals(order.getStatus(), createdOrder.getStatus());
    }

    @Test
    public void findAllByUserIdTest() {
        List<Order> orders = (List<Order>) orderRepository.findAllByUserId(order.getUserId());

        assertEquals(1, orders.size());
        assertEquals(orders.get(0).getOrderId(), order.getOrderId());
        assertEquals(orders.get(0).getUserId(), order.getUserId());
        assertEquals(orders.get(0).getTariffId(), order.getTariffId());
        assertEquals(orders.get(0).getCreditRating(), order.getCreditRating());
        assertEquals(orders.get(0).getStatus(), order.getStatus());
    }

    @Test
    public void updateTest() {
        order.setStatus(OrderStatus.APPROVED);

        Order updatedOrder = orderRepository.update(order.getId(), order)
                .orElseThrow(OrderNotFoundException::new);

        assertEquals(order.getOrderId(), updatedOrder.getOrderId());
        assertEquals(order.getUserId(), updatedOrder.getUserId());
        assertEquals(order.getTariffId(), updatedOrder.getTariffId());
        assertEquals(order.getCreditRating(), updatedOrder.getCreditRating());
        assertEquals(order.getStatus(), updatedOrder.getStatus());
    }

    @Test
    public void deleteTest() {
        orderRepository.delete(order.getUserId(), order.getOrderId());

        TariffNotFoundException exception = assertThrows(
                TariffNotFoundException.class,
                () -> orderRepository.findByOrderId(order.getOrderId())
                        .orElseThrow(TariffNotFoundException::new)
        );

        assertEquals(exception.getError().getCode(), Code.TARIFF_NOT_FOUND);
    }

    @Test
    public void findByOrderIdTest() {
        Order foundedOrder = orderRepository.findByOrderId(order.getOrderId())
                .orElseThrow(OrderNotFoundException::new);

        assertEquals(order.getOrderId(), foundedOrder.getOrderId());
        assertEquals(order.getUserId(), foundedOrder.getUserId());
        assertEquals(order.getTariffId(), foundedOrder.getTariffId());
        assertEquals(order.getCreditRating(), foundedOrder.getCreditRating());
        assertEquals(order.getStatus(), foundedOrder.getStatus());
    }

    @Test
    public void findAllByStatusTest() {
        List<Order> orders = (List<Order>) orderRepository.findAllByStatus(order.getStatus());

        assertEquals(1, orders.size());
        assertEquals(orders.get(0).getOrderId(), order.getOrderId());
        assertEquals(orders.get(0).getUserId(), order.getUserId());
        assertEquals(orders.get(0).getTariffId(), order.getTariffId());
        assertEquals(orders.get(0).getCreditRating(), order.getCreditRating());
        assertEquals(orders.get(0).getStatus(), order.getStatus());
    }
}
