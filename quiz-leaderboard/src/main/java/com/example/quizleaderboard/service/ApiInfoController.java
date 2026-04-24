package com.example.quizleaderboard.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class ApiInfoController {

    @GetMapping("/")
    public Map<String, Object> root() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("name", "Quiz Leaderboard System");
        response.put("status", "running");
        response.put("endpoints", new String[]{
                "/mock-api/events?poll=0",
                "/mock-api/leaderboard",
                "/health"
        });
        return response;
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }
}
