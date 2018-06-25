package com.amogh.lms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.amogh.lms.service.FormAttributesService;
import com.amogh.lms.web.rest.errors.BadRequestAlertException;
import com.amogh.lms.web.rest.util.HeaderUtil;
import com.amogh.lms.service.dto.FormAttributesDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing FormAttributes.
 */
@RestController
@RequestMapping("/api")
public class FormAttributesResource {

    private final Logger log = LoggerFactory.getLogger(FormAttributesResource.class);

    private static final String ENTITY_NAME = "formAttributes";

    private final FormAttributesService formAttributesService;

    public FormAttributesResource(FormAttributesService formAttributesService) {
        this.formAttributesService = formAttributesService;
    }

    /**
     * POST  /form-attributes : Create a new formAttributes.
     *
     * @param formAttributesDTO the formAttributesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new formAttributesDTO, or with status 400 (Bad Request) if the formAttributes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/form-attributes")
    @Timed
    public ResponseEntity<FormAttributesDTO> createFormAttributes(@Valid @RequestBody FormAttributesDTO formAttributesDTO) throws URISyntaxException {
        log.debug("REST request to save FormAttributes : {}", formAttributesDTO);
        if (formAttributesDTO.getId() != null) {
            throw new BadRequestAlertException("A new formAttributes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FormAttributesDTO result = formAttributesService.save(formAttributesDTO);
        return ResponseEntity.created(new URI("/api/form-attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /form-attributes : Updates an existing formAttributes.
     *
     * @param formAttributesDTO the formAttributesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated formAttributesDTO,
     * or with status 400 (Bad Request) if the formAttributesDTO is not valid,
     * or with status 500 (Internal Server Error) if the formAttributesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/form-attributes")
    @Timed
    public ResponseEntity<FormAttributesDTO> updateFormAttributes(@Valid @RequestBody FormAttributesDTO formAttributesDTO) throws URISyntaxException {
        log.debug("REST request to update FormAttributes : {}", formAttributesDTO);
        if (formAttributesDTO.getId() == null) {
            return createFormAttributes(formAttributesDTO);
        }
        FormAttributesDTO result = formAttributesService.save(formAttributesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, formAttributesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /form-attributes : get all the formAttributes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of formAttributes in body
     */
    @GetMapping("/form-attributes")
    @Timed
    public List<FormAttributesDTO> getAllFormAttributes() {
        log.debug("REST request to get all FormAttributes");
        return formAttributesService.findAll();
        }

    /**
     * GET  /form-attributes/:id : get the "id" formAttributes.
     *
     * @param id the id of the formAttributesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the formAttributesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/form-attributes/{id}")
    @Timed
    public ResponseEntity<FormAttributesDTO> getFormAttributes(@PathVariable Long id) {
        log.debug("REST request to get FormAttributes : {}", id);
        FormAttributesDTO formAttributesDTO = formAttributesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(formAttributesDTO));
    }

    /**
     * DELETE  /form-attributes/:id : delete the "id" formAttributes.
     *
     * @param id the id of the formAttributesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/form-attributes/{id}")
    @Timed
    public ResponseEntity<Void> deleteFormAttributes(@PathVariable Long id) {
        log.debug("REST request to delete FormAttributes : {}", id);
        formAttributesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
