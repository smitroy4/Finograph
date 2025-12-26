package com.finograph.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finograph.entity.HistoricalPrice;
import com.finograph.entity.Stock;
import com.finograph.repository.HistoricalPriceRepository;
import com.finograph.service.external.YahooFinanceClient;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Service
public class MarketDataService {

    private final YahooFinanceClient yahooClient;
    private final HistoricalPriceRepository repository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public MarketDataService(
            YahooFinanceClient yahooClient,
            HistoricalPriceRepository repository) {
        this.yahooClient = yahooClient;
        this.repository = repository;
    }

    public void fetchAndStoreDailyPrices(Stock stock) throws Exception {

        String response = yahooClient.fetchChartData(stock.getSymbol());
        System.out.println("RAW YAHOO RESPONSE:");
        System.out.println(response);

        JsonNode root = objectMapper.readTree(response);
        JsonNode resultNode = root.path("chart").path("result");

        if (!resultNode.isArray() || resultNode.isEmpty()) {
            throw new RuntimeException("Yahoo Finance returned no data.");
        }

        JsonNode data = resultNode.get(0);

        JsonNode timestamps = data.path("timestamp");
        JsonNode indicators = data.path("indicators").path("quote").get(0);

        JsonNode opens = indicators.path("open");
        JsonNode highs = indicators.path("high");
        JsonNode lows = indicators.path("low");
        JsonNode closes = indicators.path("close");
        JsonNode volumes = indicators.path("volume");

        for (int i = 0; i < timestamps.size(); i++) {

            if (opens.get(i).isNull()) continue;

            LocalDate date = Instant.ofEpochSecond(timestamps.get(i).asLong())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            if (repository.existsByStockAndDate(stock, date)) {
                continue;
            }

            HistoricalPrice price = new HistoricalPrice();
            price.setStock(stock);
            price.setDate(date);
            price.setOpenPrice(BigDecimal.valueOf(opens.get(i).asDouble()));
            price.setHighPrice(BigDecimal.valueOf(highs.get(i).asDouble()));
            price.setLowPrice(BigDecimal.valueOf(lows.get(i).asDouble()));
            price.setClosePrice(BigDecimal.valueOf(closes.get(i).asDouble()));
            price.setVolume(volumes.get(i).asLong());

            repository.save(price);
        }
    }
}
