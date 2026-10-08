package com.money.remainder.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Instant;

@Entity
@Table(name = "gold_assets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoldAsset {

    @Id
    @Column(length = 64)
    private String id;

    @Column(name = "user_id", nullable = false, length = 64)
    private String userId;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(name = "gold_type", nullable = false, length = 50)
    private String goldType; // JEWELRY, COIN, BAR, SGB, DIGITAL_GOLD, ETF

    @Column(nullable = false, length = 30)
    private String purity; // 24K, 22K_916, 18K_750, 14K_585

    @Column(name = "weight_in_grams", nullable = false, precision = 10, scale = 4)
    private BigDecimal weightInGrams;

    @Column(name = "purchase_price_per_gram", nullable = false, precision = 15, scale = 2)
    private BigDecimal purchasePricePerGram;

    @Column(name = "making_charges", precision = 15, scale = 2)
    @Builder.Default
    private BigDecimal makingCharges = BigDecimal.ZERO;

    @Column(name = "total_invested_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalInvestedAmount;

    @Column(name = "purchase_date", nullable = false)
    private LocalDate purchaseDate;

    @Column(name = "locker_location", length = 150)
    private String lockerLocation;

    @Column(name = "huid_number", length = 80)
    private String huidNumber;

    @Column(name = "jeweler_name", length = 150)
    private String jewelerName;

    @Column(name = "sgb_interest_rate", precision = 5, scale = 2)
    @Builder.Default
    private BigDecimal sgbInterestRate = BigDecimal.valueOf(2.50);

    @Column(name = "maturity_date")
    private LocalDate maturityDate;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(nullable = false, length = 20)
    @Builder.Default
    private String status = "ACTIVE"; // ACTIVE, SOLD, GIFTED, MATURED

    @Column(name = "created_at")
    @Builder.Default
    private Instant createdAt = Instant.now();
}
