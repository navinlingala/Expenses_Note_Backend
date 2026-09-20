package com.money.remainder.repository;

import com.money.remainder.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findByUserIdOrderByDueDateAsc(String userId);
    List<Transaction> findByUserIdAndTypeOrderByDueDateAsc(String userId, String type);
    List<Transaction> findByUserIdAndTypeInOrderByDueDateAsc(String userId, List<String> types);
    List<Transaction> findByUserIdAndDueDateBetweenOrderByDueDateAsc(String userId, Instant start, Instant end);
}
