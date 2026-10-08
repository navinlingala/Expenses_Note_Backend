package com.money.remainder.controller;

import com.money.remainder.entity.GoldAsset;
import com.money.remainder.repository.GoldAssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/gold-assets")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class GoldAssetController {

    private final GoldAssetRepository goldAssetRepository;

    @GetMapping
    public ResponseEntity<List<GoldAsset>> getGoldAssets(
            @RequestParam String userId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String goldType) {
        if (status != null && !status.trim().isEmpty()) {
            return ResponseEntity.ok(goldAssetRepository.findByUserIdAndStatusOrderByCreatedAtDesc(userId, status));
        }
        if (goldType != null && !goldType.trim().isEmpty()) {
            return ResponseEntity.ok(goldAssetRepository.findByUserIdAndGoldTypeOrderByCreatedAtDesc(userId, goldType));
        }
        return ResponseEntity.ok(goldAssetRepository.findByUserIdOrderByCreatedAtDesc(userId));
    }

    @PostMapping
    public ResponseEntity<GoldAsset> createGoldAsset(@RequestBody GoldAsset goldAsset) {
        if (goldAsset.getId() == null || goldAsset.getId().isEmpty()) {
            goldAsset.setId(UUID.randomUUID().toString());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(goldAssetRepository.save(goldAsset));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GoldAsset> updateGoldAsset(@PathVariable String id, @RequestBody GoldAsset goldAsset) {
        goldAsset.setId(id);
        return ResponseEntity.ok(goldAssetRepository.save(goldAsset));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGoldAsset(@PathVariable String id) {
        goldAssetRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Gold asset deleted successfully."));
    }
}
