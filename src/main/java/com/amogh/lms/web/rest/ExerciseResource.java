package com.amogh.lms.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amogh.lms.domain.enumeration.ContentType;
import com.amogh.lms.ingester.Ingest;
import com.amogh.lms.ingester.model.IngestModel;
import com.amogh.lms.service.ExerciseService;
import com.amogh.lms.service.TemplateService;
import com.amogh.lms.service.TopicService;
import com.amogh.lms.service.dto.ExerciseDTO;
import com.amogh.lms.service.dto.ExerciseDetailsDTO;
import com.amogh.lms.service.dto.QuestionConfigDTO;
import com.amogh.lms.service.mapper.TemplateMapper;
import com.amogh.lms.service.mapper.TopicMapper;
import com.amogh.lms.web.rest.errors.BadRequestAlertException;
import com.amogh.lms.web.rest.util.HeaderUtil;
import com.amogh.lms.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Exercise.
 */
@RestController
@RequestMapping("/api")
public class ExerciseResource {

    private final Logger log = LoggerFactory.getLogger(ExerciseResource.class);

    private static final String ENTITY_NAME = "exercise";

    private final ExerciseService exerciseService;
    
    private final TemplateService templateService;
    
    private final TopicService topicService;
    
    @Autowired
    TopicMapper topicMapper;
    
    @Autowired
    TemplateMapper templateMapper;
    
    @Autowired
    Ingest ingest;

    public ExerciseResource(ExerciseService exerciseService, TemplateService templateService, TopicService topicService) {
        this.exerciseService = exerciseService;
        this.templateService = templateService;
        this.topicService = topicService;
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

    /**
     * POST  /exercises/topic/:topicId : get the "id" exercise.
     *
     * @param questionConfigDTO the question config of the topic for which we have exercise to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the exerciseDetailsDTO, or with status 404 (Not Found)
     */
    @PostMapping("/exercises/topic")
    @Timed
    public ResponseEntity<List<ExerciseDetailsDTO>> getExercisesForTopic(@Valid @RequestBody QuestionConfigDTO questionConfigDTO) {
        List<ExerciseDetailsDTO> exercisesByTopicId = this.exerciseService.findExercisesByTopicId(questionConfigDTO.getTopicId());
        List<ExerciseDetailsDTO> resultantExerciseDetailsDTOs = null;
        if (exercisesByTopicId.size() <= questionConfigDTO.getNumberOfQuestions()) {
            resultantExerciseDetailsDTOs = exercisesByTopicId;
        } else {
            Collections.shuffle(exercisesByTopicId);
            resultantExerciseDetailsDTOs = new ArrayList<>();
            for (int i=0; i< questionConfigDTO.getNumberOfQuestions(); i++) {
                resultantExerciseDetailsDTOs.add(exercisesByTopicId.get(i));
            }
        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(resultantExerciseDetailsDTOs));
    }

    @PostMapping("/exercises/topic/submit")
    @Timed
    public ResponseEntity<Map<String, Integer>> submitExerciseResults(@Valid @RequestBody List<ExerciseDetailsDTO> exerciseDetailsDTOS) {
        Integer totalCorrect = this.exerciseService.submitExerciseStats(exerciseDetailsDTOS);
        Map<String, Integer> answeredCorrect = new HashMap<>();
        answeredCorrect.put( "answeredCorrect", totalCorrect );
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(answeredCorrect));
    }
    

    @PostMapping("/exercises/uploadExercises")
    @Timed
    public ResponseEntity<Map<String, Integer>> uploadExcersisesSheet(@Valid @RequestBody String linesFromCsv) {
    	log.info("Starting to upload Exercise");
    	int count=0;    	
    	List<IngestModel> injestModelLs = new ArrayList<>();
    	Map<String, ContentType> contentTypeMap = new HashMap<>();
    	for( ContentType con: ContentType.values()) {// shouldnt be here :@    		
    		contentTypeMap.put( con.toString(), con);
    	}
    	String[] lines=linesFromCsv.split("\\r?\\n");  
    	for( String l : lines) {
    		String[] stSlit = l.split(",");
    		IngestModel im = new IngestModel();
    		im.setCourseName(stSlit[0].trim());
    		im.setTopicName(stSlit[1].trim());
    		im.setContent(stSlit[2].trim());    		
    		im.setContentType( contentTypeMap.get(stSlit[3].trim()));
    		im.setTemplateName(stSlit[4].trim());
    		im.setAnswer(stSlit[5].trim());
    		injestModelLs.add(im);
    	}    	
    	ingest.persist( injestModelLs );
    	log.info("Completed saving exercise data");    	
    return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(count)) ).build();
    }
}

