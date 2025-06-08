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
public class NewOrderRequestDto {
    private Long courierId;
    private Long journeyId;
    private OrderStatus orderStatus;
    private Double estimatedFare;
    private Double offeredFare;

    private Long userId;
    private Long driverId;

    // Below details taken when order is placed
    private String senderName;
    private String senderContactNo;
    private String senderLandmarkAddress;
    private String recieverName;
    private String recieverContactNo;
    private String recieverLandmarkAddress;

    private String courierImageUrl1;
    private String courierImageUrl2;
    private PaymentRequestDto paymentRequestDto;
}
