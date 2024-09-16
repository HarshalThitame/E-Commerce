package com.ecom.service;

import com.ecom.entity.StockHistory;

import java.io.IOException;
import java.util.List;

public interface StockHistoryService {

    void addStockChange(Long productId, int changeAmount, StockHistory.ChangeType changeType, String reason, String productName, Long sellerId) throws IOException;
    public List<StockHistory> getRecentStockHistory(int limit) throws IOException;
    List<StockHistory> getStockHistoryBySellerId(Long sellerId) throws IOException;

}
