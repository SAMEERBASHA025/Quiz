package com.example.quizleaderboard.model;

import java.util.List;

public class LeaderboardSubmission {

    private int totalScore;
    private List<LeaderboardEntry> leaderboard;

    public LeaderboardSubmission() {
    }

    public LeaderboardSubmission(int totalScore, List<LeaderboardEntry> leaderboard) {
        this.totalScore = totalScore;
        this.leaderboard = leaderboard;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public List<LeaderboardEntry> getLeaderboard() {
        return leaderboard;
    }

    public void setLeaderboard(List<LeaderboardEntry> leaderboard) {
        this.leaderboard = leaderboard;
    }
}
