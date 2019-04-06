package com.lxisoft.sis.web.rest;

import com.lxisoft.sis.SmartInformationSystemApp;

import com.lxisoft.sis.domain.UserRole;
import com.lxisoft.sis.repository.UserRoleRepository;
import com.lxisoft.sis.service.UserRoleService;
import com.lxisoft.sis.service.dto.UserRoleDTO;
import com.lxisoft.sis.service.mapper.UserRoleMapper;
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
import java.util.List;


import static com.lxisoft.sis.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lxisoft.sis.domain.enumeration.Role;
/**
 * Test class for the UserRoleResource REST controller.
 *
 * @see UserRoleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartInformationSystemApp.class)
public class UserRoleResourceIntTest {

    private static final Role DEFAULT_ROLE = Role.STUDENT;
    private static final Role UPDATED_ROLE = Role.FACULTY;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private UserRoleService userRoleService;

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

    private MockMvc restUserRoleMockMvc;

    private UserRole userRole;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserRoleResource userRoleResource = new UserRoleResource(userRoleService);
        this.restUserRoleMockMvc = MockMvcBuilders.standaloneSetup(userRoleResource)
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
    public static UserRole createEntity(EntityManager em) {
        UserRole userRole = new UserRole()
            .role(DEFAULT_ROLE);
        return userRole;
    }

    @Before
    public void initTest() {
        userRole = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserRole() throws Exception {
        int databaseSizeBeforeCreate = userRoleRepository.findAll().size();

        // Create the UserRole
        UserRoleDTO userRoleDTO = userRoleMapper.toDto(userRole);
        restUserRoleMockMvc.perform(post("/api/user-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userRoleDTO)))
            .andExpect(status().isCreated());

        // Validate the UserRole in the database
        List<UserRole> userRoleList = userRoleRepository.findAll();
        assertThat(userRoleList).hasSize(databaseSizeBeforeCreate + 1);
        UserRole testUserRole = userRoleList.get(userRoleList.size() - 1);
        assertThat(testUserRole.getRole()).isEqualTo(DEFAULT_ROLE);
    }

    @Test
    @Transactional
    public void createUserRoleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userRoleRepository.findAll().size();

        // Create the UserRole with an existing ID
        userRole.setId(1L);
        UserRoleDTO userRoleDTO = userRoleMapper.toDto(userRole);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserRoleMockMvc.perform(post("/api/user-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userRoleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserRole in the database
        List<UserRole> userRoleList = userRoleRepository.findAll();
        assertThat(userRoleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserRoles() throws Exception {
        // Initialize the database
        userRoleRepository.saveAndFlush(userRole);

        // Get all the userRoleList
        restUserRoleMockMvc.perform(get("/api/user-roles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userRole.getId().intValue())))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE.toString())));
    }
    
    @Test
    @Transactional
    public void getUserRole() throws Exception {
        // Initialize the database
        userRoleRepository.saveAndFlush(userRole);

        // Get the userRole
        restUserRoleMockMvc.perform(get("/api/user-roles/{id}", userRole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userRole.getId().intValue()))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserRole() throws Exception {
        // Get the userRole
        restUserRoleMockMvc.perform(get("/api/user-roles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserRole() throws Exception {
        // Initialize the database
        userRoleRepository.saveAndFlush(userRole);

        int databaseSizeBeforeUpdate = userRoleRepository.findAll().size();

        // Update the userRole
        UserRole updatedUserRole = userRoleRepository.findById(userRole.getId()).get();
        // Disconnect from session so that the updates on updatedUserRole are not directly saved in db
        em.detach(updatedUserRole);
        updatedUserRole
            .role(UPDATED_ROLE);
        UserRoleDTO userRoleDTO = userRoleMapper.toDto(updatedUserRole);

        restUserRoleMockMvc.perform(put("/api/user-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userRoleDTO)))
            .andExpect(status().isOk());

        // Validate the UserRole in the database
        List<UserRole> userRoleList = userRoleRepository.findAll();
        assertThat(userRoleList).hasSize(databaseSizeBeforeUpdate);
        UserRole testUserRole = userRoleList.get(userRoleList.size() - 1);
        assertThat(testUserRole.getRole()).isEqualTo(UPDATED_ROLE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserRole() throws Exception {
        int databaseSizeBeforeUpdate = userRoleRepository.findAll().size();

        // Create the UserRole
        UserRoleDTO userRoleDTO = userRoleMapper.toDto(userRole);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserRoleMockMvc.perform(put("/api/user-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userRoleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserRole in the database
        List<UserRole> userRoleList = userRoleRepository.findAll();
        assertThat(userRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserRole() throws Exception {
        // Initialize the database
        userRoleRepository.saveAndFlush(userRole);

        int databaseSizeBeforeDelete = userRoleRepository.findAll().size();

        // Delete the userRole
        restUserRoleMockMvc.perform(delete("/api/user-roles/{id}", userRole.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserRole> userRoleList = userRoleRepository.findAll();
        assertThat(userRoleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserRole.class);
        UserRole userRole1 = new UserRole();
        userRole1.setId(1L);
        UserRole userRole2 = new UserRole();
        userRole2.setId(userRole1.getId());
        assertThat(userRole1).isEqualTo(userRole2);
        userRole2.setId(2L);
        assertThat(userRole1).isNotEqualTo(userRole2);
        userRole1.setId(null);
        assertThat(userRole1).isNotEqualTo(userRole2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserRoleDTO.class);
        UserRoleDTO userRoleDTO1 = new UserRoleDTO();
        userRoleDTO1.setId(1L);
        UserRoleDTO userRoleDTO2 = new UserRoleDTO();
        assertThat(userRoleDTO1).isNotEqualTo(userRoleDTO2);
        userRoleDTO2.setId(userRoleDTO1.getId());
        assertThat(userRoleDTO1).isEqualTo(userRoleDTO2);
        userRoleDTO2.setId(2L);
        assertThat(userRoleDTO1).isNotEqualTo(userRoleDTO2);
        userRoleDTO1.setId(null);
        assertThat(userRoleDTO1).isNotEqualTo(userRoleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userRoleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userRoleMapper.fromId(null)).isNull();
    }
}
