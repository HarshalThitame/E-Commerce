package com.ecom.controller.seller;

import com.ecom.entity.StockHistory;
import com.ecom.service.StockHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/seller/stock-history")
public class SellerStockHistoryController {

    @Autowired
    private StockHistoryService stockHistoryService;

    @PostMapping
    public ResponseEntity<String> logStockChange(@RequestBody StockHistory stockHistory) {
        try {
            stockHistoryService.addStockChange(
                    stockHistory.getProductId(),
                    stockHistory.getChangeAmount(),
                    stockHistory.getChangeType(),
                    stockHistory.getReason(),
                    stockHistory.getProductName(),
                    stockHistory.getSellerId()

            );
            return ResponseEntity.status(HttpStatus.CREATED).body("Stock change logged");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error logging stock change");
        }
    }

    @GetMapping("/recent")
    public ResponseEntity<List<StockHistory>> getRecentStockHistory(@RequestParam(defaultValue = "10") int limit) {
        try {
            List<StockHistory> history = stockHistoryService.getRecentStockHistory(limit);
            return ResponseEntity.ok(history);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<StockHistory>> getStockHistoryByProductId(@PathVariable Long sellerId) {
        try {
            List<StockHistory> history = stockHistoryService.getStockHistoryBySellerId(sellerId);
            return ResponseEntity.ok(history);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
