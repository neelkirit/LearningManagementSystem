package com.amogh.lms.service.impl;

import com.amogh.lms.service.ExerciseService;
import com.amogh.lms.service.ExerciseStatsService;
import com.amogh.lms.service.TopicService;
import com.amogh.lms.domain.Topic;
import com.amogh.lms.repository.TopicRepository;
import com.amogh.lms.service.dto.ExerciseDTO;
import com.amogh.lms.service.dto.ExerciseStatsDTO;
import com.amogh.lms.service.dto.TopicDTO;
import com.amogh.lms.service.dto.TopicDetailsDTO;
import com.amogh.lms.service.mapper.TopicMapper;

import java.util.*;

import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Topic.
 */
@Service
@Transactional
public class TopicServiceImpl implements TopicService {

    private final Logger log = LoggerFactory.getLogger(TopicServiceImpl.class);

    private final TopicRepository topicRepository;

    private final TopicMapper topicMapper;

    private final ExerciseStatsService exerciseStatsService;

    private final ExerciseService exerciseService;

    public TopicServiceImpl(
        TopicRepository topicRepository,
        TopicMapper topicMapper,
        ExerciseStatsService exerciseStatsService,
        ExerciseService exerciseService) {
        this.topicRepository = topicRepository;
        this.topicMapper = topicMapper;
        this.exerciseStatsService = exerciseStatsService;
        this.exerciseService = exerciseService;
    }

    /**
     * Save a topic.
     *
     * @param topicDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TopicDTO save(TopicDTO topicDTO) {
        log.debug("Request to save Topic : {}", topicDTO);
        Topic topic = topicMapper.toEntity(topicDTO);
        topic = topicRepository.save(topic);
        return topicMapper.toDto(topic);
    }

    /**
     * Get all the topics.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TopicDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Topics");
        return topicRepository.findAll(pageable)
            .map(topicMapper::toDto);
    }

    /**
     * Get one topic by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TopicDTO findOne(Long id) {
        log.debug("Request to get Topic : {}", id);
        Topic topic = topicRepository.findOne(id);
        return topicMapper.toDto(topic);
    }

    /**
     * Delete the topic by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Topic : {}", id);
        topicRepository.delete(id);
    }

    /**
     * Finds the topic by Name
     *
     * @param topicName the topic name
     * @return TopicDTO object if found
     */
    @Override
    public TopicDTO findByName(String topicName) {
        Topic topic = topicRepository.findByName(topicName);
        return topicMapper.toDto(topic);
    }

    /**
     * Find the topic given the topic id
     *
     * @param courseId the course id
     * @return List of topic objects
     */
    @Override
    public List<TopicDetailsDTO> findByCourseId(Long courseId) {
        List<TopicDetailsDTO> topicDetailsDTOS = new ArrayList<>();
        List<Topic> topicList = topicRepository.findByCourseId(courseId);
        List<TopicDTO> topicDTOS = topicMapper.toDto(topicList);
        for(TopicDTO topicDTO: topicDTOS) {
            List<ExerciseStatsDTO> exercisesByUser = this.exerciseStatsService.findByLoggedInUser();
            List<ExerciseDTO> exerciseDTOS = this.exerciseService.findByTopicId(topicDTO.getId());
            Set<Long> exerciseIds = new HashSet<>();
            for(ExerciseDTO exerciseDTO : exerciseDTOS) {
                exerciseIds.add(exerciseDTO.getId());
            }
            int totalExercises = exerciseDTOS.size();
            int answeredCorrect = 0;
            for(ExerciseStatsDTO exerciseStatsDTO: exercisesByUser) {
                if(exerciseStatsDTO.isStatus() && exerciseIds.contains(exerciseStatsDTO.getExerciseId())) {
                    ++answeredCorrect;
                }
            }
            float totalCompleted = ((float)answeredCorrect/totalExercises) * 100.0F;
            TopicDetailsDTO topicDetailsDTO = new TopicDetailsDTO();
            topicDetailsDTO.setupDTO(topicDTO);
            if(totalCompleted > 50.0) {
                topicDetailsDTO.setComplete(true);
            } else {
                topicDetailsDTO.setComplete(false);
            }
            topicDetailsDTOS.add(topicDetailsDTO);
        }
        return topicDetailsDTOS;
    }

    /**
     * Get all topics without pagination
     *
     * @return get all topic DTOs
     */
    @Override
    public List<TopicDTO> findAll() {
        List<Topic> allTopics = this.topicRepository.findAll();
        return this.topicMapper.toDto(allTopics);
    }
}
