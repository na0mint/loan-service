package com.fintech.loanservice.dto;

import com.fintech.loanservice.constants.OrderStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
 @RequiredArgsConstructor
public class OrderStatusDto {
    OrderStatus orderStatus;
}
