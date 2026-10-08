package com.money.remainder.repository;

import com.money.remainder.entity.CreditCardTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditCardTransactionRepository extends JpaRepository<CreditCardTransaction, String> {
    List<CreditCardTransaction> findByUserIdOrderByTransactionDateDesc(String userId);
    List<CreditCardTransaction> findByCardIdOrderByTransactionDateDesc(String cardId);
    List<CreditCardTransaction> findByUserIdAndCardIdOrderByTransactionDateDesc(String userId, String cardId);
}
