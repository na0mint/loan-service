package com.fintech.loanservice.model;

import com.fintech.loanservice.constants.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    Long id;

    UUID orderId;

    @NotNull(message = "User id is required")
    long userId;

    @NotNull(message = "Tariff id is required")
    long tariffId;

    double creditRating;

    OrderStatus status;

    Date timeInsert;

    Date timeUpdate;

    public Order(Long id, UUID orderId, long userId, long tariffId, double creditRating, OrderStatus status) {
        this.id = id;
        this.orderId = orderId;
        this.userId = userId;
        this.tariffId = tariffId;
        this.creditRating = creditRating;
        this.status = status;
    }
}
