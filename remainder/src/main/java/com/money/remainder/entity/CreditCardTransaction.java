package com.money.remainder.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Instant;

@Entity
@Table(name = "credit_card_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditCardTransaction {

    @Id
    @Column(length = 64)
    private String id;

    @Column(name = "user_id", nullable = false, length = 64)
    private String userId;

    @Column(name = "card_id", nullable = false, length = 64)
    private String cardId;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(name = "merchant_name", length = 150)
    private String merchantName;

    @Column(nullable = false, length = 50)
    @Builder.Default
    private String category = "SHOPPING"; // SHOPPING, DINING, GROCERIES, FUEL, BILLS, TRAVEL, ENTERTAINMENT, HEALTHCARE, OTHER

    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;

    @Column(name = "transaction_type", nullable = false, length = 30)
    @Builder.Default
    private String transactionType = "EXPENSE"; // EXPENSE, PAYMENT, REFUND

    @Column(name = "is_emi", nullable = false)
    @Builder.Default
    private Boolean isEmi = false;

    @Column(name = "emi_months")
    private Integer emiMonths;

    @Column(name = "monthly_emi_amount", precision = 15, scale = 2)
    private BigDecimal monthlyEmiAmount;

    @Column(name = "is_billed", nullable = false)
    @Builder.Default
    private Boolean isBilled = false;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at")
    @Builder.Default
    private Instant createdAt = Instant.now();
}
