package com.ecom.service.impl;

import com.ecom.entity.Category;
import com.ecom.entity.SubCategory;
import com.ecom.entity.SubSubCategory;
import com.ecom.repositories.CategoryRepository;
import com.ecom.repositories.SubCategoryRepository;
import com.ecom.repositories.SubSubCategoryRepository;
import com.ecom.service.CategoryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SubCategoryRepository subCategoryRepository;
    @Autowired
    private SubSubCategoryRepository subSubCategoryRepository;

    @Override
    public Optional<Category> findById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        Optional<List<SubCategory>> subCategories = subCategoryRepository.findByCategoryId(category.get().getId());
        for(SubCategory s:subCategories.get()){
            Optional<List<SubSubCategory>> subSubCategories = subSubCategoryRepository.findBySubCategoryId(s.getId());
            s.setSubSubCategories((List<SubSubCategory>) subSubCategories.get());
        }
        category.get().setSubCategories((List<SubCategory>) subCategories.get());
        return category;
    }

    @Override
    public List<Category> findAll() {
        List<Category> categories = categoryRepository.findAll();
//        for (Category category : categories) {
//            Optional<List<SubCategory>> optionalSubCategories = subCategoryRepository.findByCategoryId(category.getId());
//            if (optionalSubCategories.isPresent()) {
//                List<SubCategory> subCategorySet = new ArrayList<>(optionalSubCategories.get()); // Convert List to Set
//                for (SubCategory s : subCategorySet) {
//                    Optional<List<SubSubCategory>> optionalSubSubCategories = subSubCategoryRepository.findBySubCategoryId(s.getId());
//                    if (optionalSubSubCategories.isPresent()) {
//                        List<SubSubCategory> subSubCategorySet = new ArrayList<>(optionalSubSubCategories.get()); // Convert List to Set
//                        s.setSubSubCategories(subSubCategorySet);
//                    }
//                }
//                category.setSubCategories(subCategorySet);
//            }
//        }

        return categories;
    }


    @Transactional
    @Override
    public Category save(Category category) {
        // Debugging: Print the initial state of the category being saved

        List<SubCategory> newSubCategory = new ArrayList<>();
        List<SubSubCategory> newSubSubCategory = new ArrayList<>();

        List<SubCategory> subCategories = category.getSubCategories();


        category.setSubCategories(null);
        Category savedCategory = categoryRepository.save(category);


        for (SubCategory subCategory : subCategories) {

            List<SubSubCategory> subSubCategories = subCategory.getSubSubCategories();


            subCategory.setSubSubCategories(null);
            subCategory.setCategory(savedCategory);
            SubCategory savedSubCategory = subCategoryRepository.save(subCategory);


            for (SubSubCategory subSubCategory : subSubCategories) {

                subSubCategory.setSubCategory(savedSubCategory);
                SubSubCategory savedSubSubCategory = subSubCategoryRepository.save(subSubCategory);


            }
        }

        savedCategory.setSubCategories(newSubCategory);


        // Final save and debugging print statement

        return categoryRepository.save(savedCategory);
    }


    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category update(Long id, Category categoryDetails) {
        return categoryRepository.save(categoryDetails);
    }

    @Override
    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }


}