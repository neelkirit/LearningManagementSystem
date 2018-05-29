package com.amogh.lms.service.impl;

import com.amogh.lms.domain.Course;
import com.amogh.lms.domain.Exercise;
import com.amogh.lms.domain.ExerciseStats;
import com.amogh.lms.domain.Topic;
import com.amogh.lms.service.*;
import com.amogh.lms.service.dto.CourseStatDTO;
import com.amogh.lms.service.dto.UserStatsDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by akhil raj azhikodan on 29/5/18.
 */
@Service
public class DashboardServiceImpl implements DashboardService
{
    CourseService courseService;
    ExerciseService exerciseService;
    ExerciseStatsService exerciseStatsService;
    TopicService topicService;


    DashboardServiceImpl(CourseService courseService,
                     ExerciseService exerciseService,
                     ExerciseStatsService exerciseStatsService,
                     TopicService topicService)
    {
        this.courseService = courseService;
        this.exerciseService = exerciseService;
        this.exerciseStatsService = exerciseStatsService;
        this.topicService = topicService;
    }

    /**
     * @return Stats of the users performance
     */
    @Override
    public UserStatsDTO getUserStats()
    {
        List<Course> courses = courseService.getAll();
        List<Exercise> exercises = exerciseService.getAll();
        List<Topic> topics = topicService.getAll();
        List<ExerciseStats> exerciseStats = exerciseStatsService.getUserStats();

        long correctAnswers = exerciseStats.stream().filter(ExerciseStats::isStatus).count();
        int progressPercent = (exerciseStats.size() != 0)? (int) ((correctAnswers / exerciseStats.size()) * 100) : 100;

        Map<String, CourseStatDTO> courseStatsMap = new HashMap<>();

        courses.forEach(course -> {
            List<Topic> courseTopics = topics.stream().filter(topic -> topic.getCourse().equals(course))
                .collect(Collectors.toList());

            List<Exercise> courseExercises = exercises.stream()
                .filter(exercise -> courseTopics.contains(exercise.getTopic()))
                .collect(Collectors.toList());

            Map<Boolean, Long> courseStats = exerciseStats.stream()
                .filter(exerciseStat -> courseExercises.contains(exerciseStat.getExercise()))
                .collect(Collectors.groupingBy(ExerciseStats::isStatus,
                                               Collectors.counting()));

            long correctCount = Optional.ofNullable(courseStats.get(true)).orElse(0L);
            long incorrectCount = Optional.ofNullable(courseStats.get(false)).orElse(0L);

            courseStatsMap.put(course.getName(), new CourseStatDTO(correctCount,
                                                                   incorrectCount,
                                                                   courseExercises.size() - (correctCount + incorrectCount)));
        });

        return UserStatsDTO.builder().progressPercent(progressPercent)
            .questionsAttempted(exerciseStats.size())
            .wrongAnswers((int) (exerciseStats.size() - correctAnswers))
            .courseStats(courseStatsMap).build();
    }
}
