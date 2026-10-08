package com.money.remainder.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Instant;

@Entity
@Table(name = "child_investments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChildInvestment {

    @Id
    @Column(length = 64)
    private String id;

    @Column(name = "child_id", nullable = false, length = 64)
    private String childId;

    @Column(name = "user_id", nullable = false, length = 64)
    private String userId;

    @Column(name = "investment_name", nullable = false, length = 200)
    private String investmentName;

    @Column(name = "investment_type", nullable = false, length = 50)
    private String investmentType; // SSY, MUTUAL_FUND_SIP, PPF, FD, INSURANCE, GOLD, OTHER

    @Column(name = "invested_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal investedAmount;

    @Column(name = "current_valuation", nullable = false, precision = 15, scale = 2)
    private BigDecimal currentValuation;

    @Column(name = "expected_return_rate", precision = 5, scale = 2)
    @Builder.Default
    private BigDecimal expectedReturnRate = BigDecimal.valueOf(8.20);

    @Column(name = "monthly_contribution", precision = 15, scale = 2)
    @Builder.Default
    private BigDecimal monthlyContribution = BigDecimal.ZERO;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "maturity_date")
    private LocalDate maturityDate;

    @Column(name = "account_number_or_folio", length = 100)
    private String accountNumberOrFolio;

    @Column(name = "linked_goal_id", length = 64)
    private String linkedGoalId;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(nullable = false, length = 20)
    @Builder.Default
    private String status = "ACTIVE"; // ACTIVE, MATURED, CLOSED

    @Column(name = "created_at")
    @Builder.Default
    private Instant createdAt = Instant.now();
}
