package com.finograph.service;

import com.finograph.entity.HistoricalPrice;
import com.finograph.entity.Stock;
import com.finograph.repository.HistoricalPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoricalPriceService {

    @Autowired
    private HistoricalPriceRepository historicalPriceRepository;

    public HistoricalPrice save(HistoricalPrice historicalPrice){
        return historicalPriceRepository.save(historicalPrice);
    }

    public List<HistoricalPrice> getPricesForStock(Stock stock){
        return historicalPriceRepository.findByStockOrderByDateAsc(stock);
    }

}
