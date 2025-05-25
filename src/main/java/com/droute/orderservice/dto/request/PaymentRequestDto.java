package com.droute.orderservice.dto.request;

import com.droute.orderservice.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDto {
    private Long orderId;
    private double amount;
    private String paymentMethod;
    private String transactionId;
    private PaymentStatus status;
}
