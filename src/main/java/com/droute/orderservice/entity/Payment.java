package com.droute.orderservice.entity;

import com.droute.orderservice.enums.PaymentStatus;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
    
    private double amount;
    private String paymentMethod; // e.g., "CREDIT_CARD", "DEBIT_CARD", "PAYPAL"
    private PaymentStatus status; // e.g., "PENDING", "COMPLETED", "FAILED"
    private String transactionId; // Unique identifier for the payment transaction
}