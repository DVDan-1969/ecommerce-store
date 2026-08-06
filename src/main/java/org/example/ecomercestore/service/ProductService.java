package org.example.ecomercestore.service;

import org.example.ecomercestore.dto.ProductRequestDTO;
import org.example.ecomercestore.dto.ProductResponseDTO;


import java.util.List;

public interface ProductService {
    List<ProductResponseDTO> getAllProducts();

    ProductResponseDTO getProductById(Long id);

    ProductResponseDTO save(ProductRequestDTO dto);

    ProductResponseDTO update(Long id,ProductRequestDTO dto);


    void deleteById(Long id);
}
