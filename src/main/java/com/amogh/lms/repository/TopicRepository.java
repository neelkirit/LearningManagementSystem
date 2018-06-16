package com.amogh.lms.repository;

import com.amogh.lms.domain.Topic;
import org.springframework.stereotype.Repository;
import java.util.*;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Topic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    /**
     * Finds the topic by name
     * @param topicName the topic name
     * @return Topic object saved
     */
    Topic findByName(String topicName);

    /**
     * Find the topic given the topic id
     * @param courseId the course id
     * @return List of topic objects
     */
    List<Topic> findByCourseId(Long courseId);
}
