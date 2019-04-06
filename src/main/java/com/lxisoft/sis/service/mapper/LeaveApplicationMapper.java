package com.lxisoft.sis.service.mapper;

import com.lxisoft.sis.domain.*;
import com.lxisoft.sis.service.dto.LeaveApplicationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity LeaveApplication and its DTO LeaveApplicationDTO.
 */
@Mapper(componentModel = "spring", uses = {UserDomainMapper.class})
public interface LeaveApplicationMapper extends EntityMapper<LeaveApplicationDTO, LeaveApplication> {

    @Mapping(source = "appliedBy.id", target = "appliedById")
    LeaveApplicationDTO toDto(LeaveApplication leaveApplication);

    @Mapping(target = "attatchments", ignore = true)
    @Mapping(source = "appliedById", target = "appliedBy")
    LeaveApplication toEntity(LeaveApplicationDTO leaveApplicationDTO);

    default LeaveApplication fromId(Long id) {
        if (id == null) {
            return null;
        }
        LeaveApplication leaveApplication = new LeaveApplication();
        leaveApplication.setId(id);
        return leaveApplication;
    }
}
