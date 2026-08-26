package org.example.ecomercestore.service;

import org.example.ecomercestore.dto.CategoryRequestDTO;
import org.example.ecomercestore.dto.CategoryResponseDTO;
import org.example.ecomercestore.exception.CategoryNotFoundException;
import org.example.ecomercestore.mapper.CategoryMapper;
import org.example.ecomercestore.model.Category;
import org.example.ecomercestore.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;
    private CategoryResponseDTO categoryResponseDTO;
    private CategoryRequestDTO categoryRequestDTO;

    @BeforeEach
    public void setUp() {
        category = new Category();
        category.setId(1L);
        category.setCategoryName("Electronics");

        categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setId(1L);
        categoryResponseDTO.setCategoryName("Electronics");

        categoryRequestDTO = new CategoryRequestDTO();
        categoryRequestDTO.setCategoryName("Electronics");


    }
    @Test
    void shouldReturnListofCategories() {
        List<Category> categories = Arrays.asList(category);



        when(categoryRepository.findAll()).thenReturn(categories);
        when (categoryMapper.toResponseDTO(category))
                .thenReturn(categoryResponseDTO);

        List<CategoryResponseDTO> result = categoryService.getAllCategories();

        assertNotNull(result);
        assertEquals(categories.size(), result.size());
        assertEquals("Electronics", result.get(0).getCategoryName());

    }

    @Test
    void shouldThrowExceptionWhenIdNotFound() {
        Long id = 999L;

        when(categoryRepository.findById(id))
                .thenReturn(Optional.empty());


        CategoryNotFoundException exception = assertThrows(
                CategoryNotFoundException.class,
                () -> categoryService.getCategoryById(id)
        );

        assertEquals("category not found", exception.getMessage());
    }

    @Test
    void shouldReturnCategoryWhenCategoryFound() {

        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(category));
        when (categoryMapper.toResponseDTO(category))
                .thenReturn(categoryResponseDTO);

        CategoryResponseDTO result = categoryService.getCategoryById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getCategoryName()).isEqualTo("Electronics");
    }
    @Test
    void shouldSaveAndReturnCategory(){
        when(categoryMapper.toEntity(categoryRequestDTO)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toResponseDTO(category)).thenReturn(categoryResponseDTO);

        CategoryResponseDTO result = categoryService.saveCategory(categoryRequestDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getCategoryName()).isEqualTo("Electronics");

    }

    @Test
    void shouldUpdateCategory(){
        Long id=1L;
        categoryRequestDTO.setCategoryName("Books");
        categoryResponseDTO.setCategoryName("Books");


        when(categoryRepository.findById(1L)).
                thenReturn(Optional.of(category));
        when(categoryMapper.toResponseDTO(category))
                .thenReturn(categoryResponseDTO);
        when(categoryRepository.save(category))
                .thenReturn(category);


        CategoryResponseDTO result =
                categoryService.updateCategory(id, categoryRequestDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getCategoryName()).isEqualTo("Books");
        assertThat(category.getCategoryName()).isEqualTo("Books");
    }
    @Test
    void shouldThrowExceptionWhenUpdatingCategoryNotFound() {
        Long id=999L;

        when(categoryRepository.findById(id))
                .thenReturn(Optional.empty());

        CategoryNotFoundException exception = assertThrows(
                CategoryNotFoundException.class,
                () -> categoryService.updateCategory(id, categoryRequestDTO)
        );

        assertEquals("category not found", exception.getMessage());
    }

    @Test
    void shouldDeleteCategory() {
        Long id = 1L;

        when(categoryRepository.findById(id))
                .thenReturn(Optional.of(category));

        categoryService.deleteCategory(id);

        verify(categoryRepository).delete(category);
    }
    @Test
    void shouldThrowExceptionWhenDeletingCategoryNotFound() {
        Long id=999L;

        when(categoryRepository.findById(id))
                .thenReturn(Optional.empty());

        CategoryNotFoundException exception=assertThrows(CategoryNotFoundException.class,
                        () -> categoryService.deleteCategory(id));
        assertEquals("category not found", exception.getMessage());

    }

}
