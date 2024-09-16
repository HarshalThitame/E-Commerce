package com.ecom.repositories;

import com.ecom.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    public Set<Image> findByProductId(Long id);

    void deleteByProductId(Long id);
}
