package com.ecom.service;

import com.ecom.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Optional<Category> findById(Long id);
    List<Category> findAll();
    Category save(Category category);
    void deleteById(Long id);

    Category update(Long id, Category categoryDetails);

    Category findByName(String name);

}

