package com.amogh.lms.service.dto;

import java.io.Serializable;

public class DashboardDTO implements Serializable {

    private Integer userRank;

    private Integer userProgress;

    private Integer userQuestionsAttempted;

    private Integer userQuestionsErrored;

    public Integer getUserRank() {
        return userRank;
    }

    public void setUserRank(Integer userRank) {
        this.userRank = userRank;
    }

    public Integer getUserProgress() {
        return userProgress;
    }

    public void setUserProgress(Integer userProgress) {
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
}
