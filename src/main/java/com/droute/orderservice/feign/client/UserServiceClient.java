package com.droute.orderservice.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.droute.orderservice.config.UserServiceFeignConfig;
import com.droute.orderservice.dto.response.CommonResponseDto;
import com.droute.orderservice.dto.response.UserEntityResponseDto;

@Component
@FeignClient(name = "droute-cloud-gateway",
             path = "/api/user",
             contextId = "userServiceClient",
             configuration = UserServiceFeignConfig.class)
public interface UserServiceClient {

    @PostMapping("/courier/{courierId}/exists")
    public CommonResponseDto<Boolean> courierExistsById(@PathVariable Long courierId);

    @GetMapping("/courier/{courierId}/user-details")
    public CommonResponseDto<UserEntityResponseDto> getUserDetailsByCourierId(@PathVariable Long courierId);

}
