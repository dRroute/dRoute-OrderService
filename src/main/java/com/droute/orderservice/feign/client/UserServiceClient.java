package com.droute.orderservice.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.droute.orderservice.config.UserServiceFeignConfig;
import com.droute.orderservice.dto.response.CommonResponseDto;

@Component
@FeignClient(name = "droute-cloud-gateway", path = "/api/user", configuration = UserServiceFeignConfig.class)
public interface UserServiceClient {

    @PostMapping("/courier/{courierId}/exists")
    public CommonResponseDto<Boolean> courierExistsById(@PathVariable Long courierId);

}
