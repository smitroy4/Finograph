package com.finograph.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "historical_prices",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"stock_id", "price_date"})
        }
)
public class HistoricalPrice {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name="stock_id", nullable = false)
    private Stock stock;

    @JoinColumn(name="price_date", nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private BigDecimal highPrice;

    @Column(nullable = false)
    private BigDecimal lowPrice;

    @Column(nullable = false)
    private BigDecimal closePrice;

    @Column(nullable = false)
    private Long volume;


    public void setOpenPrice(BigDecimal bigDecimal) {
    }
}
