package com.money.remainder.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "credit_cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditCard {

    @Id
    @Column(length = 64)
    private String id;

    @Column(name = "user_id", nullable = false, length = 64)
    private String userId;

    @Column(name = "card_name", nullable = false, length = 120)
    private String cardName;

    @Column(name = "bank_name", nullable = false, length = 100)
    private String bankName;

    @Column(name = "card_network", nullable = false, length = 50)
    @Builder.Default
    private String cardNetwork = "VISA"; // VISA, MASTERCARD, RUPAY, AMEX, DINERS

    @Column(name = "card_number", length = 50)
    private String cardNumber;

    @Column(name = "card_holder_name", length = 100)
    private String cardHolderName;

    @Column(name = "expiry_date", length = 10)
    private String expiryDate;

    @Column(name = "cvv", length = 10)
    private String cvv;

    @Column(name = "card_pin", length = 10)
    private String cardPin;

    @Column(name = "last4_digits", length = 10)
    private String last4Digits;

    @Column(name = "total_limit", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalLimit;

    @Column(name = "available_limit", nullable = false, precision = 15, scale = 2)
    private BigDecimal availableLimit;

    @Column(name = "current_outstanding", nullable = false, precision = 15, scale = 2)
    @Builder.Default
    private BigDecimal currentOutstanding = BigDecimal.ZERO;

    @Column(name = "statement_day", nullable = false)
    @Builder.Default
    private Integer statementDay = 15; // 1 - 31

    @Column(name = "due_day", nullable = false)
    @Builder.Default
    private Integer dueDay = 5; // 1 - 31

    @Column(name = "color_theme", length = 50)
    @Builder.Default
    private String colorTheme = "BLUE_PURPLE";

    @Column(name = "reminder_enabled", nullable = false)
    @Builder.Default
    private Boolean reminderEnabled = true;

    @Column(name = "interest_free_days")
    @Builder.Default
    private Integer interestFreeDays = 50;

    @Column(nullable = false, length = 20)
    @Builder.Default
    private String status = "ACTIVE"; // ACTIVE, BLOCKED, CLOSED

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at")
    @Builder.Default
    private Instant createdAt = Instant.now();
}
