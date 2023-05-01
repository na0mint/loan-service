package com.fintech.loanservice.dto.mapper;

import com.fintech.loanservice.dto.OrderDto;
import com.fintech.loanservice.model.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto mapToOrderDto(Order order);
    Order mapToOrder(OrderDto orderDto);

    List<OrderDto> mapToOrderDtoList(List<Order> orderList);
    List<Order> mapToOrderList(List<OrderDto> orderDtoList);
}
