package com.money.remainder.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Instant;

@Entity
@Table(name = "investments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Investment {

    @Id
    @Column(length = 64)
    private String id;

    @Column(name = "user_id", nullable = false, length = 64)
    private String userId;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, length = 50)
    private String category; // MUTUAL_FUNDS, STOCKS, FIXED_DEPOSIT, GOLD, REAL_ESTATE, CRYPTO, OTHER

    @Column(name = "investment_type", nullable = false, length = 20)
    @Builder.Default
    private String investmentType = "LUMPSUM"; // LUMPSUM, SIP

    @Column(name = "invested_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal investedAmount;

    @Column(name = "current_value", nullable = false, precision = 15, scale = 2)
    private BigDecimal currentValue;

    @Column(name = "expected_return_rate", precision = 5, scale = 2)
    @Builder.Default
    private BigDecimal expectedReturnRate = BigDecimal.ZERO;

    @Column(name = "sip_amount", precision = 15, scale = 2)
    private BigDecimal sipAmount;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "maturity_date")
    private LocalDate maturityDate;

    @Column(name = "risk_level", length = 20)
    @Builder.Default
    private String riskLevel = "MODERATE"; // LOW, MODERATE, HIGH, VERY_HIGH

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(nullable = false, length = 20)
    @Builder.Default
    private String status = "ACTIVE"; // ACTIVE, MATURED, REDEEMED, DELETED

    @Column(name = "created_at")
    @Builder.Default
    private Instant createdAt = Instant.now();
}
