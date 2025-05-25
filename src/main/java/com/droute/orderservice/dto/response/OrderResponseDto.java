package com.droute.orderservice.dto.response;

import com.droute.orderservice.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    private Long id;
    private Long courierId;
    private Long journeyId;
    private OrderStatus orderStatus;
    private Double estimatedFare;
    private Double offeredFare;
    private PaymentResponseDto payment;
}
