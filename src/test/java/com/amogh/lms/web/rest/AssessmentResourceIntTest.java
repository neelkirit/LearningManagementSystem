package com.amogh.lms.web.rest;

import com.amogh.lms.AmoghServerApp;

import com.amogh.lms.domain.Assessment;
import com.amogh.lms.repository.AssessmentRepository;
import com.amogh.lms.service.AssessmentService;
import com.amogh.lms.service.dto.AssessmentDTO;
import com.amogh.lms.service.mapper.AssessmentMapper;
import com.amogh.lms.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.amogh.lms.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AssessmentResource REST controller.
 *
 * @see AssessmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AmoghServerApp.class)
public class AssessmentResourceIntTest {

    private static final Float DEFAULT_THRESHOLD = 1F;
    private static final Float UPDATED_THRESHOLD = 2F;

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private AssessmentMapper assessmentMapper;

    @Autowired
    private AssessmentService assessmentService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAssessmentMockMvc;

    private Assessment assessment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AssessmentResource assessmentResource = new AssessmentResource(assessmentService);
        this.restAssessmentMockMvc = MockMvcBuilders.standaloneSetup(assessmentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Assessment createEntity(EntityManager em) {
        Assessment assessment = new Assessment()
            .threshold(DEFAULT_THRESHOLD);
        return assessment;
    }

    @Before
    public void initTest() {
        assessment = createEntity(em);
    }

    @Test
    @Transactional
    public void createAssessment() throws Exception {
        int databaseSizeBeforeCreate = assessmentRepository.findAll().size();

        // Create the Assessment
        AssessmentDTO assessmentDTO = assessmentMapper.toDto(assessment);
        restAssessmentMockMvc.perform(post("/api/assessments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assessmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Assessment in the database
        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeCreate + 1);
        Assessment testAssessment = assessmentList.get(assessmentList.size() - 1);
        assertThat(testAssessment.getThreshold()).isEqualTo(DEFAULT_THRESHOLD);
    }

    @Test
    @Transactional
    public void createAssessmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = assessmentRepository.findAll().size();

        // Create the Assessment with an existing ID
        assessment.setId(1L);
        AssessmentDTO assessmentDTO = assessmentMapper.toDto(assessment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssessmentMockMvc.perform(post("/api/assessments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assessmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Assessment in the database
        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkThresholdIsRequired() throws Exception {
        int databaseSizeBeforeTest = assessmentRepository.findAll().size();
        // set the field null
        assessment.setThreshold(null);

        // Create the Assessment, which fails.
        AssessmentDTO assessmentDTO = assessmentMapper.toDto(assessment);

        restAssessmentMockMvc.perform(post("/api/assessments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assessmentDTO)))
            .andExpect(status().isBadRequest());

        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAssessments() throws Exception {
        // Initialize the database
        assessmentRepository.saveAndFlush(assessment);

        // Get all the assessmentList
        restAssessmentMockMvc.perform(get("/api/assessments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assessment.getId().intValue())))
            .andExpect(jsonPath("$.[*].threshold").value(hasItem(DEFAULT_THRESHOLD.doubleValue())));
    }

    @Test
    @Transactional
    public void getAssessment() throws Exception {
        // Initialize the database
        assessmentRepository.saveAndFlush(assessment);

        // Get the assessment
        restAssessmentMockMvc.perform(get("/api/assessments/{id}", assessment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(assessment.getId().intValue()))
            .andExpect(jsonPath("$.threshold").value(DEFAULT_THRESHOLD.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAssessment() throws Exception {
        // Get the assessment
        restAssessmentMockMvc.perform(get("/api/assessments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssessment() throws Exception {
        // Initialize the database
        assessmentRepository.saveAndFlush(assessment);
        int databaseSizeBeforeUpdate = assessmentRepository.findAll().size();

        // Update the assessment
        Assessment updatedAssessment = assessmentRepository.findOne(assessment.getId());
        // Disconnect from session so that the updates on updatedAssessment are not directly saved in db
        em.detach(updatedAssessment);
        updatedAssessment
            .threshold(UPDATED_THRESHOLD);
        AssessmentDTO assessmentDTO = assessmentMapper.toDto(updatedAssessment);

        restAssessmentMockMvc.perform(put("/api/assessments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assessmentDTO)))
            .andExpect(status().isOk());

        // Validate the Assessment in the database
        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeUpdate);
        Assessment testAssessment = assessmentList.get(assessmentList.size() - 1);
        assertThat(testAssessment.getThreshold()).isEqualTo(UPDATED_THRESHOLD);
    }

    @Test
    @Transactional
    public void updateNonExistingAssessment() throws Exception {
        int databaseSizeBeforeUpdate = assessmentRepository.findAll().size();

        // Create the Assessment
        AssessmentDTO assessmentDTO = assessmentMapper.toDto(assessment);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAssessmentMockMvc.perform(put("/api/assessments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assessmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Assessment in the database
        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAssessment() throws Exception {
        // Initialize the database
        assessmentRepository.saveAndFlush(assessment);
        int databaseSizeBeforeDelete = assessmentRepository.findAll().size();

        // Get the assessment
        restAssessmentMockMvc.perform(delete("/api/assessments/{id}", assessment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Assessment.class);
        Assessment assessment1 = new Assessment();
        assessment1.setId(1L);
        Assessment assessment2 = new Assessment();
        assessment2.setId(assessment1.getId());
        assertThat(assessment1).isEqualTo(assessment2);
        assessment2.setId(2L);
        assertThat(assessment1).isNotEqualTo(assessment2);
        assessment1.setId(null);
        assertThat(assessment1).isNotEqualTo(assessment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssessmentDTO.class);
        AssessmentDTO assessmentDTO1 = new AssessmentDTO();
        assessmentDTO1.setId(1L);
        AssessmentDTO assessmentDTO2 = new AssessmentDTO();
        assertThat(assessmentDTO1).isNotEqualTo(assessmentDTO2);
        assessmentDTO2.setId(assessmentDTO1.getId());
        assertThat(assessmentDTO1).isEqualTo(assessmentDTO2);
        assessmentDTO2.setId(2L);
        assertThat(assessmentDTO1).isNotEqualTo(assessmentDTO2);
        assessmentDTO1.setId(null);
        assertThat(assessmentDTO1).isNotEqualTo(assessmentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(assessmentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(assessmentMapper.fromId(null)).isNull();
    }
}
