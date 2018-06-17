package com.amogh.lms.service.impl;

import com.amogh.lms.service.*;
import com.amogh.lms.domain.Assessment;
import com.amogh.lms.repository.AssessmentRepository;
import com.amogh.lms.service.dto.*;
import com.amogh.lms.service.mapper.AssessmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Service Implementation for managing Assessment.
 */
@Service
@Transactional
public class AssessmentServiceImpl implements AssessmentService {

    private final Logger log = LoggerFactory.getLogger(AssessmentServiceImpl.class);

    private final AssessmentRepository assessmentRepository;

    private final AssessmentMapper assessmentMapper;

    private final TopicService topicService;

    private final ExerciseService exerciseService;

    private final TemplateService templateService;

    private final AssessmentStatsService assessmentStatsService;

    public AssessmentServiceImpl(
        AssessmentRepository assessmentRepository,
        AssessmentMapper assessmentMapper,
        TopicService topicService,
        ExerciseService exerciseService,
        TemplateService templateService,
        AssessmentStatsService assessmentStatsService) {
        this.assessmentRepository = assessmentRepository;
        this.assessmentMapper = assessmentMapper;
        this.topicService = topicService;
        this.exerciseService = exerciseService;
        this.templateService = templateService;
        this.assessmentStatsService = assessmentStatsService;
    }

    /**
     * Save a assessment.
     *
     * @param assessmentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AssessmentDTO save(AssessmentDTO assessmentDTO) {
        log.debug("Request to save Assessment : {}", assessmentDTO);
        Assessment assessment = assessmentMapper.toEntity(assessmentDTO);
        assessment = assessmentRepository.save(assessment);
        return assessmentMapper.toDto(assessment);
    }

    /**
     * Get all the assessments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AssessmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Assessments");
        return assessmentRepository.findAll(pageable)
            .map(assessmentMapper::toDto);
    }

    /**
     * Get one assessment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AssessmentDTO findOne(Long id) {
        log.debug("Request to get Assessment : {}", id);
        Assessment assessment = assessmentRepository.findOne(id);
        return assessmentMapper.toDto(assessment);
    }

    /**
     * Delete the assessment by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Assessment : {}", id);
        assessmentRepository.delete(id);
    }

    /**
     * The assessment DTO for the course
     *
     * @param courseId the course id
     * @return the assessment DTO
     */
    @Override
    public AssessmentDTO findByCourseId(Long courseId) {
        Assessment assessmentForCourse = this.assessmentRepository.findByCourseId(courseId);
        return this.assessmentMapper.toDto(assessmentForCourse);
    }

    /**
     * Gets the exercises for the given coures id
     *
     * @param courseId the course id
     * @return list of assessment exercise DTOs
     */
    @Override
    public List<AssessmentExerciseDTO> getExercisesForCourseId(Long courseId) {
        List<TopicDTO> topicsForCourse = topicService.findByCourseId(courseId);
        AssessmentDTO assessmentDTO = this.findByCourseId(courseId);
        List<AssessmentExerciseDTO> allExercises = new ArrayList<>();
        for(TopicDTO topicDTO: topicsForCourse) {
            List<ExerciseDTO> exercisesForTopic = this.exerciseService.findByTopicId(topicDTO.getId());
            for (ExerciseDTO exerciseDTO: exercisesForTopic) {
                TemplateDTO templateDTO = this.templateService.findOne(exerciseDTO.getTemplateId());
                AssessmentExerciseDTO assessmentExerciseDTO = new AssessmentExerciseDTO();
                assessmentExerciseDTO.setupDTO(exerciseDTO, templateDTO, assessmentDTO);
                allExercises.add(assessmentExerciseDTO);
            }
        }
        return allExercises;
    }

    /**
     * Updates the assessment stats
     *
     * @return stats on how many answers the user got right
     */
    @Override
    public Map<String, Float> updateAssessmentStats(List<AssessmentExerciseDTO> assessmentExerciseDTOS) {
        Long assessmentId = null;
        Long userId = null;
        int answeredCorrect = 0;
        for (AssessmentExerciseDTO assessmentExerciseDTO: assessmentExerciseDTOS) {
            if (assessmentId == null) {
                assessmentId = assessmentExerciseDTO.getAssessmentDTO().getId();
                userId = assessmentExerciseDTO.getUserId();
            }
            if (assessmentExerciseDTO.getAnswered() == true) {
                ++answeredCorrect;
            }
        }
        Float assessmentScore = (((float)answeredCorrect)/assessmentExerciseDTOS.size()) * 100;
        List<AssessmentStatsDTO> assessmentStatsByUser = this.assessmentStatsService.findAssessmentStatsByUser();
        AssessmentStatsDTO assessmentStatsDTO = null;
        if (assessmentStatsByUser != null) {
            for (AssessmentStatsDTO assessmentStatDTOByUser: assessmentStatsByUser) {
                if (assessmentStatDTOByUser.getAssessmentId() == assessmentId) {
                    assessmentStatsDTO = assessmentStatDTOByUser;
                    if(assessmentScore <  assessmentStatDTOByUser.getScore()) {
                        assessmentStatsDTO.setScore(assessmentScore);
                    }
                    break;
                }
            }
        }
        if (assessmentStatsDTO == null) {
            assessmentStatsDTO = new AssessmentStatsDTO();
            assessmentStatsDTO.setAssessmentId(assessmentId);
            assessmentStatsDTO.setUserId(userId);
            assessmentStatsDTO.setScore(assessmentScore);
        }
        this.assessmentStatsService.save(assessmentStatsDTO);
        Map<String, Float> results = new HashMap<>();
        results.put("assessmentScore", assessmentScore);
        return results;
    }
}
