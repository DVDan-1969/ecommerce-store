package org.example.ecomercestore.mapper;

import org.example.ecomercestore.dto.ProductRequestDTO;
import org.example.ecomercestore.dto.ProductResponseDTO;
import org.example.ecomercestore.model.Category;
import org.example.ecomercestore.model.Product;
import org.springframework.stereotype.Component;


@Component
public class ProductMapper {
    public Product toEntity(ProductRequestDTO dto, Category category){
        Product product = new Product();

        product.setName(dto.getProductName());
        product.setPrice(dto.getProductPrice());
        product.setQuantity(dto.getProductQuantity());
        product.setDescription(dto.getProductDescription());
        product.setImage(dto.getProductImage());
        product.setCategory(category);
        return product;
    }
    public ProductResponseDTO toResponseDTO(Product product){
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getQuantity(),
                product.getDescription(),
                product.getPrice(),
                product.getImage()


        );
    }

}
