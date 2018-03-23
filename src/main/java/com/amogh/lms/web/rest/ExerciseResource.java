package com.amogh.lms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.amogh.lms.service.ExerciseService;
import com.amogh.lms.web.rest.errors.BadRequestAlertException;
import com.amogh.lms.web.rest.util.HeaderUtil;
import com.amogh.lms.web.rest.util.PaginationUtil;
import com.amogh.lms.service.dto.ExerciseDTO;
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
 * REST controller for managing Exercise.
 */
@RestController
@RequestMapping("/api")
public class ExerciseResource {

    private final Logger log = LoggerFactory.getLogger(ExerciseResource.class);

    private static final String ENTITY_NAME = "exercise";

    private final ExerciseService exerciseService;

    public ExerciseResource(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    /**
     * POST  /exercises : Create a new exercise.
     *
     * @param exerciseDTO the exerciseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new exerciseDTO, or with status 400 (Bad Request) if the exercise has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/exercises")
    @Timed
    public ResponseEntity<ExerciseDTO> createExercise(@Valid @RequestBody ExerciseDTO exerciseDTO) throws URISyntaxException {
        log.debug("REST request to save Exercise : {}", exerciseDTO);
        if (exerciseDTO.getId() != null) {
            throw new BadRequestAlertException("A new exercise cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExerciseDTO result = exerciseService.save(exerciseDTO);
        return ResponseEntity.created(new URI("/api/exercises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /exercises : Updates an existing exercise.
     *
     * @param exerciseDTO the exerciseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated exerciseDTO,
     * or with status 400 (Bad Request) if the exerciseDTO is not valid,
     * or with status 500 (Internal Server Error) if the exerciseDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/exercises")
    @Timed
    public ResponseEntity<ExerciseDTO> updateExercise(@Valid @RequestBody ExerciseDTO exerciseDTO) throws URISyntaxException {
        log.debug("REST request to update Exercise : {}", exerciseDTO);
        if (exerciseDTO.getId() == null) {
            return createExercise(exerciseDTO);
        }
        ExerciseDTO result = exerciseService.save(exerciseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, exerciseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /exercises : get all the exercises.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of exercises in body
     */
    @GetMapping("/exercises")
    @Timed
    public ResponseEntity<List<ExerciseDTO>> getAllExercises(Pageable pageable) {
        log.debug("REST request to get a page of Exercises");
        Page<ExerciseDTO> page = exerciseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/exercises");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /exercises/:id : get the "id" exercise.
     *
     * @param id the id of the exerciseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the exerciseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/exercises/{id}")
    @Timed
    public ResponseEntity<ExerciseDTO> getExercise(@PathVariable Long id) {
        log.debug("REST request to get Exercise : {}", id);
        ExerciseDTO exerciseDTO = exerciseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(exerciseDTO));
    }

    /**
     * DELETE  /exercises/:id : delete the "id" exercise.
     *
     * @param id the id of the exerciseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/exercises/{id}")
    @Timed
    public ResponseEntity<Void> deleteExercise(@PathVariable Long id) {
        log.debug("REST request to delete Exercise : {}", id);
        exerciseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
