package com.amogh.lms.service.dto;

import java.io.Serializable;
import java.util.Map;

public class DashboardDTO implements Serializable {

    private Integer userRank;

    private String userProgress;

    private Integer userQuestionsAttempted;

    private Integer userQuestionsErrored;

    private Map<String, Float> userProgressByCourse;

    public Integer getUserRank() {
        return userRank;
    }

    public void setUserRank(Integer userRank) {
        this.userRank = userRank;
    }

    public String getUserProgress() {
        return userProgress;
    }

    public void setUserProgress(String userProgress) {
        this.userProgress = userProgress;
    }

    public Integer getUserQuestionsAttempted() {
        return userQuestionsAttempted;
    }

    public void setUserQuestionsAttempted(Integer userQuestionsAttempted) {
        this.userQuestionsAttempted = userQuestionsAttempted;
    }

    public Integer getUserQuestionsErrored() {
        return userQuestionsErrored;
    }

    public void setUserQuestionsErrored(Integer userQuestionsErrored) {
        this.userQuestionsErrored = userQuestionsErrored;
    }

    public Map<String, Float> getUserProgressByCourse() {
        return userProgressByCourse;
    }

    public void setUserProgressByCourse(Map<String, Float> userProgressByCourse) {
        this.userProgressByCourse = userProgressByCourse;
    }
}
