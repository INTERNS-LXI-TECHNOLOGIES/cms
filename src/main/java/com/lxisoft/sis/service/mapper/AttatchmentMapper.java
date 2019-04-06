package com.lxisoft.sis.service.mapper;

import com.lxisoft.sis.domain.*;
import com.lxisoft.sis.service.dto.AttatchmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Attatchment and its DTO AttatchmentDTO.
 */
@Mapper(componentModel = "spring", uses = {LeaveApplicationMapper.class})
public interface AttatchmentMapper extends EntityMapper<AttatchmentDTO, Attatchment> {

    @Mapping(source = "leaveApplication.id", target = "leaveApplicationId")
    AttatchmentDTO toDto(Attatchment attatchment);

    @Mapping(source = "leaveApplicationId", target = "leaveApplication")
    Attatchment toEntity(AttatchmentDTO attatchmentDTO);

    default Attatchment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Attatchment attatchment = new Attatchment();
        attatchment.setId(id);
        return attatchment;
    }
}
