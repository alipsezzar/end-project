package com.tosan.customer.repositories;

import com.tosan.customer.data.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE c.nationalCode = :nationalCode")
    Customer findCustomerByNationalCode(@Param("nationalCode") Long nationalCode);
}
