package com.ecom.service.impl;

import com.ecom.entity.SubCategory;
import com.ecom.repositories.SubCategoryRepository;
import com.ecom.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Override
    public Optional<SubCategory> findById(Long id) {
        return subCategoryRepository.findById(id);
    }

    @Override
    public List<SubCategory> findAll() {
        return subCategoryRepository.findAll();
    }

    @Override
    public SubCategory save(SubCategory subCategory) {
        return subCategoryRepository.save(subCategory);
    }

    @Override
    public void deleteById(Long id) {

        subCategoryRepository.deleteById(id);
    }

    @Override
    public SubCategory update(Long id, SubCategory categoryDetails) {
        Optional<SubCategory> subCategory = subCategoryRepository.findById(id);
        if (subCategory.isPresent()) {
            return subCategoryRepository.save(categoryDetails);
        }
        return null;
    }
}
