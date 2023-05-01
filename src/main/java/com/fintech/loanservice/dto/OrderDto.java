package com.fintech.loanservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDto {

    @NotNull(message = "User id is required")
    long userId;

    @NotNull(message = "Tariff id is required")
    long tariffId;
}
