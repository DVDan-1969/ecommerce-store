package org.example.ecomercestore.controller;



import org.example.ecomercestore.exception.OrderNotFoundException;
import org.example.ecomercestore.service.OrderService;
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

@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Test
    void shouldReturnOrderNotExist()throws Exception{
        Long id=999L;

        when(orderService.getOrderById(id))
                .thenThrow(new OrderNotFoundException("Order not found"));
        mockMvc.perform(get("/orders/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.order").value("Order not found"));

    }


}
