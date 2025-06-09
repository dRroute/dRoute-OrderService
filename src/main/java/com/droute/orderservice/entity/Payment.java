package com.droute.orderservice.entity;

import com.droute.orderservice.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "order")
@Builder
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;
    
    private double amount;
    private String paymentMethod; // e.g., "CREDIT_CARD", "DEBIT_CARD", "PAYPAL"
    
    @Enumerated(EnumType.STRING)
    private PaymentStatus status; // e.g., "PENDING", "COMPLETED", "FAILED"
    private String transactionId; // Unique identifier for the payment transaction
}