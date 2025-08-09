package com.example.my_consumer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    @GetMapping("/consume")
    public String product() {
        return "Hello";
    }
}