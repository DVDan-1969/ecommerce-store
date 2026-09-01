package org.example.ecomercestore.service;

import org.example.ecomercestore.dto.ProductResponseDTO;
import org.example.ecomercestore.exception.ProductNotFoundException;
import org.example.ecomercestore.mapper.ProductMapper;
import org.example.ecomercestore.model.Product;
import org.example.ecomercestore.repository.CategoryRepository;
import org.example.ecomercestore.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        Long id=999L;

        when(productRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                ProductNotFoundException.class,
                ()->productService.getProductById(id)
        );
    }
    @Test
    void shouldReturnProductWhenProductFound() {
        Long id=1L;
        Product p= new Product();
        p.setId(id);
        p.setName("Laptop Samsung");
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(p.getId());
        productResponseDTO.setProductName("Laptop Samsung");

        when(productRepository.findById(id))
                .thenReturn(Optional.of(p));
        when(productMapper.toResponseDTO(p))
                .thenReturn(productResponseDTO);

        ProductResponseDTO result = productService.getProductById(id);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getProductName()).isEqualTo("Laptop Samsung");


    }


}
