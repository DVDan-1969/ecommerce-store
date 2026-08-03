package org.example.ecomercestore.controller;

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
    public List<Product> getAllProducts() {
        return service.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return service.getProductById(id);
    }

    @PostMapping
    public Product saveProduct(@RequestBody Product product) {
        return service.save(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProductById(@PathVariable Long id, @RequestBody Product product) {
        Product updatedProduct = service.update(id, product);
        return ResponseEntity.ok(updatedProduct);
    }
}
