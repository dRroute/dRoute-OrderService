package com.droute.orderservice.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.droute.orderservice.config.DriverServiceFeignConfig;
import com.droute.orderservice.dto.response.CommonResponseDto;
import com.droute.orderservice.dto.response.CourierDetailResponseDto;

@Component
@FeignClient(name = "droute-cloud-gateway",
             path = "/api/driver",
             configuration = DriverServiceFeignConfig.class)
public interface DriverServiceClient {

    @GetMapping("/journey-details/exists/{journeyId}")
    public CommonResponseDto<Boolean> journeyExistsById(@PathVariable Long journeyId);

}
