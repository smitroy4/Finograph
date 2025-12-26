package com.finograph.controller;

import com.finograph.entity.HistoricalPrice;
import com.finograph.entity.Stock;
import com.finograph.repository.StockRepository;
import com.finograph.service.HistoricalPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/prices/")
public class HistoricalPriceController {

    @Autowired
    private HistoricalPriceService historicalPriceService;
    @Autowired
    private StockRepository stockRepository;

    // READ API (used by frontend charts)
    @GetMapping("/{symbol}")
    public List<HistoricalPrice> getPrices(@PathVariable String symbol) {
        Stock stock = stockRepository.findBySymbol(symbol)
                .orElseThrow(() -> new RuntimeException("Stock not found"));

        return historicalPriceService.getPricesForStock(stock);
    }

    // TEMP WRITE API (for learning & testing only)
    @PostMapping("/{symbol}")
    public HistoricalPrice addPrice(
            @PathVariable String symbol,
            @RequestBody HistoricalPrice price) {

        Stock stock = stockRepository.findBySymbol(symbol)
                .orElseThrow(() -> new RuntimeException("Stock not found"));

        price.setStock(stock);
        return historicalPriceService.save(price);
    }

}
