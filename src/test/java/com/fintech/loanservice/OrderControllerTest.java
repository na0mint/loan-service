package com.fintech.loanservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.loanservice.constants.OrderStatus;
import com.fintech.loanservice.controller.OrderController;
import com.fintech.loanservice.dto.mapper.OrderMapper;
import com.fintech.loanservice.model.Order;
import com.fintech.loanservice.repository.OrderRepository;
import com.fintech.loanservice.service.OrderService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    OrderService orderService;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    OrderRepository orderRepository;
    @MockBean
    OrderMapper orderMapper;
    @Autowired
    WebApplicationContext webApplicationContext;

    Order testOrder;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();

        testOrder = new Order(1230L, UUID.fromString(
                "ef4f5ad2-8147-46cb-8389-c2b8c3ef6b10"), 213,
                3, 0.45, OrderStatus.APPROVED);

        orderRepository.save(testOrder);
    }

    @AfterEach
    void end() {
        orderRepository.delete(213, UUID.fromString(
                "ef4f5ad2-8147-46cb-8389-c2b8c3ef6b10"));
    }

    @WithMockUser()
    @Test
    public void getStatusTest() throws Exception {

        when(orderService.getStatus(testOrder.getOrderId())).thenReturn(testOrder.getStatus());

        ResultActions response = mockMvc.perform(get("/loan-service/getStatusOrder")
                .param("orderId",
                        testOrder.getOrderId().toString()));

        response.andExpect(status().isOk())
                .andDo(mvcResult -> response.andDo(print())
                        .andExpect(jsonPath("$.orderStatus", is("APPROVED"))));
    }

    @WithMockUser()
    @Test
    public void deleteTest() throws Exception {
        testOrder.setStatus(OrderStatus.IN_PROGRESS);
        orderRepository.update(testOrder.getId(), testOrder);

        mockMvc.perform(delete("/loan-service/deleteOrder").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testOrder)))
                .andExpect(status().isOk());
    }

    @WithMockUser(value = "admin")
    @Test
    public void getOrdersTest() throws Exception {
        mockMvc.perform(get("/loan-service/order"))
                .andDo(print())
                .andExpect(status().isOk());
    }

//    @WithMockUser()
//    @Test
//    public void orderNotfoundTest() throws Exception {
//        //when(orderService.getStatus(testOrder.getOrderId())).thenReturn(testOrder.getStatus());
//
//        ResultActions response = mockMvc.perform(get("/loan-service/getStatusOrder")
//                .param("orderId",
//                        UUID.randomUUID().toString()));
//
//        response.andExpect(status().isOk())
//                .andDo(mvcResult -> response.andDo(print())
//                        .andExpect(jsonPath("$.code", is("ORDER_NOT_FOUND"))));
//    }

//    @WithMockUser()
//    @Test
//    public void loanConsiderationTest() throws Exception {
//        testOrder.setStatus(OrderStatus.IN_PROGRESS);
//        orderRepository.update(testOrder.getId(), testOrder);
//
//    }

//    @WithMockUser
//    @Test
//    public void createOrderTest() throws Exception {
////        Order newOrder = new Order(125230L, UUID.fromString(
////                "ef6f5ad2-8156-46cb-8389-c2b8c3ef6b10"), 21343,
////                2, 0.65, OrderStatus.REFUSED);
//
//        OrderDto orderDto = new OrderDto();
//        orderDto.setTariffId(2);
//        orderDto.setUserId(21346788);
//
//        Order newOrder = orderMapper.mapToOrder(orderDto);
//
//
//
//       //when(orderService.save(newOrder)).thenReturn(newOrder);
//
//        mockMvc.perform(post("/loan-service/order")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(orderDto))
//                        .with(csrf()))
//                .andDo(print())
//                .andExpect(jsonPath("$.orderId",
//                        is(notNullValue())));
//    }

}
