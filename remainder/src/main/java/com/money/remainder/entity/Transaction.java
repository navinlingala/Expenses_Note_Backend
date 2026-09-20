package com.money.remainder.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @Column(length = 64)
    private String id;

    @Column(name = "user_id", nullable = false, length = 64)
    private String userId;

    @Column(nullable = false, length = 180)
    private String title;

    @Column(name = "person_name", length = 120)
    private String personName;

    @Column(name = "phone_number", length = 30)
    private String phoneNumber;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 20)
    private String type; // CREDIT, DEBIT, RECEIVABLE, PAYABLE, EMI

    @Column(name = "due_date", nullable = false)
    private Instant dueDate;

    @Column(nullable = false, length = 20)
    @Builder.Default
    private String status = "PENDING"; // PENDING, PAID, RECEIVED, OVERDUE, CANCELLED

    @Column(name = "is_recurring")
    @Builder.Default
    private Boolean isRecurring = false;

    @Column(name = "recurrence_frequency", length = 20)
    @Builder.Default
    private String recurrenceFrequency = "NONE";

    @Column(name = "loan_id", length = 64)
    private String loanId;

    @Column(length = 60)
    private String category;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at")
    @Builder.Default
    private Instant createdAt = Instant.now();
}
