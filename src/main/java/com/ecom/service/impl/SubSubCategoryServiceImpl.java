package com.ecom.service.impl;

import com.ecom.entity.SubSubCategory;
import com.ecom.repositories.SubSubCategoryRepository;
import com.ecom.service.SubSubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubSubCategoryServiceImpl implements SubSubCategoryService {

    @Autowired
    private SubSubCategoryRepository subSubCategoryRepository;

    @Override
    public Optional<SubSubCategory> findById(Long id) {
        return subSubCategoryRepository.findById(id);
    }

    @Override
    public List<SubSubCategory> findAll() {
        return subSubCategoryRepository.findAll();
    }

    @Override
    public SubSubCategory save(SubSubCategory subSubCategory) {
        return subSubCategoryRepository.save(subSubCategory);
    }

    @Override
    public void deleteById(Long id) {
        subSubCategoryRepository.deleteById(id);
    }

    @Override
    public SubSubCategory update(Long id, SubSubCategory subSubCategory) {
        Optional<SubSubCategory> subSubCategoryOptional = subSubCategoryRepository.findById(id);
        if (subSubCategoryOptional.isPresent()) {
            return subSubCategoryRepository.save(subSubCategory);
        }
        return null;
    }
}
