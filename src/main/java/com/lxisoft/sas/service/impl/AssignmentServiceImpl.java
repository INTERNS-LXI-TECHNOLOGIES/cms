package com.lxisoft.sas.service.impl;

import com.lxisoft.sas.service.AssignmentService;
import com.lxisoft.sas.domain.Assignment;
import com.lxisoft.sas.repository.AssignmentRepository;
import com.lxisoft.sas.service.dto.AssignmentDTO;
import com.lxisoft.sas.service.mapper.AssignmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Assignment.
 */
@Service
@Transactional
public class AssignmentServiceImpl implements AssignmentService {

    private final Logger log = LoggerFactory.getLogger(AssignmentServiceImpl.class);

    private final AssignmentRepository assignmentRepository;

    private final AssignmentMapper assignmentMapper;

    public AssignmentServiceImpl(AssignmentRepository assignmentRepository, AssignmentMapper assignmentMapper) {
        this.assignmentRepository = assignmentRepository;
        this.assignmentMapper = assignmentMapper;
    }

    /**
     * Save a assignment.
     *
     * @param assignmentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AssignmentDTO save(AssignmentDTO assignmentDTO) {
        log.debug("Request to save Assignment : {}", assignmentDTO);
        Assignment assignment = assignmentMapper.toEntity(assignmentDTO);
        assignment = assignmentRepository.save(assignment);
        return assignmentMapper.toDto(assignment);
    }

    /**
     * Get all the assignments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AssignmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Assignments");
        return assignmentRepository.findAll(pageable)
            .map(assignmentMapper::toDto);
    }


    /**
     * Get one assignment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AssignmentDTO> findOne(Long id) {
        log.debug("Request to get Assignment : {}", id);
        return assignmentRepository.findById(id)
            .map(assignmentMapper::toDto);
    }

    /**
     * Delete the assignment by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Assignment : {}", id);        assignmentRepository.deleteById(id);
    }
}
