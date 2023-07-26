package com.digitalnewagency.task.persistence.repository;


import com.digitalnewagency.task.persistence.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findOneByPaymentId(UUID paymentId);
}
