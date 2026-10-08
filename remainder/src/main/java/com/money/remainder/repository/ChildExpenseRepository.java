package com.money.remainder.repository;

import com.money.remainder.entity.ChildExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildExpenseRepository extends JpaRepository<ChildExpense, String> {
    List<ChildExpense> findByUserIdOrderByExpenseDateDesc(String userId);
    List<ChildExpense> findByUserIdAndChildIdOrderByExpenseDateDesc(String userId, String childId);
    List<ChildExpense> findByUserIdAndCategoryOrderByExpenseDateDesc(String userId, String category);
}
