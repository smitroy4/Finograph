package com.finograph.service.external;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class YahooFinanceClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public String fetchChartData(String symbol) {

        String yahooSymbol = symbol + ".NS";
        String url = "https://query1.finance.yahoo.com/v8/finance/chart/"
                + yahooSymbol
                + "?interval=1d&range=1y";

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 "
                        + "(KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
        headers.set("Accept", "application/json");
        headers.set("Connection", "keep-alive");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        return response.getBody();
    }
}
