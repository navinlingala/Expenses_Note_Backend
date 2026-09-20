package com.money.remainder.repository;

import com.money.remainder.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, String> {
    List<Loan> findByUserIdOrderByDueDayAsc(String userId);
    List<Loan> findByUserIdAndStatusOrderByDueDayAsc(String userId, String status);
}
