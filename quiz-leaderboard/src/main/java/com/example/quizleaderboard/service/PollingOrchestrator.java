package com.example.quizleaderboard.service;

import com.example.quizleaderboard.config.LeaderboardProperties;
import com.example.quizleaderboard.model.LeaderboardSubmission;
import com.example.quizleaderboard.model.QuizEvent;
import com.example.quizleaderboard.model.QuizSubmitRequest;
import com.example.quizleaderboard.model.QuizSubmitResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PollingOrchestrator implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(PollingOrchestrator.class);

    private final LeaderboardProperties properties;
    private final ExternalQuizApiClient apiClient;
    private final LeaderboardService leaderboardService;

    public PollingOrchestrator(
            LeaderboardProperties properties,
            ExternalQuizApiClient apiClient,
            LeaderboardService leaderboardService
    ) {
        this.properties = properties;
        this.apiClient = apiClient;
        this.leaderboardService = leaderboardService;
    }

    @Override
    public void run(String... args) throws InterruptedException {
        List<QuizEvent> collectedEvents = new ArrayList<>();

        for (int poll = 0; poll < properties.getPollCount(); poll++) {
            logger.info("Polling quiz API with poll={}", poll);
            List<QuizEvent> events = apiClient.fetchEvents(
                    properties.getBaseUrl(),
                    properties.getMessagesPath(),
                    properties.getRegNo(),
                    poll
            );
            collectedEvents.addAll(events);

            if (poll < properties.getPollCount() - 1) {
                Thread.sleep(properties.getPollDelayMs());
            }
        }

        LeaderboardSubmission submission = leaderboardService.buildSubmission(collectedEvents);
        logger.info("Computed leaderboard for {} participants with overall total score={}.",
                submission.getLeaderboard().size(),
                submission.getTotalScore());

        QuizSubmitRequest submitRequest = new QuizSubmitRequest(
                properties.getRegNo(),
                submission.getLeaderboard()
        );

        QuizSubmitResponse submitResponse = apiClient.submitLeaderboard(
                properties.getBaseUrl(),
                properties.getSubmitPath(),
                submitRequest
        );

        if (submitResponse != null) {
            logger.info(
                    "Submission result: correct={}, idempotent={}, submittedTotal={}, expectedTotal={}, message={}",
                    submitResponse.isCorrect(),
                    submitResponse.isIdempotent(),
                    submitResponse.getSubmittedTotal(),
                    submitResponse.getExpectedTotal(),
                    submitResponse.getMessage()
            );
        }
    }
}
