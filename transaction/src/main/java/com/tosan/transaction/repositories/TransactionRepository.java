package com.tosan.transaction.repositories;

import com.tosan.transaction.data.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "SELECT * FROM Transaction WHERE sourceDepositNumber = :depositNumber" +
            " OR destinationDepositNumber = :depositNumber",
            nativeQuery = true)
    List<Transaction> findAllByDepositNumber(@Param("depositNumber") Long depositNumber);

    @Query(value = "SELECT t FROM Transaction t WHERE t.trackingNumber = :trackingNumber")
    Transaction findByTrackingNumber(@Param("trackingNumber") Long trackingNumber);
}
