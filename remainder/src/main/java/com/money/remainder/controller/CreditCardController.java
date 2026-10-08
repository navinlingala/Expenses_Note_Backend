package com.money.remainder.controller;

import com.money.remainder.entity.CreditCard;
import com.money.remainder.repository.CreditCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/credit-cards")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CreditCardController {

    private final CreditCardRepository creditCardRepository;

    @GetMapping
    public ResponseEntity<List<CreditCard>> getCreditCards(
            @RequestParam String userId,
            @RequestParam(required = false) String status) {
        if (status != null && !status.trim().isEmpty()) {
            return ResponseEntity.ok(creditCardRepository.findByUserIdAndStatusOrderByCreatedAtDesc(userId, status));
        }
        return ResponseEntity.ok(creditCardRepository.findByUserIdOrderByCreatedAtDesc(userId));
    }

    @PostMapping
    public ResponseEntity<CreditCard> createCreditCard(@RequestBody CreditCard card) {
        if (card.getId() == null || card.getId().isEmpty()) {
            card.setId(UUID.randomUUID().toString());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(creditCardRepository.save(card));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreditCard> updateCreditCard(@PathVariable String id, @RequestBody CreditCard card) {
        card.setId(id);
        return ResponseEntity.ok(creditCardRepository.save(card));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCreditCard(@PathVariable String id) {
        creditCardRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Credit card deleted successfully."));
    }
}
