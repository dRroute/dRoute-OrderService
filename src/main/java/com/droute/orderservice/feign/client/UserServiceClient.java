package com.droute.orderservice.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.droute.orderservice.config.UserServiceFeignConfig;
import com.droute.orderservice.dto.request.CourierDetailsRequestDto;
import com.droute.orderservice.dto.response.CommonResponseDto;
import com.droute.orderservice.dto.response.CourierDetailResponseDto;
import com.droute.orderservice.dto.response.FilteredJourneyDetailsResponseDto;
import com.droute.orderservice.dto.response.UserEntityResponseDto;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Component
@FeignClient(name = "droute-cloud-gateway",
             path = "/api/user",
             contextId = "userServiceClient",
             configuration = UserServiceFeignConfig.class)
public interface UserServiceClient {

    @GetMapping("/courier/{courierId}/exists")
    public CommonResponseDto<Boolean> courierExistsById(@PathVariable Long courierId);

    @GetMapping("/courier/{courierId}")
    public CommonResponseDto<CourierDetailResponseDto> getCourierDetailsById(@PathVariable Long courierId);

    @PutMapping("/courier/{courierId}")
    public CommonResponseDto<CourierDetailResponseDto> updateCourierDetailsById(@PathVariable Long courierId, @RequestBody CourierDetailsRequestDto data);

}
