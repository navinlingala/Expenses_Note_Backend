package com.money.remainder.controller;

import com.money.remainder.entity.ChildProfile;
import com.money.remainder.repository.ChildProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/child-profiles")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ChildProfileController {

    private final ChildProfileRepository childProfileRepository;

    @GetMapping
    public ResponseEntity<List<ChildProfile>> getChildProfiles(@RequestParam String userId) {
        return ResponseEntity.ok(childProfileRepository.findByUserIdOrderByCreatedAtDesc(userId));
    }

    @PostMapping
    public ResponseEntity<ChildProfile> createChildProfile(@RequestBody ChildProfile childProfile) {
        if (childProfile.getId() == null || childProfile.getId().isEmpty()) {
            childProfile.setId(UUID.randomUUID().toString());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(childProfileRepository.save(childProfile));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChildProfile> updateChildProfile(@PathVariable String id, @RequestBody ChildProfile childProfile) {
        childProfile.setId(id);
        return ResponseEntity.ok(childProfileRepository.save(childProfile));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChildProfile(@PathVariable String id) {
        childProfileRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Child profile deleted successfully."));
    }
}
