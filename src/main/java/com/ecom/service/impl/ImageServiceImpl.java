package com.ecom.service.impl;

import com.ecom.entity.Image;
import com.ecom.repositories.ImageRepository;
import com.ecom.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public Set<Image> getImagesByProductId(Long productId) {
        return imageRepository.findByProductId(productId);
    }

    @Override
    public void deleteById(Long id) {
        imageRepository.deleteById(id);
    }


}
