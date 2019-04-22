package com.lxisoft.sas.service;

import com.lxisoft.sas.domain.UserDomain;
import com.lxisoft.sas.service.dto.QualificationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Qualification.
 */
public interface QualificationService {

    /**
     * Save a qualification.
     *
     * @param qualificationDTO the entity to save
     * @return the persisted entity
     */
    QualificationDTO save(QualificationDTO qualificationDTO);

    /**
     * Get all the qualifications.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<QualificationDTO> findAll(Pageable pageable);


    /**
     * Get the "id" qualification.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<QualificationDTO> findOne(Long id);

    /**
     * Delete the "id" qualification.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
    Page<QualificationDTO> getQualificationOfUser(UserDomain user, Pageable pageable);
}
