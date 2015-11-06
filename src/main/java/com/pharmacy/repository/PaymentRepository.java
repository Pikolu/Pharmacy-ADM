package com.pharmacy.repository;

import com.pharmacy.domain.Payment;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Payment entity.
 */
public interface PaymentRepository extends JpaRepository<Payment,Long> {

}
