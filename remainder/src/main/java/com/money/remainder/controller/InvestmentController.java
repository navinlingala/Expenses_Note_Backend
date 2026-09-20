package com.money.remainder.controller;

import com.money.remainder.entity.Investment;
import com.money.remainder.repository.InvestmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/investments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class InvestmentController {

    private final InvestmentRepository investmentRepository;

    @GetMapping
    public ResponseEntity<List<Investment>> getInvestments(
            @RequestParam String userId,
            @RequestParam(required = false) String status) {
        if (status != null && !status.trim().isEmpty()) {
            return ResponseEntity.ok(investmentRepository.findByUserIdAndStatusOrderByCreatedAtDesc(userId, status));
        }
        return ResponseEntity.ok(investmentRepository.findByUserIdOrderByCreatedAtDesc(userId));
    }

    @PostMapping
    public ResponseEntity<Investment> createInvestment(@RequestBody Investment investment) {
        if (investment.getId() == null || investment.getId().isEmpty()) {
            investment.setId(UUID.randomUUID().toString());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(investmentRepository.save(investment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Investment> updateInvestment(@PathVariable String id, @RequestBody Investment investment) {
        investment.setId(id);
        return ResponseEntity.ok(investmentRepository.save(investment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInvestment(@PathVariable String id) {
        investmentRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Investment deleted permanently."));
    }
}
