package com.money.remainder.controller;

import com.money.remainder.entity.ChildFutureGoal;
import com.money.remainder.repository.ChildFutureGoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/child-goals")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ChildFutureGoalController {

    private final ChildFutureGoalRepository childFutureGoalRepository;

    @GetMapping
    public ResponseEntity<List<ChildFutureGoal>> getChildGoals(
            @RequestParam String userId,
            @RequestParam(required = false) String childId) {
        if (childId != null && !childId.trim().isEmpty()) {
            return ResponseEntity.ok(childFutureGoalRepository.findByUserIdAndChildIdOrderByTargetYearAsc(userId, childId));
        }
        return ResponseEntity.ok(childFutureGoalRepository.findByUserIdOrderByTargetYearAsc(userId));
    }

    @PostMapping
    public ResponseEntity<ChildFutureGoal> createChildGoal(@RequestBody ChildFutureGoal childFutureGoal) {
        if (childFutureGoal.getId() == null || childFutureGoal.getId().isEmpty()) {
            childFutureGoal.setId(UUID.randomUUID().toString());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(childFutureGoalRepository.save(childFutureGoal));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChildFutureGoal> updateChildGoal(@PathVariable String id, @RequestBody ChildFutureGoal childFutureGoal) {
        childFutureGoal.setId(id);
        return ResponseEntity.ok(childFutureGoalRepository.save(childFutureGoal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChildGoal(@PathVariable String id) {
        childFutureGoalRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Child future goal deleted successfully."));
    }
}
