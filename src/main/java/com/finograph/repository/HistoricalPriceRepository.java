package com.finograph.repository;

import com.finograph.entity.HistoricalPrice;
import com.finograph.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface HistoricalPriceRepository extends JpaRepository<HistoricalPrice, UUID> {

    List<HistoricalPrice> findByStockOrderByDateAsc(Stock stock);
    boolean existsByStockAndDate(Stock stock, LocalDate date);

}
