package com.lxisoft.sis.service;

import com.lxisoft.sis.service.dto.LeaveApplicationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing LeaveApplication.
 */
public interface LeaveApplicationService {

    /**
     * Save a leaveApplication.
     *
     * @param leaveApplicationDTO the entity to save
     * @return the persisted entity
     */
    LeaveApplicationDTO save(LeaveApplicationDTO leaveApplicationDTO);

    /**
     * Get all the leaveApplications.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<LeaveApplicationDTO> findAll(Pageable pageable);


    /**
     * Get the "id" leaveApplication.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<LeaveApplicationDTO> findOne(Long id);

    /**
     * Delete the "id" leaveApplication.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
