package com.ecom.service.impl;

import com.ecom.entity.StockHistory;
import com.ecom.service.StockHistoryService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockHistoryServiceImpl implements StockHistoryService {


    private static final String DIRECTORY_PATH = "src/main/resources/static/Stock-Data";
    private static final String FILE_NAME = "stock_history.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public StockHistoryServiceImpl() {
        // Ensure the directory and file exist when the service is initialized
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists()) {
            directory.mkdirs(); // Create the directory if it does not exist
        }

        File file = new File(DIRECTORY_PATH, FILE_NAME);
        if (!file.exists()) {
            try {
                // Create an empty file if it does not exist
                file.createNewFile();
                // Initialize with an empty list
                objectMapper.writeValue(file, new ArrayList<StockHistory>());
            } catch (IOException e) {
                e.printStackTrace();
                // Handle exception (e.g., log error, rethrow, etc.)
            }
        }
    }

    @Override
    public void addStockChange(Long productId, int changeAmount, StockHistory.ChangeType changeType, String reason, String productName, Long sellerId) throws IOException {

        File file = new File(DIRECTORY_PATH, FILE_NAME);
        // Make file writable before writing data
        if (!file.setWritable(true)) {
            throw new IOException("Failed to set the file writable.");
        }

        StockHistory history = new StockHistory();
        history.setProductId(productId);
        history.setChangeAmount(changeAmount);
        history.setChangeType(changeType);
        history.setReason(reason);
        history.setUpdatedAt(Timestamp.from(Instant.now()));
        history.setProductName(productName);
        history.setSellerId(sellerId);

        List<StockHistory> historyList = getStockHistory();
        historyList.add(history);
        objectMapper.writeValue(new File(DIRECTORY_PATH, FILE_NAME), historyList);
        // After writing, make the file read-only
        if (!file.setReadOnly()) {
            throw new IOException("Failed to set the file to read-only.");
        }
    }

    @Override
    public List<StockHistory> getRecentStockHistory(int limit) throws IOException {
        List<StockHistory> historyList = getStockHistory();
        historyList.sort(Comparator.comparing(StockHistory::getUpdatedAt).reversed());
        return historyList.stream().limit(limit).toList();
    }

    @Override
    public List<StockHistory> getStockHistoryBySellerId(Long sellerId) throws IOException {
        List<StockHistory> historyList = getStockHistory();
        return historyList.stream()
                .filter(history -> history.getSellerId().equals(sellerId))
                .collect(Collectors.toList());
    }

    private List<StockHistory> getStockHistory() throws IOException {
        File file = new File(DIRECTORY_PATH, FILE_NAME);
        if (file.exists()) {
            return objectMapper.readValue(file, new TypeReference<List<StockHistory>>(){});
        } else {
            // Return an empty list if the file does not exist
            return new ArrayList<>();
        }
    }
}
