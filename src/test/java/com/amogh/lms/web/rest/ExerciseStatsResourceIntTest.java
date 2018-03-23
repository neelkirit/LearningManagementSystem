package com.amogh.lms.web.rest;

import com.amogh.lms.AmoghServerApp;

import com.amogh.lms.domain.ExerciseStats;
import com.amogh.lms.repository.ExerciseStatsRepository;
import com.amogh.lms.service.ExerciseStatsService;
import com.amogh.lms.service.dto.ExerciseStatsDTO;
import com.amogh.lms.service.mapper.ExerciseStatsMapper;
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
 * Test class for the ExerciseStatsResource REST controller.
 *
 * @see ExerciseStatsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AmoghServerApp.class)
public class ExerciseStatsResourceIntTest {

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Autowired
    private ExerciseStatsRepository exerciseStatsRepository;

    @Autowired
    private ExerciseStatsMapper exerciseStatsMapper;

    @Autowired
    private ExerciseStatsService exerciseStatsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restExerciseStatsMockMvc;

    private ExerciseStats exerciseStats;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExerciseStatsResource exerciseStatsResource = new ExerciseStatsResource(exerciseStatsService);
        this.restExerciseStatsMockMvc = MockMvcBuilders.standaloneSetup(exerciseStatsResource)
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
    public static ExerciseStats createEntity(EntityManager em) {
        ExerciseStats exerciseStats = new ExerciseStats()
            .status(DEFAULT_STATUS);
        return exerciseStats;
    }

    @Before
    public void initTest() {
        exerciseStats = createEntity(em);
    }

    @Test
    @Transactional
    public void createExerciseStats() throws Exception {
        int databaseSizeBeforeCreate = exerciseStatsRepository.findAll().size();

        // Create the ExerciseStats
        ExerciseStatsDTO exerciseStatsDTO = exerciseStatsMapper.toDto(exerciseStats);
        restExerciseStatsMockMvc.perform(post("/api/exercise-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exerciseStatsDTO)))
            .andExpect(status().isCreated());

        // Validate the ExerciseStats in the database
        List<ExerciseStats> exerciseStatsList = exerciseStatsRepository.findAll();
        assertThat(exerciseStatsList).hasSize(databaseSizeBeforeCreate + 1);
        ExerciseStats testExerciseStats = exerciseStatsList.get(exerciseStatsList.size() - 1);
        assertThat(testExerciseStats.isStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createExerciseStatsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = exerciseStatsRepository.findAll().size();

        // Create the ExerciseStats with an existing ID
        exerciseStats.setId(1L);
        ExerciseStatsDTO exerciseStatsDTO = exerciseStatsMapper.toDto(exerciseStats);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExerciseStatsMockMvc.perform(post("/api/exercise-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exerciseStatsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExerciseStats in the database
        List<ExerciseStats> exerciseStatsList = exerciseStatsRepository.findAll();
        assertThat(exerciseStatsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = exerciseStatsRepository.findAll().size();
        // set the field null
        exerciseStats.setStatus(null);

        // Create the ExerciseStats, which fails.
        ExerciseStatsDTO exerciseStatsDTO = exerciseStatsMapper.toDto(exerciseStats);

        restExerciseStatsMockMvc.perform(post("/api/exercise-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exerciseStatsDTO)))
            .andExpect(status().isBadRequest());

        List<ExerciseStats> exerciseStatsList = exerciseStatsRepository.findAll();
        assertThat(exerciseStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExerciseStats() throws Exception {
        // Initialize the database
        exerciseStatsRepository.saveAndFlush(exerciseStats);

        // Get all the exerciseStatsList
        restExerciseStatsMockMvc.perform(get("/api/exercise-stats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exerciseStats.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getExerciseStats() throws Exception {
        // Initialize the database
        exerciseStatsRepository.saveAndFlush(exerciseStats);

        // Get the exerciseStats
        restExerciseStatsMockMvc.perform(get("/api/exercise-stats/{id}", exerciseStats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(exerciseStats.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingExerciseStats() throws Exception {
        // Get the exerciseStats
        restExerciseStatsMockMvc.perform(get("/api/exercise-stats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExerciseStats() throws Exception {
        // Initialize the database
        exerciseStatsRepository.saveAndFlush(exerciseStats);
        int databaseSizeBeforeUpdate = exerciseStatsRepository.findAll().size();

        // Update the exerciseStats
        ExerciseStats updatedExerciseStats = exerciseStatsRepository.findOne(exerciseStats.getId());
        // Disconnect from session so that the updates on updatedExerciseStats are not directly saved in db
        em.detach(updatedExerciseStats);
        updatedExerciseStats
            .status(UPDATED_STATUS);
        ExerciseStatsDTO exerciseStatsDTO = exerciseStatsMapper.toDto(updatedExerciseStats);

        restExerciseStatsMockMvc.perform(put("/api/exercise-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exerciseStatsDTO)))
            .andExpect(status().isOk());

        // Validate the ExerciseStats in the database
        List<ExerciseStats> exerciseStatsList = exerciseStatsRepository.findAll();
        assertThat(exerciseStatsList).hasSize(databaseSizeBeforeUpdate);
        ExerciseStats testExerciseStats = exerciseStatsList.get(exerciseStatsList.size() - 1);
        assertThat(testExerciseStats.isStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingExerciseStats() throws Exception {
        int databaseSizeBeforeUpdate = exerciseStatsRepository.findAll().size();

        // Create the ExerciseStats
        ExerciseStatsDTO exerciseStatsDTO = exerciseStatsMapper.toDto(exerciseStats);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restExerciseStatsMockMvc.perform(put("/api/exercise-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exerciseStatsDTO)))
            .andExpect(status().isCreated());

        // Validate the ExerciseStats in the database
        List<ExerciseStats> exerciseStatsList = exerciseStatsRepository.findAll();
        assertThat(exerciseStatsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteExerciseStats() throws Exception {
        // Initialize the database
        exerciseStatsRepository.saveAndFlush(exerciseStats);
        int databaseSizeBeforeDelete = exerciseStatsRepository.findAll().size();

        // Get the exerciseStats
        restExerciseStatsMockMvc.perform(delete("/api/exercise-stats/{id}", exerciseStats.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ExerciseStats> exerciseStatsList = exerciseStatsRepository.findAll();
        assertThat(exerciseStatsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExerciseStats.class);
        ExerciseStats exerciseStats1 = new ExerciseStats();
        exerciseStats1.setId(1L);
        ExerciseStats exerciseStats2 = new ExerciseStats();
        exerciseStats2.setId(exerciseStats1.getId());
        assertThat(exerciseStats1).isEqualTo(exerciseStats2);
        exerciseStats2.setId(2L);
        assertThat(exerciseStats1).isNotEqualTo(exerciseStats2);
        exerciseStats1.setId(null);
        assertThat(exerciseStats1).isNotEqualTo(exerciseStats2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExerciseStatsDTO.class);
        ExerciseStatsDTO exerciseStatsDTO1 = new ExerciseStatsDTO();
        exerciseStatsDTO1.setId(1L);
        ExerciseStatsDTO exerciseStatsDTO2 = new ExerciseStatsDTO();
        assertThat(exerciseStatsDTO1).isNotEqualTo(exerciseStatsDTO2);
        exerciseStatsDTO2.setId(exerciseStatsDTO1.getId());
        assertThat(exerciseStatsDTO1).isEqualTo(exerciseStatsDTO2);
        exerciseStatsDTO2.setId(2L);
        assertThat(exerciseStatsDTO1).isNotEqualTo(exerciseStatsDTO2);
        exerciseStatsDTO1.setId(null);
        assertThat(exerciseStatsDTO1).isNotEqualTo(exerciseStatsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(exerciseStatsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(exerciseStatsMapper.fromId(null)).isNull();
    }
}
