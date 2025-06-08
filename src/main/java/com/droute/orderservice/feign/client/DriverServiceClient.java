package com.droute.orderservice.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.droute.orderservice.config.DriverServiceFeignConfig;
import com.droute.orderservice.dto.response.CommonResponseDto;
import com.droute.orderservice.dto.response.FilteredJourneyDetailsResponseDto;
import com.droute.orderservice.dto.response.JourneyDetailResponseDto;

@Component
@FeignClient(name = "droute-cloud-gateway",
             path = "/api/driver",
             contextId = "driverServiceClient",
             configuration = DriverServiceFeignConfig.class)
public interface DriverServiceClient {

    @GetMapping("/journey-details/{journeyId}/exists")
    public CommonResponseDto<Boolean> journeyExistsById(@PathVariable Long journeyId);


    @GetMapping("/journey-details/{journeyId}")
    public CommonResponseDto<JourneyDetailResponseDto> getJourneyDetailsById(@PathVariable Long journeyId);
    
    
    @GetMapping("/journey-details/{journeyId}/driver-detail")
    public CommonResponseDto<FilteredJourneyDetailsResponseDto> getJourneyDetailsWithDriverDetailsByJourneyId(@PathVariable Long journeyId);

}
