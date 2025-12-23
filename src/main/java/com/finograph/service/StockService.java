package com.finograph.service;

import com.finograph.entity.Stock;
import com.finograph.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public Stock save(Stock stock){
        return stockRepository.save(stock);
    }

}
