package com.lxisoft.sas.service.mapper;

import com.lxisoft.sas.domain.*;
import com.lxisoft.sas.service.dto.QualificationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Qualification and its DTO QualificationDTO.
 */
@Mapper(componentModel = "spring", uses = {UserDomainMapper.class})
public interface QualificationMapper extends EntityMapper<QualificationDTO, Qualification> {

    @Mapping(source = "userDomain.id", target = "userDomainId")
    QualificationDTO toDto(Qualification qualification);

    @Mapping(source = "userDomainId", target = "userDomain")
    Qualification toEntity(QualificationDTO qualificationDTO);

    default Qualification fromId(Long id) {
        if (id == null) {
            return null;
        }
        Qualification qualification = new Qualification();
        qualification.setId(id);
        return qualification;
    }
}
