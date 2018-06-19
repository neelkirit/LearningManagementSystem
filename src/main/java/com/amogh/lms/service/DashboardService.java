package com.amogh.lms.service;

import com.amogh.lms.service.dto.DashboardDTO;

import java.util.Map;

public interface DashboardService {

    /**
     * Gets the user rank. Calculated based on how many questions attempted and answered correct.
     * @return rank of the user
     */
    Integer getUserRank(Map<String, Map<Long, Integer>> questionsStats);

    /**
     * Gets the number of user questions attempted
     * @return the number of questions attempted
     */
    Integer getNumberOfUserQuestions(Map<String, Map<Long, Integer>> questionsStats);

    /**
     * Gets the number of incorrect answers by users
     * @return the number of errors by user
     */
    Integer getNumberOfUserErrors(Map<String, Map<Long, Integer>> questionsStats);

    /**
     * Gets the user progress
     * @return overall progress of user
     */
    String getUserProgress(Map<String, Map<Long, Integer>> questionsStats);

    /**
     * Gets all the stats consolidated into one. Much lesser queries to be fired
     * @return Dashboard DTO instance with relevant stats
     */
    DashboardDTO getAllDashboardStats();

    /**
     * Gets user progress by course
     * @return course name with its corresponding progress
     */
    Map<String, Float> getUserProgressByCourse();
}
