package com.money.remainder.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "vault_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VaultItem {

    @Id
    @Column(length = 64)
    private String id;

    @Column(name = "user_id", nullable = false, length = 64)
    private String userId;

    @Column(name = "item_type", nullable = false, length = 50)
    @Builder.Default
    private String itemType = "CARD"; // CARD, BANK_ACCOUNT, PASSWORD_PIN, SECRET_NOTE

    @Column(nullable = false, length = 150)
    private String title;

    @Column(length = 150)
    private String subtitle;

    @Column(name = "account_or_card_number", length = 100)
    private String accountOrCardNumber;

    @Column(name = "holder_name", length = 120)
    private String holderName;

    @Column(name = "expiry_date", length = 20)
    private String expiryDate;

    @Column(length = 20)
    private String cvv;

    @Column(length = 50)
    private String pin;

    @Column(length = 255)
    private String password;

    @Column(name = "ifsc_code", length = 50)
    private String ifscCode;

    @Column(name = "upi_id", length = 100)
    private String upiId;

    @Column(name = "url_or_app", length = 255)
    private String urlOrApp;

    @Column(name = "secret_content", columnDefinition = "TEXT")
    private String secretContent;

    @Column(name = "color_theme", length = 50)
    @Builder.Default
    private String colorTheme = "OBSIDIAN";

    @Column(length = 50)
    @Builder.Default
    private String category = "GENERAL";

    @Column(name = "is_favorite", nullable = false)
    @Builder.Default
    private Boolean isFavorite = false;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at")
    @Builder.Default
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    @Builder.Default
    private Instant updatedAt = Instant.now();
}
