package com.money.remainder.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Instant;

@Entity
@Table(name = "loans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {

    @Id
    @Column(length = 64)
    private String id;

    @Column(name = "user_id", nullable = false, length = 64)
    private String userId;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(name = "lender_name", nullable = false, length = 150)
    private String lenderName;

    @Column(name = "total_principal", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalPrincipal;

    @Column(name = "emi_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal emiAmount;

    @Column(name = "interest_rate", precision = 5, scale = 2)
    @Builder.Default
    private BigDecimal interestRate = BigDecimal.ZERO;

    @Column(name = "total_emis", nullable = false)
    private Integer totalEmis;

    @Column(name = "remaining_emis", nullable = false)
    private Integer remainingEmis;

    @Column(name = "due_day", nullable = false)
    private Integer dueDay;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "reminder_offsets", columnDefinition = "TEXT")
    @Builder.Default
    private String reminderOffsets = "[7, 2, 1, 0]";

    @Column(nullable = false, length = 20)
    @Builder.Default
    private String status = "ACTIVE"; // ACTIVE, COMPLETED, ARCHIVED

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at")
    @Builder.Default
    private Instant createdAt = Instant.now();
}
