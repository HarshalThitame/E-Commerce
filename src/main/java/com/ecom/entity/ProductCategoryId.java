package com.ecom.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategoryId implements Serializable {
    private Long productId;
    private Long categoryId;


    // Getters, setters, hashCode, equals
}
