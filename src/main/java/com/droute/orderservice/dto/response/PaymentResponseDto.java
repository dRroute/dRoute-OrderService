package com.droute.orderservice.dto.response;

import com.droute.orderservice.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDto {
    private String id;
    private Long orderId;
    private double amount;
    private String paymentMethod;
    private PaymentStatus status;
    private String transactionId;
}