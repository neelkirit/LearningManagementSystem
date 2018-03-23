package com.amogh.lms.web.rest;

import com.amogh.lms.AmoghServerApp;

import com.amogh.lms.domain.AssessmentStats;
import com.amogh.lms.repository.AssessmentStatsRepository;
import com.amogh.lms.service.AssessmentStatsService;
import com.amogh.lms.service.dto.AssessmentStatsDTO;
import com.amogh.lms.service.mapper.AssessmentStatsMapper;
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
 * Test class for the AssessmentStatsResource REST controller.
 *
 * @see AssessmentStatsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AmoghServerApp.class)
public class AssessmentStatsResourceIntTest {

    private static final Float DEFAULT_SCORE = 0F;
    private static final Float UPDATED_SCORE = 1F;

    @Autowired
    private AssessmentStatsRepository assessmentStatsRepository;

    @Autowired
    private AssessmentStatsMapper assessmentStatsMapper;

    @Autowired
    private AssessmentStatsService assessmentStatsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAssessmentStatsMockMvc;

    private AssessmentStats assessmentStats;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AssessmentStatsResource assessmentStatsResource = new AssessmentStatsResource(assessmentStatsService);
        this.restAssessmentStatsMockMvc = MockMvcBuilders.standaloneSetup(assessmentStatsResource)
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
    public static AssessmentStats createEntity(EntityManager em) {
        AssessmentStats assessmentStats = new AssessmentStats()
            .score(DEFAULT_SCORE);
        return assessmentStats;
    }

    @Before
    public void initTest() {
        assessmentStats = createEntity(em);
    }

    @Test
    @Transactional
    public void createAssessmentStats() throws Exception {
        int databaseSizeBeforeCreate = assessmentStatsRepository.findAll().size();

        // Create the AssessmentStats
        AssessmentStatsDTO assessmentStatsDTO = assessmentStatsMapper.toDto(assessmentStats);
        restAssessmentStatsMockMvc.perform(post("/api/assessment-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assessmentStatsDTO)))
            .andExpect(status().isCreated());

        // Validate the AssessmentStats in the database
        List<AssessmentStats> assessmentStatsList = assessmentStatsRepository.findAll();
        assertThat(assessmentStatsList).hasSize(databaseSizeBeforeCreate + 1);
        AssessmentStats testAssessmentStats = assessmentStatsList.get(assessmentStatsList.size() - 1);
        assertThat(testAssessmentStats.getScore()).isEqualTo(DEFAULT_SCORE);
    }

    @Test
    @Transactional
    public void createAssessmentStatsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = assessmentStatsRepository.findAll().size();

        // Create the AssessmentStats with an existing ID
        assessmentStats.setId(1L);
        AssessmentStatsDTO assessmentStatsDTO = assessmentStatsMapper.toDto(assessmentStats);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssessmentStatsMockMvc.perform(post("/api/assessment-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assessmentStatsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AssessmentStats in the database
        List<AssessmentStats> assessmentStatsList = assessmentStatsRepository.findAll();
        assertThat(assessmentStatsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkScoreIsRequired() throws Exception {
        int databaseSizeBeforeTest = assessmentStatsRepository.findAll().size();
        // set the field null
        assessmentStats.setScore(null);

        // Create the AssessmentStats, which fails.
        AssessmentStatsDTO assessmentStatsDTO = assessmentStatsMapper.toDto(assessmentStats);

        restAssessmentStatsMockMvc.perform(post("/api/assessment-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assessmentStatsDTO)))
            .andExpect(status().isBadRequest());

        List<AssessmentStats> assessmentStatsList = assessmentStatsRepository.findAll();
        assertThat(assessmentStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAssessmentStats() throws Exception {
        // Initialize the database
        assessmentStatsRepository.saveAndFlush(assessmentStats);

        // Get all the assessmentStatsList
        restAssessmentStatsMockMvc.perform(get("/api/assessment-stats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assessmentStats.getId().intValue())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE.doubleValue())));
    }

    @Test
    @Transactional
    public void getAssessmentStats() throws Exception {
        // Initialize the database
        assessmentStatsRepository.saveAndFlush(assessmentStats);

        // Get the assessmentStats
        restAssessmentStatsMockMvc.perform(get("/api/assessment-stats/{id}", assessmentStats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(assessmentStats.getId().intValue()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAssessmentStats() throws Exception {
        // Get the assessmentStats
        restAssessmentStatsMockMvc.perform(get("/api/assessment-stats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssessmentStats() throws Exception {
        // Initialize the database
        assessmentStatsRepository.saveAndFlush(assessmentStats);
        int databaseSizeBeforeUpdate = assessmentStatsRepository.findAll().size();

        // Update the assessmentStats
        AssessmentStats updatedAssessmentStats = assessmentStatsRepository.findOne(assessmentStats.getId());
        // Disconnect from session so that the updates on updatedAssessmentStats are not directly saved in db
        em.detach(updatedAssessmentStats);
        updatedAssessmentStats
            .score(UPDATED_SCORE);
        AssessmentStatsDTO assessmentStatsDTO = assessmentStatsMapper.toDto(updatedAssessmentStats);

        restAssessmentStatsMockMvc.perform(put("/api/assessment-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assessmentStatsDTO)))
            .andExpect(status().isOk());

        // Validate the AssessmentStats in the database
        List<AssessmentStats> assessmentStatsList = assessmentStatsRepository.findAll();
        assertThat(assessmentStatsList).hasSize(databaseSizeBeforeUpdate);
        AssessmentStats testAssessmentStats = assessmentStatsList.get(assessmentStatsList.size() - 1);
        assertThat(testAssessmentStats.getScore()).isEqualTo(UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void updateNonExistingAssessmentStats() throws Exception {
        int databaseSizeBeforeUpdate = assessmentStatsRepository.findAll().size();

        // Create the AssessmentStats
        AssessmentStatsDTO assessmentStatsDTO = assessmentStatsMapper.toDto(assessmentStats);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAssessmentStatsMockMvc.perform(put("/api/assessment-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assessmentStatsDTO)))
            .andExpect(status().isCreated());

        // Validate the AssessmentStats in the database
        List<AssessmentStats> assessmentStatsList = assessmentStatsRepository.findAll();
        assertThat(assessmentStatsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAssessmentStats() throws Exception {
        // Initialize the database
        assessmentStatsRepository.saveAndFlush(assessmentStats);
        int databaseSizeBeforeDelete = assessmentStatsRepository.findAll().size();

        // Get the assessmentStats
        restAssessmentStatsMockMvc.perform(delete("/api/assessment-stats/{id}", assessmentStats.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AssessmentStats> assessmentStatsList = assessmentStatsRepository.findAll();
        assertThat(assessmentStatsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssessmentStats.class);
        AssessmentStats assessmentStats1 = new AssessmentStats();
        assessmentStats1.setId(1L);
        AssessmentStats assessmentStats2 = new AssessmentStats();
        assessmentStats2.setId(assessmentStats1.getId());
        assertThat(assessmentStats1).isEqualTo(assessmentStats2);
        assessmentStats2.setId(2L);
        assertThat(assessmentStats1).isNotEqualTo(assessmentStats2);
        assessmentStats1.setId(null);
        assertThat(assessmentStats1).isNotEqualTo(assessmentStats2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssessmentStatsDTO.class);
        AssessmentStatsDTO assessmentStatsDTO1 = new AssessmentStatsDTO();
        assessmentStatsDTO1.setId(1L);
        AssessmentStatsDTO assessmentStatsDTO2 = new AssessmentStatsDTO();
        assertThat(assessmentStatsDTO1).isNotEqualTo(assessmentStatsDTO2);
        assessmentStatsDTO2.setId(assessmentStatsDTO1.getId());
        assertThat(assessmentStatsDTO1).isEqualTo(assessmentStatsDTO2);
        assessmentStatsDTO2.setId(2L);
        assertThat(assessmentStatsDTO1).isNotEqualTo(assessmentStatsDTO2);
        assessmentStatsDTO1.setId(null);
        assertThat(assessmentStatsDTO1).isNotEqualTo(assessmentStatsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(assessmentStatsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(assessmentStatsMapper.fromId(null)).isNull();
    }
}
