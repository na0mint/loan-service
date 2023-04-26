package com.fintech.loanservice.controller;

import com.fintech.loanservice.model.Order;
import com.fintech.loanservice.service.OrderService;
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

    @PostMapping("/order")
    public ResponseEntity<?> saveOrder(@RequestBody Order newOrder) {
        log.info("New order is about to save: {}", newOrder.toString());

        return ResponseEntity.ok(orderService.save(newOrder).getOrderId());
    }

    @GetMapping("/order")
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @GetMapping("/getStatusOrder")
    public ResponseEntity<?> getOrderStatus(@RequestParam String orderId) {

        return ResponseEntity.ok(orderService.getStatus(UUID.fromString(orderId)));
    }

    @DeleteMapping("/deleteOrder")
    public ResponseEntity<?> delete(@RequestBody Order order) {
        orderService.delete(order);
        log.info("Order with id: {} was deleted", order.getOrderId());

        return ResponseEntity.ok().build();
    }
}
