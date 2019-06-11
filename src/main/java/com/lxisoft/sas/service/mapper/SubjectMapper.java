package com.lxisoft.sas.service.mapper;

import com.lxisoft.sas.domain.*;
import com.lxisoft.sas.service.dto.SubjectDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Subject and its DTO SubjectDTO.
 */
@Mapper(componentModel = "spring", uses = {UserDomainMapper.class})
public interface SubjectMapper extends EntityMapper<SubjectDTO, Subject> {

    @Mapping(source = "faculty.id", target = "facultyId")
    SubjectDTO toDto(Subject subject);

    @Mapping(target = "materials", ignore = true)
    @Mapping(source = "facultyId", target = "faculty")
    Subject toEntity(SubjectDTO subjectDTO);

    default Subject fromId(Long id) {
        if (id == null) {
            return null;
        }
        Subject subject = new Subject();
        subject.setId(id);
        return subject;
    }
}
