package com.lxisoft.sas.service.impl;

import com.lxisoft.sas.service.UserRoleService;
import com.lxisoft.sas.domain.UserRole;
import com.lxisoft.sas.repository.UserRoleRepository;
import com.lxisoft.sas.service.dto.UserRoleDTO;
import com.lxisoft.sas.service.mapper.UserRoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing UserRole.
 */
@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

    private final Logger log = LoggerFactory.getLogger(UserRoleServiceImpl.class);

    private final UserRoleRepository userRoleRepository;

    private final UserRoleMapper userRoleMapper;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository, UserRoleMapper userRoleMapper) {
        this.userRoleRepository = userRoleRepository;
        this.userRoleMapper = userRoleMapper;
    }

    /**
     * Save a userRole.
     *
     * @param userRoleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UserRoleDTO save(UserRoleDTO userRoleDTO) {
        log.debug("Request to save UserRole : {}", userRoleDTO);
        UserRole userRole = userRoleMapper.toEntity(userRoleDTO);
        userRole = userRoleRepository.save(userRole);
        return userRoleMapper.toDto(userRole);
    }

    /**
     * Get all the userRoles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserRoleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserRoles");
        return userRoleRepository.findAll(pageable)
            .map(userRoleMapper::toDto);
    }


    /**
     * Get one userRole by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UserRoleDTO> findOne(Long id) {
        log.debug("Request to get UserRole : {}", id);
        return userRoleRepository.findById(id)
            .map(userRoleMapper::toDto);
    }

    /**
     * Delete the userRole by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserRole : {}", id);        userRoleRepository.deleteById(id);
    }
}
