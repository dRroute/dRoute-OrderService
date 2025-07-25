package com.droute.orderservice.dto.response;

import com.droute.orderservice.entity.Order;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailsResponseDto {
    private FilteredJourneyDetailsResponseDto journeyDetails;
    private Order order;
    private CourierDetailResponseDto courier;


}