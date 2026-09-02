package org.example.ecomercestore.service;

import jakarta.transaction.Transactional;
import org.example.ecomercestore.dto.CategoryRequestDTO;
import org.example.ecomercestore.dto.CategoryResponseDTO;
import org.example.ecomercestore.exception.CategoryNotFoundException;
import org.example.ecomercestore.mapper.CategoryMapper;
import org.example.ecomercestore.model.Category;
import org.example.ecomercestore.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }


    @Override
    public List<CategoryResponseDTO> getAllCategories() {

        logger.info("Retrieving all categories");

                return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponseDTO)
                .toList();


    }

    @Override
    public CategoryResponseDTO getCategoryById(Long id) {
        logger.info("Searching for category with id {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(()->{
                    logger.warn("Category with id {} not found",id);
                    return new CategoryNotFoundException("category not found");
                });
        return categoryMapper.toResponseDTO(category);
    }


    @Override
    public CategoryResponseDTO saveCategory(CategoryRequestDTO dto) {
        logger.info("Creating new category");
      Category category = categoryMapper.toEntity(dto);
      Category savedCategory = categoryRepository.save(category);
      logger.info("Category created successfully with id {}", savedCategory.getId());
      return categoryMapper.toResponseDTO(savedCategory);
    }


    @Override
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO dto) {

        logger.info("Updating category with id {}", id);
        Category category =categoryRepository.findById(id)
                .orElseThrow(()->{
                    logger.warn("Category with id {} not found",id);
                     return new CategoryNotFoundException("category not found");
                });
        category.setCategoryName(dto.getCategoryName());
        Category updatedCategory = categoryRepository.save(category);
        logger.info("Category  with id {} updated successfully", updatedCategory.getId());
        return categoryMapper.toResponseDTO(updatedCategory);
    }


    @Override
    public void deleteCategory(Long id) {
        logger.info("Deleting category with id {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> {
                            logger.warn("Category with id {} not found", id);
                            return new CategoryNotFoundException("category not found");
                });
        categoryRepository.delete(category);
        logger.info("Category with id {} deleted successfully", id);
    }

}



