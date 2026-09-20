package com.money.remainder.repository;

import com.money.remainder.entity.Investment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestmentRepository extends JpaRepository<Investment, String> {
    List<Investment> findByUserIdOrderByCreatedAtDesc(String userId);
    List<Investment> findByUserIdAndStatusOrderByCreatedAtDesc(String userId, String status);
}
