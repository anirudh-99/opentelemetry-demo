package com.example.my_producer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class MyController {

    private final WebClient webClient;

    // Inject WebClient created in AppConfig
    public MyController(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping("/produce")
    public Mono<String> product() {
        String consumerAppUrl = "http://my-consumer:8081/consume";
        
        return webClient.get().uri(consumerAppUrl)
                .retrieve()
                .bodyToMono(String.class)
                .map(consumerResponse -> {
                    System.out.println("Producer successfully received response from /consume: " + consumerResponse);
                    return "Producer called /consume. Response from consumer: " + consumerResponse;
                })
                .onErrorResume(e -> {
                    return Mono.just("Error calling /consume: " + e.getMessage() + ". Ensure consumer app is running on " + consumerAppUrl);
                });

    }
}
