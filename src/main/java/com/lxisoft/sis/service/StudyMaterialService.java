package com.lxisoft.sis.service;

import com.lxisoft.sis.service.dto.StudyMaterialDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing StudyMaterial.
 */
public interface StudyMaterialService {

    /**
     * Save a studyMaterial.
     *
     * @param studyMaterialDTO the entity to save
     * @return the persisted entity
     */
    StudyMaterialDTO save(StudyMaterialDTO studyMaterialDTO);

    /**
     * Get all the studyMaterials.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<StudyMaterialDTO> findAll(Pageable pageable);


    /**
     * Get the "id" studyMaterial.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<StudyMaterialDTO> findOne(Long id);

    /**
     * Delete the "id" studyMaterial.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
