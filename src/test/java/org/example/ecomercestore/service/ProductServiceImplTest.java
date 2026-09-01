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

import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
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
    @Test
    void shouldReturnProductPage(){

        Product product = new Product();

        Pageable pageable=PageRequest.of(0, 5);

        Page<Product> productPage =
                new PageImpl<>(List.of(product));

        when(productRepository.findAll(pageable))
                .thenReturn(productPage);

        ProductResponseDTO responseDTO= new ProductResponseDTO();

        when(productMapper.toResponseDTO(product))
                .thenReturn(responseDTO);

        Page<ProductResponseDTO> result =
                productService.getAllProductsPageable(pageable);

        assertNotNull(result);
        assertEquals(1,result.getNumberOfElements());
        assertEquals(responseDTO, result.getContent().get(0));

        verify(productRepository).findAll(pageable);
        verify(productMapper).toResponseDTO(product);
    }
    @Test
    void shouldReturnProductsPageSortedByPriceAscending(){
        Pageable pageable =
                PageRequest.of(0, 5, Sort.by("price").ascending());
       Product product = new Product();
       Page<Product> productPage =
                new PageImpl<>(List.of(product));
       when(productRepository.findAll(pageable))
               .thenReturn(productPage);
       ProductResponseDTO responseDTO= new ProductResponseDTO();
       when(productMapper.toResponseDTO(product))
               .thenReturn(responseDTO);
       Page<ProductResponseDTO> result =
                productService.getAllProductsPageable(pageable);
       assertNotNull(result);
       assertEquals(1,result.getNumberOfElements());
       assertEquals(Sort.Direction.ASC,
               pageable.getSort().getOrderFor("price").getDirection()
       );
       verify(productRepository).findAll(pageable);
       verify(productMapper).toResponseDTO(product);

    }


}
