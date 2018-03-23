package com.amogh.lms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.amogh.lms.service.AssessmentStatsService;
import com.amogh.lms.web.rest.errors.BadRequestAlertException;
import com.amogh.lms.web.rest.util.HeaderUtil;
import com.amogh.lms.web.rest.util.PaginationUtil;
import com.amogh.lms.service.dto.AssessmentStatsDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing AssessmentStats.
 */
@RestController
@RequestMapping("/api")
public class AssessmentStatsResource {

    private final Logger log = LoggerFactory.getLogger(AssessmentStatsResource.class);

    private static final String ENTITY_NAME = "assessmentStats";

    private final AssessmentStatsService assessmentStatsService;

    public AssessmentStatsResource(AssessmentStatsService assessmentStatsService) {
        this.assessmentStatsService = assessmentStatsService;
    }

    /**
     * POST  /assessment-stats : Create a new assessmentStats.
     *
     * @param assessmentStatsDTO the assessmentStatsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new assessmentStatsDTO, or with status 400 (Bad Request) if the assessmentStats has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/assessment-stats")
    @Timed
    public ResponseEntity<AssessmentStatsDTO> createAssessmentStats(@Valid @RequestBody AssessmentStatsDTO assessmentStatsDTO) throws URISyntaxException {
        log.debug("REST request to save AssessmentStats : {}", assessmentStatsDTO);
        if (assessmentStatsDTO.getId() != null) {
            throw new BadRequestAlertException("A new assessmentStats cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AssessmentStatsDTO result = assessmentStatsService.save(assessmentStatsDTO);
        return ResponseEntity.created(new URI("/api/assessment-stats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /assessment-stats : Updates an existing assessmentStats.
     *
     * @param assessmentStatsDTO the assessmentStatsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated assessmentStatsDTO,
     * or with status 400 (Bad Request) if the assessmentStatsDTO is not valid,
     * or with status 500 (Internal Server Error) if the assessmentStatsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/assessment-stats")
    @Timed
    public ResponseEntity<AssessmentStatsDTO> updateAssessmentStats(@Valid @RequestBody AssessmentStatsDTO assessmentStatsDTO) throws URISyntaxException {
        log.debug("REST request to update AssessmentStats : {}", assessmentStatsDTO);
        if (assessmentStatsDTO.getId() == null) {
            return createAssessmentStats(assessmentStatsDTO);
        }
        AssessmentStatsDTO result = assessmentStatsService.save(assessmentStatsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, assessmentStatsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /assessment-stats : get all the assessmentStats.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of assessmentStats in body
     */
    @GetMapping("/assessment-stats")
    @Timed
    public ResponseEntity<List<AssessmentStatsDTO>> getAllAssessmentStats(Pageable pageable) {
        log.debug("REST request to get a page of AssessmentStats");
        Page<AssessmentStatsDTO> page = assessmentStatsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/assessment-stats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /assessment-stats/:id : get the "id" assessmentStats.
     *
     * @param id the id of the assessmentStatsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the assessmentStatsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/assessment-stats/{id}")
    @Timed
    public ResponseEntity<AssessmentStatsDTO> getAssessmentStats(@PathVariable Long id) {
        log.debug("REST request to get AssessmentStats : {}", id);
        AssessmentStatsDTO assessmentStatsDTO = assessmentStatsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(assessmentStatsDTO));
    }

    /**
     * DELETE  /assessment-stats/:id : delete the "id" assessmentStats.
     *
     * @param id the id of the assessmentStatsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/assessment-stats/{id}")
    @Timed
    public ResponseEntity<Void> deleteAssessmentStats(@PathVariable Long id) {
        log.debug("REST request to delete AssessmentStats : {}", id);
        assessmentStatsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
