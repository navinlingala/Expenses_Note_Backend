package com.money.remainder.repository;

import com.money.remainder.entity.GoldAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoldAssetRepository extends JpaRepository<GoldAsset, String> {
    List<GoldAsset> findByUserIdOrderByCreatedAtDesc(String userId);
    List<GoldAsset> findByUserIdAndStatusOrderByCreatedAtDesc(String userId, String status);
    List<GoldAsset> findByUserIdAndGoldTypeOrderByCreatedAtDesc(String userId, String goldType);
}
