package com.money.remainder.controller;

import com.money.remainder.entity.Transaction;
import com.money.remainder.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TransactionController {

    private final TransactionRepository transactionRepository;

    @GetMapping
    public ResponseEntity<List<Transaction>> getTransactions(@RequestParam String userId, @RequestParam(required = false) String type) {
        if (type != null && !type.trim().isEmpty()) {
            return ResponseEntity.ok(transactionRepository.findByUserIdAndTypeOrderByDueDateAsc(userId, type));
        }
        return ResponseEntity.ok(transactionRepository.findByUserIdOrderByDueDateAsc(userId));
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        if (transaction.getId() == null || transaction.getId().isEmpty()) {
            transaction.setId(UUID.randomUUID().toString());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionRepository.save(transaction));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable String id, @RequestBody Transaction transaction) {
        transaction.setId(id);
        return ResponseEntity.ok(transactionRepository.save(transaction));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable String id, @RequestBody Map<String, String> body) {
        Optional<Transaction> optional = transactionRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Transaction tx = optional.get();
        if (body.containsKey("status")) {
            tx.setStatus(body.get("status"));
        }
        return ResponseEntity.ok(transactionRepository.save(tx));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable String id) {
        transactionRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Transaction deleted successfully."));
    }
}