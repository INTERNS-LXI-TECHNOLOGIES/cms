package com.lxisoft.sis.web.rest;

import com.lxisoft.sis.SmartInformationSystemApp;

import com.lxisoft.sis.domain.ExamSchedule;
import com.lxisoft.sis.repository.ExamScheduleRepository;
import com.lxisoft.sis.service.ExamScheduleService;
import com.lxisoft.sis.service.dto.ExamScheduleDTO;
import com.lxisoft.sis.service.mapper.ExamScheduleMapper;
import com.lxisoft.sis.web.rest.errors.ExceptionTranslator;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.lxisoft.sis.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lxisoft.sis.domain.enumeration.Department;
import com.lxisoft.sis.domain.enumeration.Semester;
/**
 * Test class for the ExamScheduleResource REST controller.
 *
 * @see ExamScheduleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartInformationSystemApp.class)
public class ExamScheduleResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final Department DEFAULT_DEPARTMENT = Department.CSE;
    private static final Department UPDATED_DEPARTMENT = Department.ME;

    private static final Semester DEFAULT_SEMESTER = Semester.S1;
    private static final Semester UPDATED_SEMESTER = Semester.S2;

    @Autowired
    private ExamScheduleRepository examScheduleRepository;

    @Autowired
    private ExamScheduleMapper examScheduleMapper;

    @Autowired
    private ExamScheduleService examScheduleService;

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

    private MockMvc restExamScheduleMockMvc;

    private ExamSchedule examSchedule;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExamScheduleResource examScheduleResource = new ExamScheduleResource(examScheduleService);
        this.restExamScheduleMockMvc = MockMvcBuilders.standaloneSetup(examScheduleResource)
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
    public static ExamSchedule createEntity(EntityManager em) {
        ExamSchedule examSchedule = new ExamSchedule()
            .title(DEFAULT_TITLE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .active(DEFAULT_ACTIVE)
            .department(DEFAULT_DEPARTMENT)
            .semester(DEFAULT_SEMESTER);
        return examSchedule;
    }

    @Before
    public void initTest() {
        examSchedule = createEntity(em);
    }

    @Test
    @Transactional
    public void createExamSchedule() throws Exception {
        int databaseSizeBeforeCreate = examScheduleRepository.findAll().size();

        // Create the ExamSchedule
        ExamScheduleDTO examScheduleDTO = examScheduleMapper.toDto(examSchedule);
        restExamScheduleMockMvc.perform(post("/api/exam-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examScheduleDTO)))
            .andExpect(status().isCreated());

        // Validate the ExamSchedule in the database
        List<ExamSchedule> examScheduleList = examScheduleRepository.findAll();
        assertThat(examScheduleList).hasSize(databaseSizeBeforeCreate + 1);
        ExamSchedule testExamSchedule = examScheduleList.get(examScheduleList.size() - 1);
        assertThat(testExamSchedule.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testExamSchedule.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testExamSchedule.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testExamSchedule.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testExamSchedule.getDepartment()).isEqualTo(DEFAULT_DEPARTMENT);
        assertThat(testExamSchedule.getSemester()).isEqualTo(DEFAULT_SEMESTER);
    }

    @Test
    @Transactional
    public void createExamScheduleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = examScheduleRepository.findAll().size();

        // Create the ExamSchedule with an existing ID
        examSchedule.setId(1L);
        ExamScheduleDTO examScheduleDTO = examScheduleMapper.toDto(examSchedule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExamScheduleMockMvc.perform(post("/api/exam-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examScheduleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExamSchedule in the database
        List<ExamSchedule> examScheduleList = examScheduleRepository.findAll();
        assertThat(examScheduleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllExamSchedules() throws Exception {
        // Initialize the database
        examScheduleRepository.saveAndFlush(examSchedule);

        // Get all the examScheduleList
        restExamScheduleMockMvc.perform(get("/api/exam-schedules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examSchedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT.toString())))
            .andExpect(jsonPath("$.[*].semester").value(hasItem(DEFAULT_SEMESTER.toString())));
    }
    
    @Test
    @Transactional
    public void getExamSchedule() throws Exception {
        // Initialize the database
        examScheduleRepository.saveAndFlush(examSchedule);

        // Get the examSchedule
        restExamScheduleMockMvc.perform(get("/api/exam-schedules/{id}", examSchedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(examSchedule.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.department").value(DEFAULT_DEPARTMENT.toString()))
            .andExpect(jsonPath("$.semester").value(DEFAULT_SEMESTER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExamSchedule() throws Exception {
        // Get the examSchedule
        restExamScheduleMockMvc.perform(get("/api/exam-schedules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExamSchedule() throws Exception {
        // Initialize the database
        examScheduleRepository.saveAndFlush(examSchedule);

        int databaseSizeBeforeUpdate = examScheduleRepository.findAll().size();

        // Update the examSchedule
        ExamSchedule updatedExamSchedule = examScheduleRepository.findById(examSchedule.getId()).get();
        // Disconnect from session so that the updates on updatedExamSchedule are not directly saved in db
        em.detach(updatedExamSchedule);
        updatedExamSchedule
            .title(UPDATED_TITLE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .active(UPDATED_ACTIVE)
            .department(UPDATED_DEPARTMENT)
            .semester(UPDATED_SEMESTER);
        ExamScheduleDTO examScheduleDTO = examScheduleMapper.toDto(updatedExamSchedule);

        restExamScheduleMockMvc.perform(put("/api/exam-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examScheduleDTO)))
            .andExpect(status().isOk());

        // Validate the ExamSchedule in the database
        List<ExamSchedule> examScheduleList = examScheduleRepository.findAll();
        assertThat(examScheduleList).hasSize(databaseSizeBeforeUpdate);
        ExamSchedule testExamSchedule = examScheduleList.get(examScheduleList.size() - 1);
        assertThat(testExamSchedule.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testExamSchedule.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testExamSchedule.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testExamSchedule.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testExamSchedule.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testExamSchedule.getSemester()).isEqualTo(UPDATED_SEMESTER);
    }

    @Test
    @Transactional
    public void updateNonExistingExamSchedule() throws Exception {
        int databaseSizeBeforeUpdate = examScheduleRepository.findAll().size();

        // Create the ExamSchedule
        ExamScheduleDTO examScheduleDTO = examScheduleMapper.toDto(examSchedule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExamScheduleMockMvc.perform(put("/api/exam-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examScheduleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExamSchedule in the database
        List<ExamSchedule> examScheduleList = examScheduleRepository.findAll();
        assertThat(examScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExamSchedule() throws Exception {
        // Initialize the database
        examScheduleRepository.saveAndFlush(examSchedule);

        int databaseSizeBeforeDelete = examScheduleRepository.findAll().size();

        // Delete the examSchedule
        restExamScheduleMockMvc.perform(delete("/api/exam-schedules/{id}", examSchedule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ExamSchedule> examScheduleList = examScheduleRepository.findAll();
        assertThat(examScheduleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamSchedule.class);
        ExamSchedule examSchedule1 = new ExamSchedule();
        examSchedule1.setId(1L);
        ExamSchedule examSchedule2 = new ExamSchedule();
        examSchedule2.setId(examSchedule1.getId());
        assertThat(examSchedule1).isEqualTo(examSchedule2);
        examSchedule2.setId(2L);
        assertThat(examSchedule1).isNotEqualTo(examSchedule2);
        examSchedule1.setId(null);
        assertThat(examSchedule1).isNotEqualTo(examSchedule2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamScheduleDTO.class);
        ExamScheduleDTO examScheduleDTO1 = new ExamScheduleDTO();
        examScheduleDTO1.setId(1L);
        ExamScheduleDTO examScheduleDTO2 = new ExamScheduleDTO();
        assertThat(examScheduleDTO1).isNotEqualTo(examScheduleDTO2);
        examScheduleDTO2.setId(examScheduleDTO1.getId());
        assertThat(examScheduleDTO1).isEqualTo(examScheduleDTO2);
        examScheduleDTO2.setId(2L);
        assertThat(examScheduleDTO1).isNotEqualTo(examScheduleDTO2);
        examScheduleDTO1.setId(null);
        assertThat(examScheduleDTO1).isNotEqualTo(examScheduleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(examScheduleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(examScheduleMapper.fromId(null)).isNull();
    }
}
