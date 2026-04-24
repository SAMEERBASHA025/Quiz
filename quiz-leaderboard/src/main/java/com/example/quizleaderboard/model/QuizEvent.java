package com.example.quizleaderboard.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QuizEvent {

    private String roundId;
    private String participant;
    private int score;

    public String getRoundId() {
        return roundId;
    }

    public void setRoundId(String roundId) {
        this.roundId = roundId;
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String uniqueKey() {
        return roundId + "|" + participant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuizEvent quizEvent)) {
            return false;
        }
        return Objects.equals(roundId, quizEvent.roundId) && Objects.equals(participant, quizEvent.participant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roundId, participant);
    }
}
