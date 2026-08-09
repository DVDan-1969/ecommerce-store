package org.example.ecomercestore.mapper;


import org.example.ecomercestore.dto.CategoryRequestDTO;
import org.example.ecomercestore.dto.CategoryResponseDTO;
import org.example.ecomercestore.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public Category toEntity(CategoryRequestDTO  dto) {
        Category category = new Category();

        category.setCategoryName(dto.getCategoryName());
        return category;
    }

    public CategoryResponseDTO toResponseDTO(Category category) {
       return  new CategoryResponseDTO(
               category.getId(),
               category.getCategoryName()
       );


    }
}
