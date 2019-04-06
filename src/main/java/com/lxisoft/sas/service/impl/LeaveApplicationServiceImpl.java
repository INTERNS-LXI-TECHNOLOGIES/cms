package com.lxisoft.sas.service.impl;

import com.lxisoft.sas.service.LeaveApplicationService;
import com.lxisoft.sas.domain.LeaveApplication;
import com.lxisoft.sas.repository.LeaveApplicationRepository;
import com.lxisoft.sas.service.dto.LeaveApplicationDTO;
import com.lxisoft.sas.service.mapper.LeaveApplicationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing LeaveApplication.
 */
@Service
@Transactional
public class LeaveApplicationServiceImpl implements LeaveApplicationService {

    private final Logger log = LoggerFactory.getLogger(LeaveApplicationServiceImpl.class);

    private final LeaveApplicationRepository leaveApplicationRepository;

    private final LeaveApplicationMapper leaveApplicationMapper;

    public LeaveApplicationServiceImpl(LeaveApplicationRepository leaveApplicationRepository, LeaveApplicationMapper leaveApplicationMapper) {
        this.leaveApplicationRepository = leaveApplicationRepository;
        this.leaveApplicationMapper = leaveApplicationMapper;
    }

    /**
     * Save a leaveApplication.
     *
     * @param leaveApplicationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LeaveApplicationDTO save(LeaveApplicationDTO leaveApplicationDTO) {
        log.debug("Request to save LeaveApplication : {}", leaveApplicationDTO);
        LeaveApplication leaveApplication = leaveApplicationMapper.toEntity(leaveApplicationDTO);
        leaveApplication = leaveApplicationRepository.save(leaveApplication);
        return leaveApplicationMapper.toDto(leaveApplication);
    }

    /**
     * Get all the leaveApplications.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LeaveApplicationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LeaveApplications");
        return leaveApplicationRepository.findAll(pageable)
            .map(leaveApplicationMapper::toDto);
    }


    /**
     * Get one leaveApplication by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LeaveApplicationDTO> findOne(Long id) {
        log.debug("Request to get LeaveApplication : {}", id);
        return leaveApplicationRepository.findById(id)
            .map(leaveApplicationMapper::toDto);
    }

    /**
     * Delete the leaveApplication by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LeaveApplication : {}", id);        leaveApplicationRepository.deleteById(id);
    }
}
