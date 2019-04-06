package com.lxisoft.sas.web.rest;

import com.lxisoft.sas.SmartAcademicSystemApp;

import com.lxisoft.sas.domain.ExamHall;
import com.lxisoft.sas.repository.ExamHallRepository;
import com.lxisoft.sas.service.ExamHallService;
import com.lxisoft.sas.service.dto.ExamHallDTO;
import com.lxisoft.sas.service.mapper.ExamHallMapper;
import com.lxisoft.sas.web.rest.errors.ExceptionTranslator;

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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.lxisoft.sas.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ExamHallResource REST controller.
 *
 * @see ExamHallResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartAcademicSystemApp.class)
public class ExamHallResourceIntTest {

    private static final Integer DEFAULT_HALL_NUMBER = 1;
    private static final Integer UPDATED_HALL_NUMBER = 2;

    private static final String DEFAULT_BATCH = "AAAAAAAAAA";
    private static final String UPDATED_BATCH = "BBBBBBBBBB";

    private static final Integer DEFAULT_ROLL_NUM_FROM = 1;
    private static final Integer UPDATED_ROLL_NUM_FROM = 2;

    private static final Integer DEFAULT_ROLL_NUM_TO = 1;
    private static final Integer UPDATED_ROLL_NUM_TO = 2;

    private static final String DEFAULT_INVIGIALTOR = "AAAAAAAAAA";
    private static final String UPDATED_INVIGIALTOR = "BBBBBBBBBB";

    @Autowired
    private ExamHallRepository examHallRepository;

    @Autowired
    private ExamHallMapper examHallMapper;

    @Autowired
    private ExamHallService examHallService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restExamHallMockMvc;

    private ExamHall examHall;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExamHallResource examHallResource = new ExamHallResource(examHallService);
        this.restExamHallMockMvc = MockMvcBuilders.standaloneSetup(examHallResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExamHall createEntity(EntityManager em) {
        ExamHall examHall = new ExamHall()
            .hallNumber(DEFAULT_HALL_NUMBER)
            .batch(DEFAULT_BATCH)
            .rollNumFrom(DEFAULT_ROLL_NUM_FROM)
            .rollNumTo(DEFAULT_ROLL_NUM_TO)
            .invigialtor(DEFAULT_INVIGIALTOR);
        return examHall;
    }

    @Before
    public void initTest() {
        examHall = createEntity(em);
    }

    @Test
    @Transactional
    public void createExamHall() throws Exception {
        int databaseSizeBeforeCreate = examHallRepository.findAll().size();

        // Create the ExamHall
        ExamHallDTO examHallDTO = examHallMapper.toDto(examHall);
        restExamHallMockMvc.perform(post("/api/exam-halls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examHallDTO)))
            .andExpect(status().isCreated());

        // Validate the ExamHall in the database
        List<ExamHall> examHallList = examHallRepository.findAll();
        assertThat(examHallList).hasSize(databaseSizeBeforeCreate + 1);
        ExamHall testExamHall = examHallList.get(examHallList.size() - 1);
        assertThat(testExamHall.getHallNumber()).isEqualTo(DEFAULT_HALL_NUMBER);
        assertThat(testExamHall.getBatch()).isEqualTo(DEFAULT_BATCH);
        assertThat(testExamHall.getRollNumFrom()).isEqualTo(DEFAULT_ROLL_NUM_FROM);
        assertThat(testExamHall.getRollNumTo()).isEqualTo(DEFAULT_ROLL_NUM_TO);
        assertThat(testExamHall.getInvigialtor()).isEqualTo(DEFAULT_INVIGIALTOR);
    }

    @Test
    @Transactional
    public void createExamHallWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = examHallRepository.findAll().size();

        // Create the ExamHall with an existing ID
        examHall.setId(1L);
        ExamHallDTO examHallDTO = examHallMapper.toDto(examHall);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExamHallMockMvc.perform(post("/api/exam-halls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examHallDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExamHall in the database
        List<ExamHall> examHallList = examHallRepository.findAll();
        assertThat(examHallList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllExamHalls() throws Exception {
        // Initialize the database
        examHallRepository.saveAndFlush(examHall);

        // Get all the examHallList
        restExamHallMockMvc.perform(get("/api/exam-halls?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examHall.getId().intValue())))
            .andExpect(jsonPath("$.[*].hallNumber").value(hasItem(DEFAULT_HALL_NUMBER)))
            .andExpect(jsonPath("$.[*].batch").value(hasItem(DEFAULT_BATCH.toString())))
            .andExpect(jsonPath("$.[*].rollNumFrom").value(hasItem(DEFAULT_ROLL_NUM_FROM)))
            .andExpect(jsonPath("$.[*].rollNumTo").value(hasItem(DEFAULT_ROLL_NUM_TO)))
            .andExpect(jsonPath("$.[*].invigialtor").value(hasItem(DEFAULT_INVIGIALTOR.toString())));
    }
    
    @Test
    @Transactional
    public void getExamHall() throws Exception {
        // Initialize the database
        examHallRepository.saveAndFlush(examHall);

        // Get the examHall
        restExamHallMockMvc.perform(get("/api/exam-halls/{id}", examHall.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(examHall.getId().intValue()))
            .andExpect(jsonPath("$.hallNumber").value(DEFAULT_HALL_NUMBER))
            .andExpect(jsonPath("$.batch").value(DEFAULT_BATCH.toString()))
            .andExpect(jsonPath("$.rollNumFrom").value(DEFAULT_ROLL_NUM_FROM))
            .andExpect(jsonPath("$.rollNumTo").value(DEFAULT_ROLL_NUM_TO))
            .andExpect(jsonPath("$.invigialtor").value(DEFAULT_INVIGIALTOR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExamHall() throws Exception {
        // Get the examHall
        restExamHallMockMvc.perform(get("/api/exam-halls/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExamHall() throws Exception {
        // Initialize the database
        examHallRepository.saveAndFlush(examHall);

        int databaseSizeBeforeUpdate = examHallRepository.findAll().size();

        // Update the examHall
        ExamHall updatedExamHall = examHallRepository.findById(examHall.getId()).get();
        // Disconnect from session so that the updates on updatedExamHall are not directly saved in db
        em.detach(updatedExamHall);
        updatedExamHall
            .hallNumber(UPDATED_HALL_NUMBER)
            .batch(UPDATED_BATCH)
            .rollNumFrom(UPDATED_ROLL_NUM_FROM)
            .rollNumTo(UPDATED_ROLL_NUM_TO)
            .invigialtor(UPDATED_INVIGIALTOR);
        ExamHallDTO examHallDTO = examHallMapper.toDto(updatedExamHall);

        restExamHallMockMvc.perform(put("/api/exam-halls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examHallDTO)))
            .andExpect(status().isOk());

        // Validate the ExamHall in the database
        List<ExamHall> examHallList = examHallRepository.findAll();
        assertThat(examHallList).hasSize(databaseSizeBeforeUpdate);
        ExamHall testExamHall = examHallList.get(examHallList.size() - 1);
        assertThat(testExamHall.getHallNumber()).isEqualTo(UPDATED_HALL_NUMBER);
        assertThat(testExamHall.getBatch()).isEqualTo(UPDATED_BATCH);
        assertThat(testExamHall.getRollNumFrom()).isEqualTo(UPDATED_ROLL_NUM_FROM);
        assertThat(testExamHall.getRollNumTo()).isEqualTo(UPDATED_ROLL_NUM_TO);
        assertThat(testExamHall.getInvigialtor()).isEqualTo(UPDATED_INVIGIALTOR);
    }

    @Test
    @Transactional
    public void updateNonExistingExamHall() throws Exception {
        int databaseSizeBeforeUpdate = examHallRepository.findAll().size();

        // Create the ExamHall
        ExamHallDTO examHallDTO = examHallMapper.toDto(examHall);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExamHallMockMvc.perform(put("/api/exam-halls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examHallDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExamHall in the database
        List<ExamHall> examHallList = examHallRepository.findAll();
        assertThat(examHallList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExamHall() throws Exception {
        // Initialize the database
        examHallRepository.saveAndFlush(examHall);

        int databaseSizeBeforeDelete = examHallRepository.findAll().size();

        // Delete the examHall
        restExamHallMockMvc.perform(delete("/api/exam-halls/{id}", examHall.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ExamHall> examHallList = examHallRepository.findAll();
        assertThat(examHallList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamHall.class);
        ExamHall examHall1 = new ExamHall();
        examHall1.setId(1L);
        ExamHall examHall2 = new ExamHall();
        examHall2.setId(examHall1.getId());
        assertThat(examHall1).isEqualTo(examHall2);
        examHall2.setId(2L);
        assertThat(examHall1).isNotEqualTo(examHall2);
        examHall1.setId(null);
        assertThat(examHall1).isNotEqualTo(examHall2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamHallDTO.class);
        ExamHallDTO examHallDTO1 = new ExamHallDTO();
        examHallDTO1.setId(1L);
        ExamHallDTO examHallDTO2 = new ExamHallDTO();
        assertThat(examHallDTO1).isNotEqualTo(examHallDTO2);
        examHallDTO2.setId(examHallDTO1.getId());
        assertThat(examHallDTO1).isEqualTo(examHallDTO2);
        examHallDTO2.setId(2L);
        assertThat(examHallDTO1).isNotEqualTo(examHallDTO2);
        examHallDTO1.setId(null);
        assertThat(examHallDTO1).isNotEqualTo(examHallDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(examHallMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(examHallMapper.fromId(null)).isNull();
    }
}
