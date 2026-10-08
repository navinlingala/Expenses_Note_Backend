package com.money.remainder.repository;

import com.money.remainder.entity.VaultItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaultItemRepository extends JpaRepository<VaultItem, String> {
    List<VaultItem> findByUserIdOrderByCreatedAtDesc(String userId);
    List<VaultItem> findByUserIdAndItemTypeOrderByCreatedAtDesc(String userId, String itemType);
}
