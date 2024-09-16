package com.ecom.service;

import com.ecom.entity.Image;

import java.util.Set;

public interface ImageService {

    public Image saveImage(Image image);

    public Set<Image> getImagesByProductId(Long productId);

    void deleteById(Long id);
}
