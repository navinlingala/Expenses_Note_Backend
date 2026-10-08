package com.money.remainder.repository;

import com.money.remainder.entity.ChildProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildProfileRepository extends JpaRepository<ChildProfile, String> {
    List<ChildProfile> findByUserIdOrderByCreatedAtDesc(String userId);
}
