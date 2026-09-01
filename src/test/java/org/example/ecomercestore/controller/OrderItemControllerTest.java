package org.example.ecomercestore.controller;

import org.example.ecomercestore.exception.OrderItemNotFoundException;
import org.example.ecomercestore.exception.OrderNotFoundException;
import org.example.ecomercestore.service.OrderItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderItemController.class)
@AutoConfigureMockMvc(addFilters = false)
public class OrderItemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderItemService orderItemService;

    @Test
    void shouldReturnOrderItemNotFound()throws Exception{
        Long id=999L;

        when(orderItemService.getOrderItemById(id))
                .thenThrow(new OrderItemNotFoundException("Order item not found"));
        mockMvc.perform(get("/orderItems/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.orderItem").value("Order item not found"));
    }
}
