package com.droute.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.droute.orderservice.feign.error.decoder.CustomFeignErrorDecoder;

import feign.codec.ErrorDecoder;

@Configuration
public class DriverServiceFeignConfig {
    @Bean(name = "driverServiceErrorDecoder")
    public ErrorDecoder driverServiceErrorDecoder() {
        return new CustomFeignErrorDecoder();
    }
}
