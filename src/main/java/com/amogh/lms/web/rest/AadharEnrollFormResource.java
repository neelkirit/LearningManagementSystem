package com.amogh.lms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.amogh.lms.service.AadharEnrollFormService;
import com.amogh.lms.web.rest.errors.BadRequestAlertException;
import com.amogh.lms.web.rest.util.HeaderUtil;
import com.amogh.lms.service.dto.AadharEnrollFormDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing AadharEnrollForm.
 */
@RestController
@RequestMapping("/api")
public class AadharEnrollFormResource {

    private final Logger log = LoggerFactory.getLogger(AadharEnrollFormResource.class);

    private static final String ENTITY_NAME = "aadharEnrollForm";

    private final AadharEnrollFormService aadharEnrollFormService;

    public AadharEnrollFormResource(AadharEnrollFormService aadharEnrollFormService) {
        this.aadharEnrollFormService = aadharEnrollFormService;
    }

    /**
     * POST  /aadhar-enroll-forms : Create a new aadharEnrollForm.
     *
     * @param aadharEnrollFormDTO the aadharEnrollFormDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new aadharEnrollFormDTO, or with status 400 (Bad Request) if the aadharEnrollForm has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/aadhar-enroll-forms")
    @Timed
    public ResponseEntity<AadharEnrollFormDTO> createAadharEnrollForm(@RequestBody AadharEnrollFormDTO aadharEnrollFormDTO) throws URISyntaxException {
        log.debug("REST request to save AadharEnrollForm : {}", aadharEnrollFormDTO);
        if (aadharEnrollFormDTO.getId() != null) {
            throw new BadRequestAlertException("A new aadharEnrollForm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AadharEnrollFormDTO result = aadharEnrollFormService.save(aadharEnrollFormDTO);
        return ResponseEntity.created(new URI("/api/aadhar-enroll-forms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /aadhar-enroll-forms : Updates an existing aadharEnrollForm.
     *
     * @param aadharEnrollFormDTO the aadharEnrollFormDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated aadharEnrollFormDTO,
     * or with status 400 (Bad Request) if the aadharEnrollFormDTO is not valid,
     * or with status 500 (Internal Server Error) if the aadharEnrollFormDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/aadhar-enroll-forms")
    @Timed
    public ResponseEntity<AadharEnrollFormDTO> updateAadharEnrollForm(@RequestBody AadharEnrollFormDTO aadharEnrollFormDTO) throws URISyntaxException {
        log.debug("REST request to update AadharEnrollForm : {}", aadharEnrollFormDTO);
        if (aadharEnrollFormDTO.getId() == null) {
            return createAadharEnrollForm(aadharEnrollFormDTO);
        }
        AadharEnrollFormDTO result = aadharEnrollFormService.save(aadharEnrollFormDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, aadharEnrollFormDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /aadhar-enroll-forms : get all the aadharEnrollForms.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of aadharEnrollForms in body
     */
    @GetMapping("/aadhar-enroll-forms")
    @Timed
    public List<AadharEnrollFormDTO> getAllAadharEnrollForms() {
        log.debug("REST request to get all AadharEnrollForms");
        return aadharEnrollFormService.findAll();
        }

    /**
     * GET  /aadhar-enroll-forms/:id : get the "id" aadharEnrollForm.
     *
     * @param id the id of the aadharEnrollFormDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the aadharEnrollFormDTO, or with status 404 (Not Found)
     */
    @GetMapping("/aadhar-enroll-forms/{id}")
    @Timed
    public ResponseEntity<AadharEnrollFormDTO> getAadharEnrollForm(@PathVariable Long id) {
        log.debug("REST request to get AadharEnrollForm : {}", id);
        AadharEnrollFormDTO aadharEnrollFormDTO = aadharEnrollFormService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(aadharEnrollFormDTO));
    }

    /**
     * DELETE  /aadhar-enroll-forms/:id : delete the "id" aadharEnrollForm.
     *
     * @param id the id of the aadharEnrollFormDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/aadhar-enroll-forms/{id}")
    @Timed
    public ResponseEntity<Void> deleteAadharEnrollForm(@PathVariable Long id) {
        log.debug("REST request to delete AadharEnrollForm : {}", id);
        aadharEnrollFormService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
