package org.example.ecomercestore.service;

import jakarta.transaction.Transactional;
import org.example.ecomercestore.dto.CategoryRequestDTO;
import org.example.ecomercestore.dto.CategoryResponseDTO;
import org.example.ecomercestore.exception.CategoryNotFoundException;
import org.example.ecomercestore.mapper.CategoryMapper;
import org.example.ecomercestore.model.Category;
import org.example.ecomercestore.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }


    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponseDTO)
                .toList();


    }

    @Override
    public CategoryResponseDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()->new CategoryNotFoundException("category not found"));
        return categoryMapper.toResponseDTO(category);
    }


    @Override
    public CategoryResponseDTO saveCategory(CategoryRequestDTO dto) {
      Category category = categoryMapper.toEntity(dto);
      Category savedCategory = categoryRepository.save(category);
      return categoryMapper.toResponseDTO(savedCategory);
    }


    @Override
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO dto) {
        Category category =categoryRepository.findById(id)
                .orElseThrow(()->new CategoryNotFoundException("category not found"));
        category.setCategoryName(dto.getCategoryName());
        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.toResponseDTO(updatedCategory);
    }


    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()->new CategoryNotFoundException("category not found"));
        categoryRepository.delete(category);
    }

}



