package com.example.quizleaderboard.service;

import com.example.quizleaderboard.model.LeaderboardEntry;
import com.example.quizleaderboard.model.LeaderboardSubmission;
import com.example.quizleaderboard.model.QuizEvent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class LeaderboardService {

    public LeaderboardSubmission buildSubmission(List<QuizEvent> allEvents) {
        Set<String> seenKeys = new HashSet<>();
        Map<String, Integer> totalsByParticipant = new HashMap<>();

        for (QuizEvent event : allEvents) {
            if (event.getRoundId() == null || event.getParticipant() == null) {
                continue;
            }

            String key = event.uniqueKey();
            if (!seenKeys.add(key)) {
                continue;
            }

            totalsByParticipant.merge(event.getParticipant(), event.getScore(), Integer::sum);
        }

        List<LeaderboardEntry> leaderboard = new ArrayList<>();
        int overallTotal = 0;
        for (Map.Entry<String, Integer> participantScore : totalsByParticipant.entrySet()) {
            int score = participantScore.getValue();
            overallTotal += score;
            leaderboard.add(new LeaderboardEntry(participantScore.getKey(), score));
        }

        leaderboard.sort(Comparator.comparingInt(LeaderboardEntry::getTotalScore).reversed());
        return new LeaderboardSubmission(overallTotal, leaderboard);
    }
}
