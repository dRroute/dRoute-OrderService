package com.droute.orderservice.dto.response;


import java.time.LocalDateTime;

import com.droute.orderservice.enums.CourierStatus;
import com.droute.orderservice.enums.DimensionUnit;
import com.droute.orderservice.enums.WeightUnit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CourierDetailResponseDto {
    private Long courierId;
    private Long userId;
    private UserEntityResponseDto user;
    private String courierSourceAddress;
    private String courierSourceCoordinate;
    private String courierDestinationAddress;
    private String courierDestinationCoordinate;
    private Double courierHeight;
    private Double courierWidth;
    private Double courierLength;
    private DimensionUnit courierDimensionUnit;
    private Double courierWeight;
    private WeightUnit courierWeightUnit;
    private Double courierValue;
    private CourierStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
