package com.lxisoft.sis.service.impl;

import com.lxisoft.sis.service.AttatchmentService;
import com.lxisoft.sis.domain.Attatchment;
import com.lxisoft.sis.repository.AttatchmentRepository;
import com.lxisoft.sis.service.dto.AttatchmentDTO;
import com.lxisoft.sis.service.mapper.AttatchmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Attatchment.
 */
@Service
@Transactional
public class AttatchmentServiceImpl implements AttatchmentService {

    private final Logger log = LoggerFactory.getLogger(AttatchmentServiceImpl.class);

    private final AttatchmentRepository attatchmentRepository;

    private final AttatchmentMapper attatchmentMapper;

    public AttatchmentServiceImpl(AttatchmentRepository attatchmentRepository, AttatchmentMapper attatchmentMapper) {
        this.attatchmentRepository = attatchmentRepository;
        this.attatchmentMapper = attatchmentMapper;
    }

    /**
     * Save a attatchment.
     *
     * @param attatchmentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AttatchmentDTO save(AttatchmentDTO attatchmentDTO) {
        log.debug("Request to save Attatchment : {}", attatchmentDTO);
        Attatchment attatchment = attatchmentMapper.toEntity(attatchmentDTO);
        attatchment = attatchmentRepository.save(attatchment);
        return attatchmentMapper.toDto(attatchment);
    }

    /**
     * Get all the attatchments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AttatchmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Attatchments");
        return attatchmentRepository.findAll(pageable)
            .map(attatchmentMapper::toDto);
    }


    /**
     * Get one attatchment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AttatchmentDTO> findOne(Long id) {
        log.debug("Request to get Attatchment : {}", id);
        return attatchmentRepository.findById(id)
            .map(attatchmentMapper::toDto);
    }

    /**
     * Delete the attatchment by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Attatchment : {}", id);        attatchmentRepository.deleteById(id);
    }
}
