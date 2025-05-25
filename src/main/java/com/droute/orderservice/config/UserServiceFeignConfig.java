package com.droute.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.droute.orderservice.feign.error.decoder.CustomFeignErrorDecoder;

import feign.codec.ErrorDecoder;

@Configuration
public class UserServiceFeignConfig {
    @Bean(name = "userServiceErrorDecoder")
    public ErrorDecoder userServiceErrorDecoder() {
        return new CustomFeignErrorDecoder();
    }
}
