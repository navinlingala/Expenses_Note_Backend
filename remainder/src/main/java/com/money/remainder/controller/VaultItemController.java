package com.money.remainder.controller;

import com.money.remainder.entity.VaultItem;
import com.money.remainder.repository.VaultItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/vault")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VaultItemController {

    private final VaultItemRepository vaultItemRepository;

    @GetMapping
    public ResponseEntity<List<VaultItem>> getVaultItems(
            @RequestParam String userId,
            @RequestParam(required = false) String itemType) {
        if (itemType != null && !itemType.trim().isEmpty() && !itemType.equalsIgnoreCase("ALL")) {
            return ResponseEntity.ok(vaultItemRepository.findByUserIdAndItemTypeOrderByCreatedAtDesc(userId, itemType.toUpperCase()));
        }
        return ResponseEntity.ok(vaultItemRepository.findByUserIdOrderByCreatedAtDesc(userId));
    }

    @PostMapping
    public ResponseEntity<VaultItem> createVaultItem(@RequestBody VaultItem item) {
        if (item.getId() == null || item.getId().isEmpty()) {
            item.setId(UUID.randomUUID().toString());
        }
        item.setCreatedAt(Instant.now());
        item.setUpdatedAt(Instant.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(vaultItemRepository.save(item));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VaultItem> updateVaultItem(@PathVariable String id, @RequestBody VaultItem item) {
        item.setId(id);
        item.setUpdatedAt(Instant.now());
        return ResponseEntity.ok(vaultItemRepository.save(item));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVaultItem(@PathVariable String id) {
        vaultItemRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Vault item deleted successfully."));
    }
}
