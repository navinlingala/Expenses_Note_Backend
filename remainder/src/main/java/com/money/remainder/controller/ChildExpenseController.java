package com.money.remainder.controller;

import com.money.remainder.entity.ChildExpense;
import com.money.remainder.repository.ChildExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/child-expenses")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ChildExpenseController {

    private final ChildExpenseRepository childExpenseRepository;

    @GetMapping
    public ResponseEntity<List<ChildExpense>> getChildExpenses(
            @RequestParam String userId,
            @RequestParam(required = false) String childId,
            @RequestParam(required = false) String category) {
        if (childId != null && !childId.trim().isEmpty()) {
            return ResponseEntity.ok(childExpenseRepository.findByUserIdAndChildIdOrderByExpenseDateDesc(userId, childId));
        }
        if (category != null && !category.trim().isEmpty()) {
            return ResponseEntity.ok(childExpenseRepository.findByUserIdAndCategoryOrderByExpenseDateDesc(userId, category));
        }
        return ResponseEntity.ok(childExpenseRepository.findByUserIdOrderByExpenseDateDesc(userId));
    }

    @PostMapping
    public ResponseEntity<ChildExpense> createChildExpense(@RequestBody ChildExpense childExpense) {
        if (childExpense.getId() == null || childExpense.getId().isEmpty()) {
            childExpense.setId(UUID.randomUUID().toString());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(childExpenseRepository.save(childExpense));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChildExpense> updateChildExpense(@PathVariable String id, @RequestBody ChildExpense childExpense) {
        childExpense.setId(id);
        return ResponseEntity.ok(childExpenseRepository.save(childExpense));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChildExpense(@PathVariable String id) {
        childExpenseRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Child expense deleted successfully."));
    }
}
