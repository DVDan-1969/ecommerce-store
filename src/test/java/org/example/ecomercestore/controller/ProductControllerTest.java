package org.example.ecomercestore.controller;

import org.example.ecomercestore.dto.ProductRequestDTO;
import org.example.ecomercestore.dto.ProductResponseDTO;
import org.example.ecomercestore.exception.ProductNotFoundException;
import org.example.ecomercestore.service.ProductService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ProductService productService;


    @Test
    void shouldReturnProducts() throws Exception {
        ProductResponseDTO product1 = new ProductResponseDTO();
        product1.setId(1L);
        product1.setProductName("Laptop Samsung");

        ProductResponseDTO product2 = new ProductResponseDTO();
        product2.setId(2L);
        product2.setProductName("iPhone 15");

        when(productService.getAllProducts())
                 .thenReturn(List.of(product1,product2));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productName").value("Laptop Samsung"))
                .andExpect(jsonPath("$[1].productName").value("iPhone 15"));


    }


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
        Long id = 999L;

        when(productService.getProductById(id))
                .thenThrow(new ProductNotFoundException("product not found"));

        mockMvc.perform(get("/api/products/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.product").value("product not found"));
    }




    @Test
    void shouldSaveProduct() throws Exception {

        ProductResponseDTO product =new ProductResponseDTO();
        product.setId(1L);
        product.setProductName("Laptop Dell");
        product.setProductImage("LaptopDell.jpg");
        product.setProductDescription("Laptop Dell");
        product.setProductQuantity(5);
        product.setProductPrice(new BigDecimal("2500.00"));

        String validProductJson= """                
                {
                "productName": "Laptop Dell",
                "productDescription": "LaptopDell.jpg",
                "productQuantity": 5,
                "productPrice": 2500.00,
                "productImage": "LaptopDell.jpg",
                "categoryId": "1" 
                }               
                """;
        when(productService.save(any(ProductRequestDTO.class)))
                .thenReturn(product);

        mockMvc.perform(post("/api/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(validProductJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.productName").value("Laptop Dell"));
    }



    @Test
    void shouldReturnBadRequestWhenProductNameIsBlank() throws Exception {


        String invalidProductJson= """                
                {
                "productName": "",
                "productDescription": "LaptopDell.jpg",
                "productQuantity": 5,
                "productPrice": 2500.00,
                "productImage": "LaptopDell.jpg",
                "categoryId": "1" 
                }               
                """;


        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidProductJson))
                .andExpect(status().isBadRequest())
                .andExpect((jsonPath("$.productName").value("must not be blank")));

    }


    @Test
    void shouldReturnProductSaved() throws Exception {
        ProductResponseDTO product =new ProductResponseDTO();

        product.setId(1L);
        product.setProductName("Laptop Samsung");
        product.setProductImage("LaptopDell.jpg");
        product.setProductDescription("Laptop Dell");
        product.setProductQuantity(5);
        product.setProductPrice(new BigDecimal("2500.00"));

        String validProductJson= """                
                {
                "productName": "Laptop Samsung",
                "productDescription": "LaptopDell.jpg",
                "productQuantity": 5,
                "productPrice": 2500.00,
                "productImage": "LaptopDell.jpg",
                "categoryId": "1" 
                }               
                """;

        when(productService.update(eq(1L),any(ProductRequestDTO.class)))
                .thenReturn(product);

        mockMvc.perform(put("/api/products/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validProductJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.productName").value("Laptop Samsung"));
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        Long id=1L;

        mockMvc.perform(delete("/api/products/{id}", id))
                .andExpect(status().isNoContent());
        verify(productService).deleteById(id);


    }


    @Test
    void shouldReturnNoProduct()throws Exception{
        Long id=999L;
        doThrow(new ProductNotFoundException("product not found"))
                .when(productService)
                .deleteById(id);

        mockMvc.perform(delete("/api/products/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.product").value("product not found"));


    }










}
