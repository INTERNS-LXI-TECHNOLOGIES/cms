package com.lxisoft.sas.service;

import com.lxisoft.sas.service.dto.UserRoleDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing UserRole.
 */
public interface UserRoleService {

    /**
     * Save a userRole.
     *
     * @param userRoleDTO the entity to save
     * @return the persisted entity
     */
    UserRoleDTO save(UserRoleDTO userRoleDTO);

    /**
     * Get all the userRoles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UserRoleDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userRole.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<UserRoleDTO> findOne(Long id);

    /**
     * Delete the "id" userRole.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
