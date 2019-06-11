package com.lxisoft.sas.web.rest;

import com.lxisoft.sas.SmartAcademicSystemApp;

import com.lxisoft.sas.domain.TimeTable;
import com.lxisoft.sas.repository.TimeTableRepository;
import com.lxisoft.sas.service.TimeTableService;
import com.lxisoft.sas.service.dto.TimeTableDTO;
import com.lxisoft.sas.service.mapper.TimeTableMapper;
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

import com.lxisoft.sas.domain.enumeration.Semester;
import com.lxisoft.sas.domain.enumeration.Department;
/**
 * Test class for the TimeTableResource REST controller.
 *
 * @see TimeTableResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartAcademicSystemApp.class)
public class TimeTableResourceIntTest {

    private static final Semester DEFAULT_SEMESTER = Semester.S1;
    private static final Semester UPDATED_SEMESTER = Semester.S2;

    private static final Department DEFAULT_DEPARTMENT = Department.CSE;
    private static final Department UPDATED_DEPARTMENT = Department.ME;

    @Autowired
    private TimeTableRepository timeTableRepository;

    @Autowired
    private TimeTableMapper timeTableMapper;

    @Autowired
    private TimeTableService timeTableService;

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

    private MockMvc restTimeTableMockMvc;

    private TimeTable timeTable;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TimeTableResource timeTableResource = new TimeTableResource(timeTableService);
        this.restTimeTableMockMvc = MockMvcBuilders.standaloneSetup(timeTableResource)
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
    public static TimeTable createEntity(EntityManager em) {
        TimeTable timeTable = new TimeTable()
            .semester(DEFAULT_SEMESTER)
            .department(DEFAULT_DEPARTMENT);
        return timeTable;
    }

    @Before
    public void initTest() {
        timeTable = createEntity(em);
    }

    @Test
    @Transactional
    public void createTimeTable() throws Exception {
        int databaseSizeBeforeCreate = timeTableRepository.findAll().size();

        // Create the TimeTable
        TimeTableDTO timeTableDTO = timeTableMapper.toDto(timeTable);
        restTimeTableMockMvc.perform(post("/api/time-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeTableDTO)))
            .andExpect(status().isCreated());

        // Validate the TimeTable in the database
        List<TimeTable> timeTableList = timeTableRepository.findAll();
        assertThat(timeTableList).hasSize(databaseSizeBeforeCreate + 1);
        TimeTable testTimeTable = timeTableList.get(timeTableList.size() - 1);
        assertThat(testTimeTable.getSemester()).isEqualTo(DEFAULT_SEMESTER);
        assertThat(testTimeTable.getDepartment()).isEqualTo(DEFAULT_DEPARTMENT);
    }

    @Test
    @Transactional
    public void createTimeTableWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = timeTableRepository.findAll().size();

        // Create the TimeTable with an existing ID
        timeTable.setId(1L);
        TimeTableDTO timeTableDTO = timeTableMapper.toDto(timeTable);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimeTableMockMvc.perform(post("/api/time-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeTableDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TimeTable in the database
        List<TimeTable> timeTableList = timeTableRepository.findAll();
        assertThat(timeTableList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTimeTables() throws Exception {
        // Initialize the database
        timeTableRepository.saveAndFlush(timeTable);

        // Get all the timeTableList
        restTimeTableMockMvc.perform(get("/api/time-tables?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timeTable.getId().intValue())))
            .andExpect(jsonPath("$.[*].semester").value(hasItem(DEFAULT_SEMESTER.toString())))
            .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT.toString())));
    }
    
    @Test
    @Transactional
    public void getTimeTable() throws Exception {
        // Initialize the database
        timeTableRepository.saveAndFlush(timeTable);

        // Get the timeTable
        restTimeTableMockMvc.perform(get("/api/time-tables/{id}", timeTable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(timeTable.getId().intValue()))
            .andExpect(jsonPath("$.semester").value(DEFAULT_SEMESTER.toString()))
            .andExpect(jsonPath("$.department").value(DEFAULT_DEPARTMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTimeTable() throws Exception {
        // Get the timeTable
        restTimeTableMockMvc.perform(get("/api/time-tables/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTimeTable() throws Exception {
        // Initialize the database
        timeTableRepository.saveAndFlush(timeTable);

        int databaseSizeBeforeUpdate = timeTableRepository.findAll().size();

        // Update the timeTable
        TimeTable updatedTimeTable = timeTableRepository.findById(timeTable.getId()).get();
        // Disconnect from session so that the updates on updatedTimeTable are not directly saved in db
        em.detach(updatedTimeTable);
        updatedTimeTable
            .semester(UPDATED_SEMESTER)
            .department(UPDATED_DEPARTMENT);
        TimeTableDTO timeTableDTO = timeTableMapper.toDto(updatedTimeTable);

        restTimeTableMockMvc.perform(put("/api/time-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeTableDTO)))
            .andExpect(status().isOk());

        // Validate the TimeTable in the database
        List<TimeTable> timeTableList = timeTableRepository.findAll();
        assertThat(timeTableList).hasSize(databaseSizeBeforeUpdate);
        TimeTable testTimeTable = timeTableList.get(timeTableList.size() - 1);
        assertThat(testTimeTable.getSemester()).isEqualTo(UPDATED_SEMESTER);
        assertThat(testTimeTable.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingTimeTable() throws Exception {
        int databaseSizeBeforeUpdate = timeTableRepository.findAll().size();

        // Create the TimeTable
        TimeTableDTO timeTableDTO = timeTableMapper.toDto(timeTable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimeTableMockMvc.perform(put("/api/time-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeTableDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TimeTable in the database
        List<TimeTable> timeTableList = timeTableRepository.findAll();
        assertThat(timeTableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTimeTable() throws Exception {
        // Initialize the database
        timeTableRepository.saveAndFlush(timeTable);

        int databaseSizeBeforeDelete = timeTableRepository.findAll().size();

        // Delete the timeTable
        restTimeTableMockMvc.perform(delete("/api/time-tables/{id}", timeTable.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TimeTable> timeTableList = timeTableRepository.findAll();
        assertThat(timeTableList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TimeTable.class);
        TimeTable timeTable1 = new TimeTable();
        timeTable1.setId(1L);
        TimeTable timeTable2 = new TimeTable();
        timeTable2.setId(timeTable1.getId());
        assertThat(timeTable1).isEqualTo(timeTable2);
        timeTable2.setId(2L);
        assertThat(timeTable1).isNotEqualTo(timeTable2);
        timeTable1.setId(null);
        assertThat(timeTable1).isNotEqualTo(timeTable2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TimeTableDTO.class);
        TimeTableDTO timeTableDTO1 = new TimeTableDTO();
        timeTableDTO1.setId(1L);
        TimeTableDTO timeTableDTO2 = new TimeTableDTO();
        assertThat(timeTableDTO1).isNotEqualTo(timeTableDTO2);
        timeTableDTO2.setId(timeTableDTO1.getId());
        assertThat(timeTableDTO1).isEqualTo(timeTableDTO2);
        timeTableDTO2.setId(2L);
        assertThat(timeTableDTO1).isNotEqualTo(timeTableDTO2);
        timeTableDTO1.setId(null);
        assertThat(timeTableDTO1).isNotEqualTo(timeTableDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(timeTableMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(timeTableMapper.fromId(null)).isNull();
    }
}
