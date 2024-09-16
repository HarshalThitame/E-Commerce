package com.ecom.entity;

import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Data
public class StockHistory {

    private Long productId;
    private String productName;
    private int changeAmount;
    private ChangeType changeType;
    private String reason;
    private Long sellerId;

    @UpdateTimestamp
    private Timestamp updatedAt;

    public enum ChangeType {
        ADDITION, REMOVAL, ADJUSTMENT
    }

    // Getters and setters
}
