package com.ecom.service;

import com.ecom.entity.SubCategory;

import java.util.List;
import java.util.Optional;

public interface SubCategoryService {
    Optional<SubCategory> findById(Long id);
    List<SubCategory> findAll();
    SubCategory save(SubCategory subCategory);
    void deleteById(Long id);

    SubCategory update(Long id, SubCategory categoryDetails);
}

