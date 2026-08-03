package org.example.ecomercestore.service;

import jakarta.transaction.Transactional;
import org.example.ecomercestore.model.Category;
import org.example.ecomercestore.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;

    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<Category> getAllCategories() {
        return repository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return repository.findById(id).orElse(null);
    }


    @Override
    public Category saveCategory(Category category) {
        return repository.save(category);}


    @Override
    public Category updateCategory(Long id, Category category) {
        return repository.save(category);
    }


    @Override
    public void deleteCategory(Long id) {
        repository.deleteById(id);
    }

}



