package com.money.remainder.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "child_future_goals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChildFutureGoal {

    @Id
    @Column(length = 64)
    private String id;

    @Column(name = "child_id", nullable = false, length = 64)
    private String childId;

    @Column(name = "user_id", nullable = false, length = 64)
    private String userId;

    @Column(name = "goal_title", nullable = false, length = 200)
    private String goalTitle;

    @Column(name = "target_amount_today", nullable = false, precision = 15, scale = 2)
    private BigDecimal targetAmountToday;

    @Column(name = "estimated_inflation_rate", precision = 5, scale = 2)
    @Builder.Default
    private BigDecimal estimatedInflationRate = BigDecimal.valueOf(8.00);

    @Column(name = "target_year", nullable = false)
    private Integer targetYear;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at")
    @Builder.Default
    private Instant createdAt = Instant.now();
}
