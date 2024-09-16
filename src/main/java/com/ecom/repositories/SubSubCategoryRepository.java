package com.ecom.repositories;

import com.ecom.entity.SubSubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubSubCategoryRepository extends JpaRepository<SubSubCategory, Long> {

    Optional<List<SubSubCategory>> findBySubCategoryId(Long subCategoryId);

}
