package com.lxisoft.sas.service;

import com.lxisoft.sas.service.dto.UserDomainDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing UserDomain.
 */
public interface UserDomainService {

    /**
     * Save a userDomain.
     *
     * @param userDomainDTO the entity to save
     * @return the persisted entity
     */
    UserDomainDTO save(UserDomainDTO userDomainDTO);

    /**
     * Get all the userDomains.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UserDomainDTO> findAll(Pageable pageable);

    /**
     * Get all the UserDomain with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<UserDomainDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" userDomain.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<UserDomainDTO> findOne(Long id);

    /**
     * Delete the "id" userDomain.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
