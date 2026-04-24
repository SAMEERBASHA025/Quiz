package com.example.quizleaderboard.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Collections;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QuizMessagesResponse {

    private String regNo;
    private String setId;
    private int pollIndex;
    private List<QuizEvent> events;

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getSetId() {
        return setId;
    }

    public void setSetId(String setId) {
        this.setId = setId;
    }

    public int getPollIndex() {
        return pollIndex;
    }

    public void setPollIndex(int pollIndex) {
        this.pollIndex = pollIndex;
    }

    public List<QuizEvent> getEvents() {
        return events == null ? Collections.emptyList() : events;
    }

    public void setEvents(List<QuizEvent> events) {
        this.events = events;
    }
}
