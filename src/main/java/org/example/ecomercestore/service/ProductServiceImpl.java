package org.example.ecomercestore.service;

import jakarta.transaction.Transactional;
import org.example.ecomercestore.dto.ProductRequestDTO;
import org.example.ecomercestore.dto.ProductResponseDTO;
import org.example.ecomercestore.exception.CategoryNotFoundException;
import org.example.ecomercestore.exception.ProductNotFoundException;
import org.example.ecomercestore.mapper.ProductMapper;
import org.example.ecomercestore.model.Category;
import org.example.ecomercestore.model.Product;
import org.example.ecomercestore.repository.CategoryRepository;
import org.example.ecomercestore.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository repository,
                              ProductMapper productMapper,
                              CategoryRepository categoryRepository) {
        this.repository = repository;
        this.productMapper = productMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {

        return repository.findAll()
                .stream()
                .map(productMapper::toResponseDTO)
                .toList();

    }

    @Override
    public ProductResponseDTO getProductById(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(()->new ProductNotFoundException("product not found"));

        return productMapper.toResponseDTO(product);
    }

    @Override
    public ProductResponseDTO save(ProductRequestDTO dto) {
        Category category= categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(()->new CategoryNotFoundException("Category not found"));
        Product product = productMapper.toEntity(dto,category);
        Product savedProduct = repository.save(product);
        return productMapper.toResponseDTO(savedProduct);
    }

    @Override
    public ProductResponseDTO update(Long id, ProductRequestDTO dto) {
        Product product = repository.findById(id)
                .orElseThrow(()->new ProductNotFoundException("Product not found"));
        Category category=categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(()->new CategoryNotFoundException("Category not found"));
        product.setName(dto.getProductName());
        product.setDescription(dto.getProductDescription());
        product.setPrice(dto.getProductPrice());
        product.setQuantity(dto.getProductQuantity());
        product.setImage(dto.getProductImage());
        product.setCategory(category);
        Product updatedProduct = repository.save(product);
        return productMapper.toResponseDTO(updatedProduct);
    }

    @Override
    public void deleteById(Long id) {
        Product product= repository.findById(id)
                .orElseThrow(()->new ProductNotFoundException("Product not found"));
        repository.delete(product);
    }
}


