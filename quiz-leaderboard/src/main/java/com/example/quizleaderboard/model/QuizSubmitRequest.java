package com.example.quizleaderboard.model;

import java.util.List;

public class QuizSubmitRequest {

    private String regNo;
    private List<LeaderboardEntry> leaderboard;

    public QuizSubmitRequest() {
    }

    public QuizSubmitRequest(String regNo, List<LeaderboardEntry> leaderboard) {
        this.regNo = regNo;
        this.leaderboard = leaderboard;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public List<LeaderboardEntry> getLeaderboard() {
        return leaderboard;
    }

    public void setLeaderboard(List<LeaderboardEntry> leaderboard) {
        this.leaderboard = leaderboard;
    }
}
