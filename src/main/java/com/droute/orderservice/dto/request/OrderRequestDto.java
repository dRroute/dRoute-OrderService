package com.droute.orderservice.dto.request;

import com.droute.orderservice.enums.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
    private Long courierId;
    private Long journeyId;
    private OrderStatus orderStatus;
    private Double estimatedFare;
    private Double offeredFare;
    private PaymentRequestDto paymentRequestDto;
}