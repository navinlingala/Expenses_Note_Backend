package com.money.remainder.controller;

import com.money.remainder.entity.CreditCard;
import com.money.remainder.entity.CreditCardTransaction;
import com.money.remainder.repository.CreditCardRepository;
import com.money.remainder.repository.CreditCardTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/credit-card-transactions")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CreditCardTransactionController {

    private final CreditCardTransactionRepository transactionRepository;
    private final CreditCardRepository creditCardRepository;

    @GetMapping
    public ResponseEntity<List<CreditCardTransaction>> getTransactions(
            @RequestParam String userId,
            @RequestParam(required = false) String cardId) {
        if (cardId != null && !cardId.trim().isEmpty()) {
            return ResponseEntity.ok(transactionRepository.findByUserIdAndCardIdOrderByTransactionDateDesc(userId, cardId));
        }
        return ResponseEntity.ok(transactionRepository.findByUserIdOrderByTransactionDateDesc(userId));
    }

    @PostMapping
    public ResponseEntity<CreditCardTransaction> createTransaction(@RequestBody CreditCardTransaction tx) {
        if (tx.getId() == null || tx.getId().isEmpty()) {
            tx.setId(UUID.randomUUID().toString());
        }
        CreditCardTransaction saved = transactionRepository.save(tx);

        // Update card outstanding & available limit
        Optional<CreditCard> cardOpt = creditCardRepository.findById(tx.getCardId());
        if (cardOpt.isPresent()) {
            CreditCard card = cardOpt.get();
            BigDecimal amount = tx.getAmount() != null ? tx.getAmount() : BigDecimal.ZERO;
            if ("PAYMENT".equalsIgnoreCase(tx.getTransactionType()) || "REFUND".equalsIgnoreCase(tx.getTransactionType())) {
                BigDecimal newOutstanding = card.getCurrentOutstanding().subtract(amount);
                if (newOutstanding.compareTo(BigDecimal.ZERO) < 0) newOutstanding = BigDecimal.ZERO;
                card.setCurrentOutstanding(newOutstanding);
                card.setAvailableLimit(card.getTotalLimit().subtract(newOutstanding));
            } else {
                BigDecimal newOutstanding = card.getCurrentOutstanding().add(amount);
                card.setCurrentOutstanding(newOutstanding);
                BigDecimal newAvailable = card.getTotalLimit().subtract(newOutstanding);
                card.setAvailableLimit(newAvailable.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : newAvailable);
            }
            creditCardRepository.save(card);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable String id) {
        transactionRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Credit card transaction deleted successfully."));
    }
}
