package com.amogh.lms.web.rest;

import com.amogh.lms.service.DashboardService;
import com.amogh.lms.service.dto.DashboardDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Dashboard controller
 */
@RestController
@RequestMapping("/api/dashboard")
public class DashboardResource {

    private final Logger log = LoggerFactory.getLogger(DashboardResource.class);

    private DashboardService dashboardService;

    public DashboardResource(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    /**
    * GET getUserRank
    */
    @GetMapping("/user-rank")
    public Integer getUserRank() {
        return this.dashboardService.getUserRank(null);
    }

    /**
    * GET getNumberOfUserQuestions
    */
    @GetMapping("/questions-attempted")
    public Integer getNumberOfUserQuestions() {
        return this.dashboardService.getNumberOfUserQuestions(null);
    }

    /**
    * GET getNumberOfUserErrors
    */
    @GetMapping("/questions-errors")
    public Integer getNumberOfUserErrors() {
        return this.dashboardService.getNumberOfUserErrors(null);
    }

    /**
    * GET getUserProgress
    */
    @GetMapping("/user-progress")
    public String getUserProgress() {
        return this.dashboardService.getUserProgress(null);
    }

    /**
     * GET user progress by course
     */
    @GetMapping("/user-progress/course")
    public Map<String, Float> getUserProgressByCourse() {
        return this.dashboardService.getUserProgressByCourse();
    }

    /**
    * GET getAllUserDashboardStats
    */
    @GetMapping("/user-dashboard-stats")
    public DashboardDTO getAllUserDashboardStats() {
        return this.dashboardService.getAllDashboardStats();
    }

    /**
    * GET getNumberOfQuestionsAttemptedPerCourse
    */
    @GetMapping("/get-number-of-questions-attempted-per-course")
    public String getNumberOfQuestionsAttemptedPerCourse() {
        return "getNumberOfQuestionsAttemptedPerCourse";
    }

    /**
    * GET getProgressByCourse
    */
    @GetMapping("/get-progress-by-course")
    public String getProgressByCourse() {
        return "getProgressByCourse";
    }

}
