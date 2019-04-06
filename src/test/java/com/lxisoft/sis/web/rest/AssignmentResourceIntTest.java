package com.lxisoft.sis.web.rest;

import com.lxisoft.sis.SmartInformationSystemApp;

import com.lxisoft.sis.domain.Assignment;
import com.lxisoft.sis.repository.AssignmentRepository;
import com.lxisoft.sis.service.AssignmentService;
import com.lxisoft.sis.service.dto.AssignmentDTO;
import com.lxisoft.sis.service.mapper.AssignmentMapper;
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

import com.lxisoft.sis.domain.enumeration.Semester;
import com.lxisoft.sis.domain.enumeration.Department;
/**
 * Test class for the AssignmentResource REST controller.
 *
 * @see AssignmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartInformationSystemApp.class)
public class AssignmentResourceIntTest {

    private static final Instant DEFAULT_SUBMISSION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SUBMISSION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TOPIC = "AAAAAAAAAA";
    private static final String UPDATED_TOPIC = "BBBBBBBBBB";

    private static final Semester DEFAULT_SEMESTER = Semester.S1;
    private static final Semester UPDATED_SEMESTER = Semester.S2;

    private static final Department DEFAULT_DEPARTMENT = Department.CSE;
    private static final Department UPDATED_DEPARTMENT = Department.ME;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private AssignmentMapper assignmentMapper;

    @Autowired
    private AssignmentService assignmentService;

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

    private MockMvc restAssignmentMockMvc;

    private Assignment assignment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AssignmentResource assignmentResource = new AssignmentResource(assignmentService);
        this.restAssignmentMockMvc = MockMvcBuilders.standaloneSetup(assignmentResource)
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
    public static Assignment createEntity(EntityManager em) {
        Assignment assignment = new Assignment()
            .submissionDate(DEFAULT_SUBMISSION_DATE)
            .topic(DEFAULT_TOPIC)
            .semester(DEFAULT_SEMESTER)
            .department(DEFAULT_DEPARTMENT);
        return assignment;
    }

    @Before
    public void initTest() {
        assignment = createEntity(em);
    }

    @Test
    @Transactional
    public void createAssignment() throws Exception {
        int databaseSizeBeforeCreate = assignmentRepository.findAll().size();

        // Create the Assignment
        AssignmentDTO assignmentDTO = assignmentMapper.toDto(assignment);
        restAssignmentMockMvc.perform(post("/api/assignments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assignmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Assignment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeCreate + 1);
        Assignment testAssignment = assignmentList.get(assignmentList.size() - 1);
        assertThat(testAssignment.getSubmissionDate()).isEqualTo(DEFAULT_SUBMISSION_DATE);
        assertThat(testAssignment.getTopic()).isEqualTo(DEFAULT_TOPIC);
        assertThat(testAssignment.getSemester()).isEqualTo(DEFAULT_SEMESTER);
        assertThat(testAssignment.getDepartment()).isEqualTo(DEFAULT_DEPARTMENT);
    }

    @Test
    @Transactional
    public void createAssignmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = assignmentRepository.findAll().size();

        // Create the Assignment with an existing ID
        assignment.setId(1L);
        AssignmentDTO assignmentDTO = assignmentMapper.toDto(assignment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssignmentMockMvc.perform(post("/api/assignments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assignmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Assignment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAssignments() throws Exception {
        // Initialize the database
        assignmentRepository.saveAndFlush(assignment);

        // Get all the assignmentList
        restAssignmentMockMvc.perform(get("/api/assignments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assignment.getId().intValue())))
            .andExpect(jsonPath("$.[*].submissionDate").value(hasItem(DEFAULT_SUBMISSION_DATE.toString())))
            .andExpect(jsonPath("$.[*].topic").value(hasItem(DEFAULT_TOPIC.toString())))
            .andExpect(jsonPath("$.[*].semester").value(hasItem(DEFAULT_SEMESTER.toString())))
            .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT.toString())));
    }
    
    @Test
    @Transactional
    public void getAssignment() throws Exception {
        // Initialize the database
        assignmentRepository.saveAndFlush(assignment);

        // Get the assignment
        restAssignmentMockMvc.perform(get("/api/assignments/{id}", assignment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(assignment.getId().intValue()))
            .andExpect(jsonPath("$.submissionDate").value(DEFAULT_SUBMISSION_DATE.toString()))
            .andExpect(jsonPath("$.topic").value(DEFAULT_TOPIC.toString()))
            .andExpect(jsonPath("$.semester").value(DEFAULT_SEMESTER.toString()))
            .andExpect(jsonPath("$.department").value(DEFAULT_DEPARTMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAssignment() throws Exception {
        // Get the assignment
        restAssignmentMockMvc.perform(get("/api/assignments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssignment() throws Exception {
        // Initialize the database
        assignmentRepository.saveAndFlush(assignment);

        int databaseSizeBeforeUpdate = assignmentRepository.findAll().size();

        // Update the assignment
        Assignment updatedAssignment = assignmentRepository.findById(assignment.getId()).get();
        // Disconnect from session so that the updates on updatedAssignment are not directly saved in db
        em.detach(updatedAssignment);
        updatedAssignment
            .submissionDate(UPDATED_SUBMISSION_DATE)
            .topic(UPDATED_TOPIC)
            .semester(UPDATED_SEMESTER)
            .department(UPDATED_DEPARTMENT);
        AssignmentDTO assignmentDTO = assignmentMapper.toDto(updatedAssignment);

        restAssignmentMockMvc.perform(put("/api/assignments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assignmentDTO)))
            .andExpect(status().isOk());

        // Validate the Assignment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeUpdate);
        Assignment testAssignment = assignmentList.get(assignmentList.size() - 1);
        assertThat(testAssignment.getSubmissionDate()).isEqualTo(UPDATED_SUBMISSION_DATE);
        assertThat(testAssignment.getTopic()).isEqualTo(UPDATED_TOPIC);
        assertThat(testAssignment.getSemester()).isEqualTo(UPDATED_SEMESTER);
        assertThat(testAssignment.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingAssignment() throws Exception {
        int databaseSizeBeforeUpdate = assignmentRepository.findAll().size();

        // Create the Assignment
        AssignmentDTO assignmentDTO = assignmentMapper.toDto(assignment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssignmentMockMvc.perform(put("/api/assignments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assignmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Assignment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAssignment() throws Exception {
        // Initialize the database
        assignmentRepository.saveAndFlush(assignment);

        int databaseSizeBeforeDelete = assignmentRepository.findAll().size();

        // Delete the assignment
        restAssignmentMockMvc.perform(delete("/api/assignments/{id}", assignment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Assignment.class);
        Assignment assignment1 = new Assignment();
        assignment1.setId(1L);
        Assignment assignment2 = new Assignment();
        assignment2.setId(assignment1.getId());
        assertThat(assignment1).isEqualTo(assignment2);
        assignment2.setId(2L);
        assertThat(assignment1).isNotEqualTo(assignment2);
        assignment1.setId(null);
        assertThat(assignment1).isNotEqualTo(assignment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssignmentDTO.class);
        AssignmentDTO assignmentDTO1 = new AssignmentDTO();
        assignmentDTO1.setId(1L);
        AssignmentDTO assignmentDTO2 = new AssignmentDTO();
        assertThat(assignmentDTO1).isNotEqualTo(assignmentDTO2);
        assignmentDTO2.setId(assignmentDTO1.getId());
        assertThat(assignmentDTO1).isEqualTo(assignmentDTO2);
        assignmentDTO2.setId(2L);
        assertThat(assignmentDTO1).isNotEqualTo(assignmentDTO2);
        assignmentDTO1.setId(null);
        assertThat(assignmentDTO1).isNotEqualTo(assignmentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(assignmentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(assignmentMapper.fromId(null)).isNull();
    }
}
