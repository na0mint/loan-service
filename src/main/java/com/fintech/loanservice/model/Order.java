package com.fintech.loanservice.model;

import com.fintech.loanservice.constants.OrderStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
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
}
