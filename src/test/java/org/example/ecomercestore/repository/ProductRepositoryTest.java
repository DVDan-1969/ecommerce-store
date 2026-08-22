package org.example.ecomercestore.repository;

import org.example.ecomercestore.model.Category;
import org.example.ecomercestore.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Test
    void shouldFindProductsWhenNameMatchesPartially() {
        Category category = new Category();
        category.setCategoryName("Electronics");

        Category savedCategory = categoryRepository.saveAndFlush(category);

        assertThat(savedCategory).isNotNull();
        assertThat(savedCategory.getId()).isNotNull();

        Product p1 = new Product();
        p1.setName("Laptop Asus");
        p1.setCategory(savedCategory);

        Product p2 = new Product();
        p2.setName("Laptop Lenovo");
        p2.setCategory(savedCategory);

        Product p3 = new Product();
        p3.setName("Apple iPhone 15");
        p3.setCategory(savedCategory);

        productRepository.saveAll(List.of(p1,p2,p3));

        List<Product>result=productRepository.searchByName("Laptop");
        assertThat(result).hasSize(2);
        List<String>names=result.stream()
                .map(Product::getName)
                .toList();
        assertThat(names)
                .contains("Laptop Asus","Laptop Lenovo");

    }
}
