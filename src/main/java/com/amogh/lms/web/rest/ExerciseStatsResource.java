package com.amogh.lms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.amogh.lms.service.ExerciseStatsService;
import com.amogh.lms.web.rest.errors.BadRequestAlertException;
import com.amogh.lms.web.rest.util.HeaderUtil;
import com.amogh.lms.web.rest.util.PaginationUtil;
import com.amogh.lms.service.dto.ExerciseStatsDTO;
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
 * REST controller for managing ExerciseStats.
 */
@RestController
@RequestMapping("/api")
public class ExerciseStatsResource {

    private final Logger log = LoggerFactory.getLogger(ExerciseStatsResource.class);

    private static final String ENTITY_NAME = "exerciseStats";

    private final ExerciseStatsService exerciseStatsService;

    public ExerciseStatsResource(ExerciseStatsService exerciseStatsService) {
        this.exerciseStatsService = exerciseStatsService;
    }

    /**
     * POST  /exercise-stats : Create a new exerciseStats.
     *
     * @param exerciseStatsDTO the exerciseStatsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new exerciseStatsDTO, or with status 400 (Bad Request) if the exerciseStats has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/exercise-stats")
    @Timed
    public ResponseEntity<ExerciseStatsDTO> createExerciseStats(@Valid @RequestBody ExerciseStatsDTO exerciseStatsDTO) throws URISyntaxException {
        log.debug("REST request to save ExerciseStats : {}", exerciseStatsDTO);
        if (exerciseStatsDTO.getId() != null) {
            throw new BadRequestAlertException("A new exerciseStats cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExerciseStatsDTO result = exerciseStatsService.save(exerciseStatsDTO);
        return ResponseEntity.created(new URI("/api/exercise-stats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /exercise-stats : Updates an existing exerciseStats.
     *
     * @param exerciseStatsDTO the exerciseStatsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated exerciseStatsDTO,
     * or with status 400 (Bad Request) if the exerciseStatsDTO is not valid,
     * or with status 500 (Internal Server Error) if the exerciseStatsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/exercise-stats")
    @Timed
    public ResponseEntity<ExerciseStatsDTO> updateExerciseStats(@Valid @RequestBody ExerciseStatsDTO exerciseStatsDTO) throws URISyntaxException {
        log.debug("REST request to update ExerciseStats : {}", exerciseStatsDTO);
        if (exerciseStatsDTO.getId() == null) {
            return createExerciseStats(exerciseStatsDTO);
        }
        ExerciseStatsDTO result = exerciseStatsService.save(exerciseStatsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, exerciseStatsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /exercise-stats : get all the exerciseStats.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of exerciseStats in body
     */
    @GetMapping("/exercise-stats")
    @Timed
    public ResponseEntity<List<ExerciseStatsDTO>> getAllExerciseStats(Pageable pageable) {
        log.debug("REST request to get a page of ExerciseStats");
        Page<ExerciseStatsDTO> page = exerciseStatsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/exercise-stats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /exercise-stats/:id : get the "id" exerciseStats.
     *
     * @param id the id of the exerciseStatsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the exerciseStatsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/exercise-stats/{id}")
    @Timed
    public ResponseEntity<ExerciseStatsDTO> getExerciseStats(@PathVariable Long id) {
        log.debug("REST request to get ExerciseStats : {}", id);
        ExerciseStatsDTO exerciseStatsDTO = exerciseStatsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(exerciseStatsDTO));
    }

    /**
     * DELETE  /exercise-stats/:id : delete the "id" exerciseStats.
     *
     * @param id the id of the exerciseStatsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/exercise-stats/{id}")
    @Timed
    public ResponseEntity<Void> deleteExerciseStats(@PathVariable Long id) {
        log.debug("REST request to delete ExerciseStats : {}", id);
        exerciseStatsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
