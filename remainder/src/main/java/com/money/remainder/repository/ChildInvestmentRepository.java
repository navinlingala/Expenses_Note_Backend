package com.money.remainder.repository;

import com.money.remainder.entity.ChildInvestment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildInvestmentRepository extends JpaRepository<ChildInvestment, String> {
    List<ChildInvestment> findByUserIdOrderByCreatedAtDesc(String userId);
    List<ChildInvestment> findByUserIdAndChildIdOrderByCreatedAtDesc(String userId, String childId);
    List<ChildInvestment> findByUserIdAndStatusOrderByCreatedAtDesc(String userId, String status);
}
