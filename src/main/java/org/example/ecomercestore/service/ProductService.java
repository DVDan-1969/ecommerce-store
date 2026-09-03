package org.example.ecomercestore.service;

import org.example.ecomercestore.dto.ProductRequestDTO;
import org.example.ecomercestore.dto.ProductResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;


import java.util.List;

public interface ProductService {

    List<ProductResponseDTO> searchByName(String name);


    List<ProductResponseDTO> getAllProducts();

    Page<ProductResponseDTO> getAllProductsPageable(Pageable pageable);

    ProductResponseDTO getProductById(Long id);

    ProductResponseDTO save(ProductRequestDTO dto);

    ProductResponseDTO update(Long id,ProductRequestDTO dto);


    void deleteById(Long id);
}
