package com.fintech.loanservice.controller;

import com.fintech.loanservice.dto.OrderDto;
import com.fintech.loanservice.dto.OrderStatusDto;
import com.fintech.loanservice.dto.mapper.OrderMapper;
import com.fintech.loanservice.model.Order;
import com.fintech.loanservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/loan-service")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {

    OrderService orderService;
    OrderMapper orderMapper;

    @PostMapping("/order")
    public ResponseEntity<?> saveOrder(@RequestBody OrderDto newOrder) {
        log.info("New order is about to save: {}", newOrder.toString());

        return ResponseEntity.ok(orderService.save(
                orderMapper.mapToOrder(newOrder)).getOrderId());
    }

    @GetMapping("/order")
    @CircuitBreaker(name = "loanService", fallbackMethod = "timeLimitFallback")
    public ResponseEntity<?> getAllOrders() {
        log.info("Returning all orders");

        return ResponseEntity.ok(orderService.findAll());
    }

    public ResponseEntity<?> timeLimitFallback(RequestNotPermitted t) throws InterruptedException {
        log.warn("Time limit fallback circuit breaker method");

        return ResponseEntity.ok("loanService is full");
    }

    @GetMapping("/getStatusOrder")
    public ResponseEntity<?> getOrderStatus(@RequestParam String orderId) {
        log.info("Returning order status by orderId");

        return ResponseEntity.ok(new OrderStatusDto(orderService.getStatus(UUID.fromString(orderId))));
    }

    @DeleteMapping("/deleteOrder")
    public ResponseEntity<?> delete(@RequestBody Order order) {
        orderService.delete(order);
        log.info("Order with id={} was deleted", order.getOrderId());

        return ResponseEntity.ok().build();
    }
}
