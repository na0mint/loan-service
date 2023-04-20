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

    long id;

    @NotNull(message = "Order id is required")
    UUID orderId;

    @NotNull(message = "User id is required")
    long userId;

    @NotNull(message = "Tariff id is required")
    long tariffId;

    @NotNull(message = "Credit rating is required")
    @Max(value = 1, message = "Credit rating should be lower than 1")
    @Min(value = 0, message = "Credit rating should be greater than 0")
    double creditRating;

    @NotNull(message = "Status is required")
    OrderStatus status;

    @NotNull(message = "Time of insertion is required")
    Date timeInsert;

    @NotNull(message = "Update time is required")
    Date timeUpdate;
}
