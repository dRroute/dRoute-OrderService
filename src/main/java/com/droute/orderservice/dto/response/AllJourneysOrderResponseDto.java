package com.droute.orderservice.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllJourneysOrderResponseDto {

    private JourneyDetailResponseDto journeyDetails;
    private List<OrderDetailsResponseDto> orderDetails;
    
}



