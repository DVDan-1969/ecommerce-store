package org.example.ecomercestore.controller;


import jakarta.validation.Valid;
import org.example.ecomercestore.dto.ProductRequestDTO;
import org.example.ecomercestore.dto.ProductResponseDTO;
import org.example.ecomercestore.model.Product;
import org.example.ecomercestore.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductResponseDTO> getAllProducts() {
        return service.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductResponseDTO getProductById(@PathVariable Long id) {

        return service.getProductById(id);
    }

    @PostMapping
    public ProductResponseDTO saveProduct(@Valid @RequestBody ProductRequestDTO dto) {
        return service.save(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProductById(
            @PathVariable Long id,
            @Valid@RequestBody ProductRequestDTO dto) {
        ProductResponseDTO updatedProduct = service.update(id,dto);
        return ResponseEntity.ok(updatedProduct);
    }
}
