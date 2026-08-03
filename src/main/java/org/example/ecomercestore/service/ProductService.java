package org.example.ecomercestore.service;

import org.example.ecomercestore.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    Product getProductById(Long id);

    Product save(Product product);

    Product update(Long id,Product product);


    void deleteById(Long id);
}
