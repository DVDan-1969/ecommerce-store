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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

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

        logger.info("Retrieving all products");


        return repository.findAll()
                .stream()
                .map(productMapper::toResponseDTO)
                .toList();

    }
    @Override
    public List<ProductResponseDTO> searchByName(String name){
        logger.info("Searching for products by name:{}",name);

        return repository.searchByName(name)
                .stream()
                .map(productMapper::toResponseDTO)
                .toList();
    }
    @Override
    public Page<ProductResponseDTO> getAllProductsPageable(Pageable pageable) {
        Page<Product> products = repository.findAll(pageable);

        return products.map(productMapper::toResponseDTO);
    }

    @Override
    public ProductResponseDTO getProductById(Long id) {
        logger.info("Searching for product with id {}", id);
        Product product = repository.findById(id)
                .orElseThrow(()->{
                    logger.warn("Product with id {} not found", id);
                    return new ProductNotFoundException("Product not found");
                });


        return productMapper.toResponseDTO(product);
    }

    @Override
    public ProductResponseDTO save(ProductRequestDTO dto) {

       logger.info("Creating product with category id {}", dto.getCategoryId());
        Category category= categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(()->{
                    logger.warn("Category with id {} not found", dto.getCategoryId());
                     return new CategoryNotFoundException("Category not found");
                });
        Product product = productMapper.toEntity(dto,category);
        Product savedProduct = repository.save(product);
        logger.info("Product created successfully with id {}",savedProduct.getId());
        return productMapper.toResponseDTO(savedProduct);
    }

    @Override
    public ProductResponseDTO update(Long id, ProductRequestDTO dto) {

        logger.info("Updating product with id {}", id);
        Product product = repository.findById(id)
                .orElseThrow(()->{
                    logger.warn("Product with id {} not found",id);
                    return new ProductNotFoundException("Product not found");
                });
        Category category=categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(()->{
                    logger.warn("Category with id {} not found",dto.getCategoryId());
                    return new CategoryNotFoundException("Category not found");
                });
        product.setName(dto.getProductName());
        product.setDescription(dto.getProductDescription());
        product.setPrice(dto.getProductPrice());
        product.setQuantity(dto.getProductQuantity());
        product.setImage(dto.getProductImage());
        product.setCategory(category);
        Product updatedProduct = repository.save(product);
        logger.info("Product with id {} updated successfully",updatedProduct.getId());
        return productMapper.toResponseDTO(updatedProduct);
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Deleting product with id {}", id);
        Product product= repository.findById(id)
                .orElseThrow(()->{
                    logger.warn("Product with id {} not found", id);
                    return new ProductNotFoundException("Product not found");
                });
        repository.delete(product);
        logger.info("Product with id {} deleted successfully",id);
    }
}


