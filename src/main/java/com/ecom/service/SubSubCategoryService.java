package com.ecom.service;

import com.ecom.entity.SubSubCategory;

import java.util.List;
import java.util.Optional;

public interface SubSubCategoryService {
    Optional<SubSubCategory> findById(Long id);
    List<SubSubCategory> findAll();
    SubSubCategory save(SubSubCategory subSubCategory);
    void deleteById(Long id);

    SubSubCategory update(Long id, SubSubCategory subSubCategory);
}

