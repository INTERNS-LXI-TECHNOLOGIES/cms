package com.lxisoft.sis.service.mapper;

import com.lxisoft.sis.domain.*;
import com.lxisoft.sis.service.dto.AssignmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Assignment and its DTO AssignmentDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AssignmentMapper extends EntityMapper<AssignmentDTO, Assignment> {



    default Assignment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Assignment assignment = new Assignment();
        assignment.setId(id);
        return assignment;
    }
}
