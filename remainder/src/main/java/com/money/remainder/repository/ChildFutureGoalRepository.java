package com.money.remainder.repository;

import com.money.remainder.entity.ChildFutureGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildFutureGoalRepository extends JpaRepository<ChildFutureGoal, String> {
    List<ChildFutureGoal> findByUserIdOrderByTargetYearAsc(String userId);
    List<ChildFutureGoal> findByUserIdAndChildIdOrderByTargetYearAsc(String userId, String childId);
}
