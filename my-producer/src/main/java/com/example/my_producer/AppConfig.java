package com.example.my_producer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    @Bean
    public WebClient webClient() {
        // You can configure base URL, timeouts, etc. here if needed
        return WebClient.builder().build();
    }
}
