package com.money.remainder.controller;

import com.money.remainder.entity.ChildInvestment;
import com.money.remainder.repository.ChildInvestmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/child-investments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ChildInvestmentController {

    private final ChildInvestmentRepository childInvestmentRepository;

    @GetMapping
    public ResponseEntity<List<ChildInvestment>> getChildInvestments(
            @RequestParam String userId,
            @RequestParam(required = false) String childId,
            @RequestParam(required = false) String status) {
        if (childId != null && !childId.trim().isEmpty()) {
            return ResponseEntity.ok(childInvestmentRepository.findByUserIdAndChildIdOrderByCreatedAtDesc(userId, childId));
        }
        if (status != null && !status.trim().isEmpty()) {
            return ResponseEntity.ok(childInvestmentRepository.findByUserIdAndStatusOrderByCreatedAtDesc(userId, status));
        }
        return ResponseEntity.ok(childInvestmentRepository.findByUserIdOrderByCreatedAtDesc(userId));
    }

    @PostMapping
    public ResponseEntity<ChildInvestment> createChildInvestment(@RequestBody ChildInvestment childInvestment) {
        if (childInvestment.getId() == null || childInvestment.getId().isEmpty()) {
            childInvestment.setId(UUID.randomUUID().toString());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(childInvestmentRepository.save(childInvestment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChildInvestment> updateChildInvestment(@PathVariable String id, @RequestBody ChildInvestment childInvestment) {
        childInvestment.setId(id);
        return ResponseEntity.ok(childInvestmentRepository.save(childInvestment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChildInvestment(@PathVariable String id) {
        childInvestmentRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Child investment deleted successfully."));
    }
}
