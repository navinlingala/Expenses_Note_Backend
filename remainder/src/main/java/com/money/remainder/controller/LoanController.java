package com.money.remainder.controller;

import com.money.remainder.entity.Loan;
import com.money.remainder.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LoanController {

    private final LoanRepository loanRepository;

    @GetMapping
    public ResponseEntity<List<Loan>> getLoans(@RequestParam String userId, @RequestParam(required = false) String status) {
        if (status != null && !status.trim().isEmpty()) {
            return ResponseEntity.ok(loanRepository.findByUserIdAndStatusOrderByDueDayAsc(userId, status));
        }
        return ResponseEntity.ok(loanRepository.findByUserIdOrderByDueDayAsc(userId));
    }

    @PostMapping
    public ResponseEntity<Loan> createLoan(@RequestBody Loan loan) {
        if (loan.getId() == null || loan.getId().isEmpty()) {
            loan.setId(UUID.randomUUID().toString());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(loanRepository.save(loan));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Loan> updateLoan(@PathVariable String id, @RequestBody Loan loan) {
        loan.setId(id);
        return ResponseEntity.ok(loanRepository.save(loan));
    }

    @PutMapping("/{id}/pay-emi")
    public ResponseEntity<?> payEmi(@PathVariable String id) {
        Optional<Loan> optional = loanRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Loan loan = optional.get();
        if (loan.getRemainingEmis() > 0) {
            loan.setRemainingEmis(loan.getRemainingEmis() - 1);
            if (loan.getRemainingEmis() == 0) {
                loan.setStatus("COMPLETED");
            }
            return ResponseEntity.ok(loanRepository.save(loan));
        }
        return ResponseEntity.badRequest().body(Map.of("message", "All EMIs already paid."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLoan(@PathVariable String id) {
        loanRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Loan deleted successfully."));
    }
}