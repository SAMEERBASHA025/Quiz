package com.example.quizleaderboard.service;

import com.example.quizleaderboard.model.LeaderboardSubmission;
import com.example.quizleaderboard.model.QuizEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LocalMockQuizApiController {

    private static final Logger logger = LoggerFactory.getLogger(LocalMockQuizApiController.class);

    @GetMapping("/mock-api/events")
    public List<QuizEvent> events(@RequestParam("poll") int poll) {
        // Intentionally returns repeated round/participant pairs across polls.
        return List.of(
                event("R1", "Alice", 10),
                event("R1", "Bob", 12),
                event("R2", "Alice", 8),
                event("R2", "Bob", 11),
                event("R3", poll % 2 == 0 ? "Carol" : "Alice", poll + 5)
        );
    }

    @PostMapping("/mock-api/leaderboard")
    public ResponseEntity<Void> submit(@RequestBody LeaderboardSubmission submission) {
        logger.info("Received local leaderboard submission: participants={}, totalScore={}",
                submission.getLeaderboard().size(),
                submission.getTotalScore());
        return ResponseEntity.ok().build();
    }

    private QuizEvent event(String roundId, String participant, int score) {
        QuizEvent event = new QuizEvent();
        event.setRoundId(roundId);
        event.setParticipant(participant);
        event.setScore(score);
        return event;
    }
}
