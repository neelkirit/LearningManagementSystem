package com.amogh.lms.service.impl;

import com.amogh.lms.service.*;
import com.amogh.lms.service.dto.*;
import com.amogh.lms.web.rest.errors.InternalServerErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class DashboardServiceImpl implements DashboardService {

    private final Logger log = LoggerFactory.getLogger(DashboardServiceImpl.class);

    private ExerciseStatsService exerciseStatsService;

    private CourseService courseService;

    private TopicService topicService;

    private ExerciseService exerciseService;

    private UserService userService;

    private Map<String, Float> rankWeightage;

    public DashboardServiceImpl(
        ExerciseStatsService exerciseStatsService,
        TopicService topicService,
        ExerciseService exerciseService,
        UserService userService,
        CourseService courseService) {
        this.exerciseStatsService = exerciseStatsService;
        this.topicService = topicService;
        this.exerciseService = exerciseService;
        this.userService = userService;
        this.courseService = courseService;
        this.initWeightage();
    }

    private void initWeightage() {
        rankWeightage = new HashMap<>();
        rankWeightage.put("QuestionsAttempted", 0.06f);
        rankWeightage.put("QuestionsAnswered", 0.04f);
    }

    /**
     * Gets the user rank. Calculated based on how many questions attempted and answered correct.
     *
     * @return rank of the user
     */
    @Override
    public Integer getUserRank(Map<String, Map<Long, Integer>> questionsStats) {
        if (questionsStats == null) {
           questionsStats = this.getQuestionsStatsByUsers();
        }
        Map<Long, Integer> userRanks = this.getRankByUsers(questionsStats);
        UserDTO userDTO = this.getCurrentUser();
        return userRanks.get(userDTO.getId());
    }

    /**
     * Gets the number of user questions attempted
     *
     * @return the number of questions attempted
     */
    @Override
    public Integer getNumberOfUserQuestions(Map<String, Map<Long, Integer>> questionsStats) {
        if (questionsStats == null) {
            questionsStats = this.getQuestionsStatsByUsers();
        }
        Map<Long, Integer> questAttemptedByUser = questionsStats.get("QuestionsAttempted");
        UserDTO currentUser = this.getCurrentUser();
        Integer totalAttempted = questAttemptedByUser.get(currentUser.getId());
        if (totalAttempted == null) {
            totalAttempted = 0;
        }
        return totalAttempted;
    }

    /**
     * Gets the number of incorrect answers by users
     *
     * @return the number of errors by user
     */
    @Override
    public Integer getNumberOfUserErrors(Map<String, Map<Long, Integer>> questionsStats) {
        if (questionsStats == null) {
            questionsStats = this.getQuestionsStatsByUsers();
        }
        Map<Long, Integer> questAttemptedByUser = questionsStats.get("QuestionsAttempted");
        UserDTO currentUser = this.getCurrentUser();
        Integer totalAttempted = questAttemptedByUser.get(currentUser.getId());
        Integer totalErrors = 0;
        if (totalAttempted != null) {
            Map<Long, Integer> questAnsweredByUser = questionsStats.get("QuestionsAnswered");
            Integer totalAnswered = questAnsweredByUser.get(currentUser.getId());
            if (totalAnswered == null) {
                totalAnswered = 0;
            }
            totalErrors = totalAttempted - totalAnswered;
        }
        return totalErrors;
    }

    /**
     * Gets the user progress
     *
     * @return overall progress of user
     */
    @Override
    public String getUserProgress(Map<String, Map<Long, Integer>> questionsStats) {
        if (questionsStats == null) {
            questionsStats = this.getQuestionsStatsByUsers();
        }
        Long totalExerciseRows = this.exerciseService.getTotalRows();
        Map<Long, Integer> questAttemptedByUser = questionsStats.get("QuestionsAttempted");
        UserDTO currentUser = this.getCurrentUser();
        Integer questAttempted = questAttemptedByUser.get(currentUser.getId());
        Float progress;
        if (questAttempted == null) {
            progress = 0F;
        } else {
            progress = ((float)questAttempted/totalExerciseRows) * 100F;
        }
        return String.format("%.2f", progress);
    }

    /**
     * Gets all the stats consolidated into one. Much lesser queries to be fired
     *
     * @return Dashboard DTO instance with relevant stats
     */
    @Override
    public DashboardDTO getAllDashboardStats() {
        Map<String, Map<Long, Integer>> questionsStats = this.getQuestionsStatsByUsers();
        DashboardDTO dashboardDTO = new DashboardDTO();
        dashboardDTO.setUserProgress(this.getUserProgress(questionsStats));
        dashboardDTO.setUserQuestionsAttempted(this.getNumberOfUserQuestions(questionsStats));
        dashboardDTO.setUserQuestionsErrored(this.getNumberOfUserErrors(questionsStats));
        dashboardDTO.setUserRank(this.getUserRank(questionsStats));
        dashboardDTO.setUserProgressByCourse(this.getUserProgressByCourse());
        return dashboardDTO;
    }

    /**
     * Gets user progress by course
     *
     * @return course name with its corresponding progress
     */
    @Override
    public Map<String, Float> getUserProgressByCourse() {
        // Find progress per course for user
        Map<Long, Integer> questsAnsweredByUserPerTopic = new HashMap<>();
        List<ExerciseStatsDTO> exerciseStatsDTOS = this.exerciseStatsService.findByLoggedInUser();
        for (ExerciseStatsDTO exerciseStatsDTO : exerciseStatsDTOS) {
            if (exerciseStatsDTO.isStatus()) {
                ExerciseDTO exerciseDTO = this.exerciseService.findOne(exerciseStatsDTO.getExerciseId());
                Integer answeredByTopicCount = questsAnsweredByUserPerTopic.get(exerciseDTO.getTopicId());
                if (answeredByTopicCount == null) {
                    answeredByTopicCount = 1;
                } else {
                    ++answeredByTopicCount;
                }
                questsAnsweredByUserPerTopic.put(exerciseDTO.getTopicId(), answeredByTopicCount);
            }
        }
        Map<Long, Integer> totalTopicCompletedByCourse = new HashMap<>();
        for (Map.Entry<Long, Integer> entry : questsAnsweredByUserPerTopic.entrySet()) {
            Long topicId = entry.getKey();
            Integer totalQuestsAnsweredForTopic = entry.getValue();
            if (totalQuestsAnsweredForTopic > 20) {
                TopicDTO topicDTO = this.topicService.findOne(topicId);
                Integer totalTopicsCompletedByCourse = totalTopicCompletedByCourse.get(topicDTO.getCourseId());
                if (totalTopicsCompletedByCourse == null) {
                    totalTopicsCompletedByCourse = 1;
                } else {
                    ++totalTopicsCompletedByCourse;
                }
                totalTopicCompletedByCourse.put(topicDTO.getCourseId(), totalTopicsCompletedByCourse);
            }
        }
        Map<Long, Integer> totalTopicByCourse = new HashMap<>();
        List<CourseDTO> courseDTOS = this.courseService.findAll();
        for (CourseDTO courseDTO : courseDTOS) {
            int totalTopicsByCourse = this.topicService.findByCourseId(courseDTO.getId()).size();
            totalTopicByCourse.put(courseDTO.getId(), totalTopicsByCourse);
        }
        Map<String, Float> userProgressByCourse = new HashMap<>();
        for (Map.Entry<Long, Integer> entry : totalTopicByCourse.entrySet()) {
            Long courseId = entry.getKey();
            Integer totalTopicsUnderCourse = entry.getValue();
            if(totalTopicsUnderCourse == null || totalTopicsUnderCourse == 0) {
                totalTopicsUnderCourse = 1;
            }
            Integer topicsCompletedByCourse = totalTopicCompletedByCourse.get(courseId);
            if(topicsCompletedByCourse == null) {
                topicsCompletedByCourse = 0;
            }
            Float courseProgress = ((float)topicsCompletedByCourse/totalTopicsUnderCourse) * 100F;
            CourseDTO courseDTO = this.courseService.findOne(courseId);
            userProgressByCourse.put(courseDTO.getName(), courseProgress);
        }
        return userProgressByCourse;
    }


    private Map<Long, Integer> getRankByUsers(Map<String, Map<Long, Integer>> questionStats) {
        Map<Long, Integer> questAttemptedByUser = questionStats.get("QuestionsAttempted");
        Map<Long, Integer> questAnsweredByUser = questionStats.get("QuestionsAnswered");
        Float questionsAttemptedWeight = this.rankWeightage.get("QuestionsAttempted");
        Float questionsAnsweredWeight = this.rankWeightage.get("QuestionsAnswered");
        Map<Long, Float> totalWeightScoreByUser = new HashMap<>();
        for (Map.Entry<Long, Integer> entry : questAttemptedByUser.entrySet()) {
            Long userId = entry.getKey();
            Float totalWeight = entry.getValue() * questionsAttemptedWeight;
            totalWeightScoreByUser.put(userId, totalWeight);
        }
        for (Map.Entry<Long, Integer> entry : questAnsweredByUser.entrySet()) {
            Long userId = entry.getKey();
            Float totalWeight = totalWeightScoreByUser.get(userId);
            totalWeight += entry.getValue() * questionsAnsweredWeight;
            totalWeightScoreByUser.put(userId, totalWeight);
        }
        List<Map.Entry<Long, Integer>> sortedEntriesByTotalWeight = new ArrayList(totalWeightScoreByUser.entrySet());
        sortedEntriesByTotalWeight.sort(Map.Entry.comparingByValue());
        Map<Long, Integer> result = new HashMap<>();
        for (int i=0; i < sortedEntriesByTotalWeight.size(); i++) {
            Map.Entry<Long, Integer> entry = sortedEntriesByTotalWeight.get(i);
            result.put(entry.getKey(), i + 1);
        }
        return result;
    }

    private Map<String, Map<Long, Integer>> getQuestionsStatsByUsers() {
        Map<Long, Integer> questAttemptedByUser = new HashMap<>();
        Map<Long, Integer> questAnsweredByUser = new HashMap<>();
        List<ExerciseStatsDTO> allRows = this.exerciseStatsService.getAllRows();
        for (ExerciseStatsDTO exerciseStatsDTO : allRows) {
            Integer totalQuestsAttempted = questAttemptedByUser.get(exerciseStatsDTO.getUserId());
            if (totalQuestsAttempted == null) {
                totalQuestsAttempted = 0;
            } else {
                ++totalQuestsAttempted;
            }
            questAttemptedByUser.put(exerciseStatsDTO.getUserId(), totalQuestsAttempted);
            if (exerciseStatsDTO.isStatus()) {
                Integer answeredCorrect = questAnsweredByUser.get(exerciseStatsDTO.getUserId());
                if (answeredCorrect == null) {
                    answeredCorrect = 0;
                } else {
                    ++answeredCorrect;
                }
                questAnsweredByUser.put(exerciseStatsDTO.getUserId(), answeredCorrect);
            }
        }
        Map<String, Map<Long, Integer>> questionStats = new HashMap<>();
        questionStats.put("QuestionsAttempted", questAttemptedByUser);
        questionStats.put("QuestionsAnswered", questAnsweredByUser);
        return questionStats;
    }


    private UserDTO getCurrentUser() {
        UserDTO userDTO = userService.getUserWithAuthorities()
            .map(UserDTO::new)
            .orElseThrow(() -> new InternalServerErrorException("User could not be found"));
        return userDTO;
    }
}
