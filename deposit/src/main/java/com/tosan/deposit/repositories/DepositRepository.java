package com.tosan.deposit.repositories;

import com.tosan.deposit.data.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, Long> {

    @Query(value = "SELECT * FROM Deposit WHERE customer_id = :customerId",
            nativeQuery = true)
    List<Deposit> findAllByCustomerId(@Param("customerId") Long customerId);

    @Query(value = "SELECT d FROM Deposit d WHERE d.depositNumber = :depositNumber")
    Deposit findByDepositNumber(@Param("depositNumber") Long depositNumber);
}
