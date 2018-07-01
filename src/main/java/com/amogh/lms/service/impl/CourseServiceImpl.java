package com.amogh.lms.service.impl;

import com.amogh.lms.service.AssessmentService;
import com.amogh.lms.service.CourseService;
import com.amogh.lms.domain.Course;
import com.amogh.lms.repository.CourseRepository;
import com.amogh.lms.service.dto.AssessmentDTO;
import com.amogh.lms.service.dto.CourseDTO;
import com.amogh.lms.service.mapper.CourseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * Service Implementation for managing Course.
 */
@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private final Logger log = LoggerFactory.getLogger(CourseServiceImpl.class);

    private final CourseRepository courseRepository;

    private final CourseMapper courseMapper;

    private final AssessmentService assessmentService;

    public CourseServiceImpl(
        CourseRepository courseRepository,
        CourseMapper courseMapper,
        AssessmentService assessmentService
    ) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.assessmentService = assessmentService;
    }

    /**
     * Save a course.
     *
     * @param courseDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CourseDTO save(CourseDTO courseDTO) {
        log.debug("Request to save Course : {}", courseDTO);
        Course course = courseMapper.toEntity(courseDTO);
        course = courseRepository.save(course);
        return courseMapper.toDto(course);
    }

    /**
     * Get all the courses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CourseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Courses");
        return courseRepository.findAll(pageable)
            .map(courseMapper::toDto);
    }

    /**
     * Get one course by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CourseDTO findOne(Long id) {
        log.debug("Request to get Course : {}", id);
        Course course = courseRepository.findOne(id);
        return courseMapper.toDto(course);
    }

    /**
     * Delete the course by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Course : {}", id);
        courseRepository.delete(id);
    }

    /**
     * Get all the courses.
     *
     * @return the list of entities
     */
    @Override
    public List<CourseDTO> findAll() {
        List<Course> courses = this.courseRepository.findAll();
        return this.courseMapper.toDto(courses);
    }

    @Override
    @Transactional(readOnly = true)
    public CourseDTO findByName(String name) {
        Course course = courseRepository.findByName(name);
        return courseMapper.toDto(course);
    }

    @Override
    public List<CourseDTO> findCoursesByAssessment() {
        List<CourseDTO> courseDTOS = new ArrayList<>();
        List<AssessmentDTO> assessmentDTOS = this.assessmentService.findAll();
        for (AssessmentDTO assessmentDTO: assessmentDTOS) {
            CourseDTO courseDTO = this.findOne(assessmentDTO.getId());
            Boolean assessmentCompleteness = this.assessmentService.getAssessmentCompleteness(assessmentDTO);
            courseDTO.setComplete(assessmentCompleteness);
            courseDTOS.add(courseDTO);
        }
        return courseDTOS;
    }
}
