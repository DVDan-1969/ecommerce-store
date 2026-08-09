package org.example.ecomercestore.service;

import org.example.ecomercestore.dto.CategoryRequestDTO;
import org.example.ecomercestore.dto.CategoryResponseDTO;


import java.util.List;

public interface CategoryService {

    List<CategoryResponseDTO> getAllCategories();

    CategoryResponseDTO getCategoryById(Long id);

    CategoryResponseDTO saveCategory(CategoryRequestDTO dto);

    CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO dto);

    void deleteCategory(Long id);
}
