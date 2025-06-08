package com.droute.orderservice.repository;

import com.droute.orderservice.entity.Order;
import com.droute.orderservice.entity.Payment;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // Custom query methods can be added here
    // Example:
    // Payment findByTransactionId(String transactionId);
    Optional<Payment> findByOrder(Order order);
}