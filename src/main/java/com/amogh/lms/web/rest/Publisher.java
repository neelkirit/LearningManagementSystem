package com.amogh.lms.web.rest;

import com.amogh.lms.repository.AssessmentRepository;
import com.amogh.lms.repository.CourseRepository;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing Assessment.
 */
@RestController
@RequestMapping("/api/bulk-publisher")
public class Publisher {

    @Autowired
    AssessmentRepository assessmentRepository;

    @Autowired
    CourseRepository courseRepository;

    private final Logger logger = LoggerFactory.getLogger(Publisher.class);

    @PostMapping("/assessments")
    @Timed
    public ResponseEntity publishAssessments() {
        logger.info("First Method");
        return new ResponseEntity(HttpStatus.OK);
    }
}
