package org.example.ecomercestore.controller;


import org.example.ecomercestore.dto.CategoryResponseDTO;
import org.example.ecomercestore.dto.CategoryRequestDTO;
import org.example.ecomercestore.exception.CategoryNotFoundException;
import org.example.ecomercestore.service.CategoryService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.data.jpa.repository.query.QueryEnhancerFactories.eql;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.List;


@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private CategoryService categoryService;


    @Test
   void shouldReturnCategoryExists() throws Exception {
        Long id = 1L;
        CategoryResponseDTO category=new CategoryResponseDTO();
        category.setId(id);
        category.setCategoryName("Electronics");

        when(categoryService.getCategoryById(id))
                .thenReturn(category);

        mockMvc.perform(MockMvcRequestBuilders.get("/categories/{id}",id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.categoryName").value("Electronics"));

    }


    @Test
    void shouldReturnCategoryNotExists() throws Exception {
        Long id = 999L;
        when(categoryService.getCategoryById(id))
                .thenThrow(new CategoryNotFoundException("category not found"));
        mockMvc.perform(get("/categories/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.category").value("category not found"));

    }


    @Test
    void shouldReturnCategories() throws Exception {
        CategoryResponseDTO category1=new CategoryResponseDTO();
        category1.setCategoryName("Electronics");
        category1.setId(1L);

        CategoryResponseDTO category2=new CategoryResponseDTO();
        category2.setCategoryName("Books");
        category2.setId(2L);


        when(categoryService.getAllCategories())
                .thenReturn(List.of(category1,category2));

        mockMvc.perform(MockMvcRequestBuilders.get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].categoryName").value("Electronics"))
                .andExpect(jsonPath("$[1].categoryName").value("Books"));

    }


    @Test
    void shouldSaveCategory() throws Exception {
        CategoryResponseDTO category=new CategoryResponseDTO();
        category.setCategoryName("Electronics");
        category.setId(1L);

        String validCategoryJson= """
                {
                "categoryName": "Electronics"
                }
                """;

        when(categoryService.saveCategory(any(CategoryRequestDTO.class)))
                .thenReturn(category);

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validCategoryJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.categoryName").value("Electronics"));

    }

    @Test
    void shouldReturnBadRequestWhenCategoryNameIsEmpty() throws Exception {
        String invalidCategoryJson = """
        {
            "categoryName": ""
        }
        """;


        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidCategoryJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.categoryName").value("must not be blank"));
    }

    @Test
    void shouldReturnCategorySaved() throws Exception {
        CategoryResponseDTO category=new CategoryResponseDTO();

        category.setId(1L);
        category.setCategoryName("Books");

        String validCategoryJson = """
        {
            "categoryName": "Books"
            
        }
        """;
        when(categoryService.updateCategory(eq(1L),any(CategoryRequestDTO.class)))
                .thenReturn(category);

        mockMvc.perform(put("/categories/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validCategoryJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.categoryName").value("Books"));
    }
    @Test
    void shouldDeleteCategory() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/categories/{id}", id))
                .andExpect(status().isNoContent());
        verify(categoryService).deleteCategory(id);

    }

    @Test
    void shouldReturnNoCategory() throws Exception {
        Long id = 999L;

        doThrow(new CategoryNotFoundException("category not found"))
                .when(categoryService)
                .deleteCategory(id);

        mockMvc.perform(delete("/categories/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.category").value("category not found"));
    }



}
