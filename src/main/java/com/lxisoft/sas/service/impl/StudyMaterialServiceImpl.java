package com.lxisoft.sas.service.impl;

import com.lxisoft.sas.service.StudyMaterialService;
import com.lxisoft.sas.domain.StudyMaterial;
import com.lxisoft.sas.repository.StudyMaterialRepository;
import com.lxisoft.sas.service.dto.StudyMaterialDTO;
import com.lxisoft.sas.service.mapper.StudyMaterialMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing StudyMaterial.
 */
@Service
@Transactional
public class StudyMaterialServiceImpl implements StudyMaterialService {

    private final Logger log = LoggerFactory.getLogger(StudyMaterialServiceImpl.class);

    private final StudyMaterialRepository studyMaterialRepository;

    private final StudyMaterialMapper studyMaterialMapper;

    public StudyMaterialServiceImpl(StudyMaterialRepository studyMaterialRepository, StudyMaterialMapper studyMaterialMapper) {
        this.studyMaterialRepository = studyMaterialRepository;
        this.studyMaterialMapper = studyMaterialMapper;
    }

    /**
     * Save a studyMaterial.
     *
     * @param studyMaterialDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public StudyMaterialDTO save(StudyMaterialDTO studyMaterialDTO) {
        log.debug("Request to save StudyMaterial : {}", studyMaterialDTO);
        StudyMaterial studyMaterial = studyMaterialMapper.toEntity(studyMaterialDTO);
        studyMaterial = studyMaterialRepository.save(studyMaterial);
        return studyMaterialMapper.toDto(studyMaterial);
    }

    /**
     * Get all the studyMaterials.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StudyMaterialDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StudyMaterials");
        return studyMaterialRepository.findAll(pageable)
            .map(studyMaterialMapper::toDto);
    }


    /**
     * Get one studyMaterial by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StudyMaterialDTO> findOne(Long id) {
        log.debug("Request to get StudyMaterial : {}", id);
        return studyMaterialRepository.findById(id)
            .map(studyMaterialMapper::toDto);
    }

    /**
     * Delete the studyMaterial by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StudyMaterial : {}", id);        studyMaterialRepository.deleteById(id);
    }
}
