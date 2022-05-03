package com.tosan.deposit.services;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class DepositConfigure {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
