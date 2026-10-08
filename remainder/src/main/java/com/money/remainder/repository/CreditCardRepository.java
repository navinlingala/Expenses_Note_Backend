package com.money.remainder.repository;

import com.money.remainder.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, String> {
    List<CreditCard> findByUserIdOrderByCreatedAtDesc(String userId);
    List<CreditCard> findByUserIdAndStatusOrderByCreatedAtDesc(String userId, String status);
}
