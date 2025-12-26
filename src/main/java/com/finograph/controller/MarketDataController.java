package com.finograph.controller;

import com.finograph.entity.Stock;
import com.finograph.repository.StockRepository;
import com.finograph.service.MarketDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/market-data")
public class MarketDataController {

    @Autowired
    private MarketDataService service;
    @Autowired
    private StockRepository stockRepository;

    @PostMapping("/fetch/{symbol}")
    public String fetch(@PathVariable String symbol) throws Exception {

        Stock stock = stockRepository.findBySymbol(symbol)
                .orElseThrow(() -> new RuntimeException("Stock not found"));

        service.fetchAndStoreDailyPrices(stock);
        return "Data fetched successfully";
    }

}
