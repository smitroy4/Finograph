package com.finograph.service;

import com.finograph.entity.Stock;
import com.finograph.entity.User;
import com.finograph.entity.Watchlist;
import com.finograph.repository.WatchlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WatchlistService {

    @Autowired
    private WatchlistRepository watchlistRepository;

    public Watchlist add(User user, Stock stock){
        Watchlist watchlist = new Watchlist();
        watchlist.setUser(user);
        watchlist.setStock(stock);
        return watchlistRepository.save(watchlist);
    }

}
