package com.amogh.lms.service;

import com.amogh.lms.domain.Exercise;
import com.amogh.lms.domain.Topic;
import com.amogh.lms.service.dto.TopicDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Topic.
 */
public interface TopicService {

    /**
     * Save a topic.
     *
     * @param topicDTO the entity to save
     * @return the persisted entity
     */
    TopicDTO save(TopicDTO topicDTO);

    /**
     * Get all the topics.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TopicDTO> findAll(Pageable pageable);

    /**
     * Get the "id" topic.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TopicDTO findOne(Long id);

    /**
     * Delete the "id" topic.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Finds the topic by name
     * @param topicName the topic name
     * @return TopicDTO object if found
     */
    TopicDTO findByName(String topicName);

    /**
     * Get all the topics.
     * @return the list of topics
     */
    List<Topic> getAll();
}
