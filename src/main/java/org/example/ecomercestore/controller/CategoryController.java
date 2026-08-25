package org.example.ecomercestore.controller;

import jakarta.validation.Valid;
import org.example.ecomercestore.dto.CategoryRequestDTO;
import org.example.ecomercestore.dto.CategoryResponseDTO;
import org.example.ecomercestore.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public List<CategoryResponseDTO> getAllCategories() {
        return service.getAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryResponseDTO getCategoryById(@PathVariable Long id) {
        return service.getCategoryById(id);
    }

    @PostMapping
    public CategoryResponseDTO saveCategory(@Valid @RequestBody CategoryRequestDTO dto) {
        return service.saveCategory(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable Long id) {
        service.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryRequestDTO dto) {
        CategoryResponseDTO updatedCategory = service.updateCategory(id,dto);
        return ResponseEntity.ok(updatedCategory);
    }
}
