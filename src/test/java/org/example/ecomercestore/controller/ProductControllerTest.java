package org.example.ecomercestore.controller;

import org.example.ecomercestore.dto.ProductResponseDTO;
import org.example.ecomercestore.exception.ProductNotFoundException;
import org.example.ecomercestore.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.when;


@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ProductService productService;

    @Test
    void shouldReturnProductExists() throws Exception {
        Long id=1L;

        ProductResponseDTO product =new ProductResponseDTO();
        product.setId(id);
        product.setProductName("Laptop Samsung");

        when(productService.getProductById(id))
                .thenReturn(product);

        mockMvc.perform(get("/api/products/{id}",id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.productName").value("Laptop Samsung"));

    }
    @Test
    void shouldReturnProductNotFound() throws Exception {
        Long id=999L;



        when(productService.getProductById(id))
                .thenThrow(new ProductNotFoundException("Product Not Found"));
        mockMvc.perform(get("/api/products/{id}",id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.product").value("Product Not Found"));

    }
}
