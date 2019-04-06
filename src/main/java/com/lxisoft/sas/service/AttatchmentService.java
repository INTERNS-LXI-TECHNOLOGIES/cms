package com.lxisoft.sas.service;

import com.lxisoft.sas.service.dto.AttatchmentDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Attatchment.
 */
public interface AttatchmentService {

    /**
     * Save a attatchment.
     *
     * @param attatchmentDTO the entity to save
     * @return the persisted entity
     */
    AttatchmentDTO save(AttatchmentDTO attatchmentDTO);

    /**
     * Get all the attatchments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AttatchmentDTO> findAll(Pageable pageable);


    /**
     * Get the "id" attatchment.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AttatchmentDTO> findOne(Long id);

    /**
     * Delete the "id" attatchment.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
