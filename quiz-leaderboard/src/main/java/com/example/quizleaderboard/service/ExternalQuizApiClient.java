package com.example.quizleaderboard.service;

import com.example.quizleaderboard.model.QuizMessagesResponse;
import com.example.quizleaderboard.model.QuizEvent;
import com.example.quizleaderboard.model.QuizSubmitRequest;
import com.example.quizleaderboard.model.QuizSubmitResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

@Component
public class ExternalQuizApiClient {

    private static final Logger logger = LoggerFactory.getLogger(ExternalQuizApiClient.class);

    private final RestTemplate restTemplate;

    public ExternalQuizApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<QuizEvent> fetchEvents(String baseUrl, String messagesPath, String regNo, int pollValue) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path(messagesPath)
                .queryParam("regNo", regNo)
                .queryParam("poll", pollValue)
                .toUriString();

        try {
            ResponseEntity<QuizMessagesResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    QuizMessagesResponse.class
            );

            QuizMessagesResponse body = response.getBody();
            return body == null ? Collections.emptyList() : body.getEvents();
        } catch (RestClientException exception) {
            logger.error("Failed to fetch quiz events for poll={}. Continuing with next poll.", pollValue, exception);
            return Collections.emptyList();
        }
    }

    public QuizSubmitResponse submitLeaderboard(String baseUrl, String submitPath, QuizSubmitRequest submission) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path(submitPath)
                .toUriString();

        try {
            ResponseEntity<QuizSubmitResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity<>(submission),
                    QuizSubmitResponse.class
            );
            logger.info("Leaderboard submitted successfully.");
            return response.getBody();
        } catch (RestClientException exception) {
            logger.error("Failed to submit leaderboard.", exception);
            throw exception;
        }
    }
}
