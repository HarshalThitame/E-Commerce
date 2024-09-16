package com.ecom.controller.seller;

import com.ecom.entity.Image;
import com.ecom.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/api/seller/images")
public class SellerImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping("/{productId}")
    public ResponseEntity<Set<Image>> getImagesByProductId(@PathVariable Long productId) {
        Set<Image> images = imageService.getImagesByProductId(productId);
        return ResponseEntity.ok(Objects.requireNonNullElseGet(images, HashSet::new));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Image> deleteImage(@PathVariable Long id) {
        imageService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
