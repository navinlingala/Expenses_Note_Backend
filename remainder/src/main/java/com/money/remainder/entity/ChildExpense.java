package com.money.remainder.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Instant;

@Entity
@Table(name = "child_expenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChildExpense {

    @Id
    @Column(length = 64)
    private String id;

    @Column(name = "child_id", nullable = false, length = 64)
    private String childId;

    @Column(name = "user_id", nullable = false, length = 64)
    private String userId;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 50)
    private String category; // EDUCATION, HEALTHCARE, CHILDCARE, EVENTS, ALLOWANCE, OTHERS

    @Column(name = "expense_date", nullable = false)
    private LocalDate expenseDate;

    @Column(name = "payment_mode", length = 50)
    @Builder.Default
    private String paymentMode = "UPI"; // UPI, CASH, CARD, NET_BANKING

    @Column(name = "is_recurring")
    @Builder.Default
    private Boolean isRecurring = false;

    @Column(name = "recurrence_frequency", length = 30)
    @Builder.Default
    private String recurrenceFrequency = "NONE"; // NONE, MONTHLY, QUARTERLY, YEARLY

    @Column(name = "receipt_url", length = 500)
    private String receiptUrl;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at")
    @Builder.Default
    private Instant createdAt = Instant.now();
}
