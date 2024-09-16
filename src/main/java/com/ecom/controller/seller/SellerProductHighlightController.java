package com.ecom.controller.seller;

import com.ecom.service.ProductHighlightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/seller/highlight")
public class SellerProductHighlightController {

    @Autowired
    private ProductHighlightsService productHighlightsService;

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductHighlightById(@PathVariable Long id) {
        productHighlightsService.deleteProductHighlightById(id);
        return ResponseEntity.ok().build();
    }
}
